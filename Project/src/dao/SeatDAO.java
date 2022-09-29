package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dto.SeatDTO;

public class SeatDAO {
	String sql;
	
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	public SeatDAO() {
		Used_Drop();
	}
	
	// 자리목록
	public ArrayList<SeatDTO> Seat_List() {
		Used_Drop();
		sql = "SELECT * FROM seat_list LEFT JOIN seat_used ON SL_NUM = SU_SLNUM and su_sort = 'N' ORDER BY SL_NUM";
		ArrayList<SeatDTO> list = new ArrayList<SeatDTO>();
		
		conn = DataBase.getConnection();
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				SeatDTO sdto = new SeatDTO();
				sdto.setS_num(rs.getInt("sl_num"));
				sdto.setS_used(rs.getString("sl_used"));
				sdto.setS_id(rs.getString("su_mid"));
				sdto.setS_sdate(rs.getTimestamp("su_sdate"));
				sdto.setS_edate(rs.getTimestamp("su_edate"));
				list.add(sdto);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			DataBase.close(rs, stmt, conn);
		}
		return list;
	}
	
	// 자리사용목록
	public ArrayList<SeatDTO> Used_List(String id) {
		Used_Drop();
		sql = "select row_number() over(order BY (SELECT 1)) as no, su.* from seat_used AS su where su_sort = 'N' ";
		if(id != null) {
			sql += " and su_mid = '" + id + "' order by su_index";
		}else {
			sql += " order by su_index";
		}
		ArrayList<SeatDTO> list = new ArrayList<SeatDTO>();
		
		conn = DataBase.getConnection();
		
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				SeatDTO sdto = new SeatDTO();
				sdto.setS_num(rs.getInt("su_slnum"));
				sdto.setS_id(rs.getString("su_mid"));
				sdto.setS_sdate(rs.getTimestamp("su_sdate"));
				sdto.setS_edate(rs.getTimestamp("su_edate"));
				list.add(sdto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataBase.close(rs, stmt, conn);
		}
		return list;
	}
	// 예약 목록
	public ArrayList<SeatDTO> Resever_list(String id) {
		Used_Drop();
		sql = "select * from seat_resev";
		if(id != null) {
			sql += " where sr_mid = ?";
		}
		conn = DataBase.getConnection();
		ArrayList<SeatDTO> list = new ArrayList<SeatDTO>();
		
		conn = DataBase.getConnection();
		
		try {
			stmt = conn.prepareStatement(sql);
			if(id != null) {
				stmt.setString(1, id);
			}
			rs = stmt.executeQuery();
			while(rs.next()) {
				SeatDTO sdto = new SeatDTO();
				sdto.setS_index(rs.getInt("sr_index"));
				sdto.setS_num(rs.getInt("sr_slnum"));
				sdto.setS_id(rs.getString("sr_mid"));
				sdto.setS_date(rs.getTimestamp("sr_date"));
				list.add(sdto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DataBase.close(rs, stmt, conn);
		}
		System.out.println(list.size());
		return list;
	}
	
	// 예약하기
	public int Resever(int num, String id) {
		Used_Drop();
		int res = 0;
		if(Used_List(id).size() != 0) {
			return -2;
		}else if(Resever_list(id).size() != 0) {
			return -3;
		}else {
			sql = "insert into seat_resev(sr_slnum, sr_mid) values(?, ?)";
			conn = DataBase.getConnection();
			
			try {
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, num);
				stmt.setString(2, id);
				res = stmt.executeUpdate();
				conn.commit();
			} catch (Exception e) {
				try {
					res = -1;
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				DataBase.close(stmt, conn);
			}
		}
		return res;
	}
	
	// 사용하기
	public int Used(int num, String used, String id) {
		Used_Drop();
		int res = -1;
		if(Used_List(id).size() != 0) {
			return -2;
		}else if(Resever_list(id).size() != 0) {
			return -3;
		}else {
			sql = "insert into seat_used(su_slnum, su_mid) values(?, ?)";
			conn = DataBase.getConnection();
			try {
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, num);
				stmt.setString(2, id);
				if(stmt.executeUpdate() == 0) {
					throw new Exception();
				}else {
					sql = "update seat_list set sl_used = 'Y' where sl_num = ? and sl_used = ?";
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, num);
					stmt.setString(2, used);
					if(stmt.executeUpdate() == 0) {
						throw new Exception();
					}else {
						res = 1;
						conn.commit();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("에러났다");
				e.printStackTrace();
				try {
					res = -1;
					conn.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} finally {
				try {
					conn.setAutoCommit(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				DataBase.close(stmt, conn);
			}
		}
		return res;
	}

	// 사용시간 끝난 자리 후
	public int Used_Drop() {
		System.out.println("자리 후처리");
		int res = 0;
		conn = DataBase.getConnection();
		int cnt = 0;
		try {
			conn.setAutoCommit(false);
			System.out.println("select1");
			sql = "SELECT COUNT(*) cnt FROM seat_resev LEFT JOIN seat_used ON sr_slnum = seat_used.su_slnum WHERE SYSDATE() >= SU_EDATE AND su_sort = 'N'";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			System.out.println(cnt);
			if(cnt != 0) {
				System.out.println("insert1");
				sql = "INSERT INTO seat_used(su_slnum, su_mid) SELECT sr_slnum, sr_mid FROM seat_resev";
				stmt = conn.prepareStatement(sql);
				stmt.executeUpdate();
				System.out.println("delete1");
				sql = "DELETE FROM seat_resev WHERE sr_slnum IN (SELECT su_slnum FROM seat_used left JOIN seat_resev ON sr_slnum = su_slnum WHERE sr_index IS not NULL)";
				stmt = conn.prepareStatement(sql);
				stmt.executeUpdate();
			}
			System.out.println("update1");
			sql = "update seat_used set su_sort = 'Y' WHERE SYSDATE() >= SU_EDATE";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			System.out.println("update2");
			sql = "UPDATE seat_list SET SL_USED = 'N'";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			System.out.println("update3");
			sql = "UPDATE seat_list SET SL_USED = 'Y' WHERE sl_num in (SELECT SU_SLNUM FROM seat_used where su_sort = 'N')";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			System.out.println("rollback");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			// e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBase.close(rs, stmt, conn);
		}
		return res;
	}

	public ArrayList<SeatDTO> UsedHistory(String id) {
		System.out.println("UsedHistory");
		Used_Drop();
		sql = "select row_number() over(order BY su_sdate) as no, su.* from seat_used AS su where su_mid = ? order by no desc";
		ArrayList<SeatDTO> list = new ArrayList<SeatDTO>();
		conn = DataBase.getConnection();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while(rs.next()) {
				SeatDTO sdto = new SeatDTO();
				sdto.setS_index(rs.getInt("no"));
				sdto.setS_num(rs.getInt("su_slnum"));
				sdto.setS_sdate(rs.getTimestamp("su_sdate"));
				sdto.setS_edate(rs.getTimestamp("su_edate"));
				list.add(sdto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBase.close(rs, stmt, conn);
		}
		return list;
	}

	public int returnSeat(String s_id, int s_num) {
		Used_Drop();
		int res = 0;
		conn = DataBase.getConnection();
		try {
			conn.setAutoCommit(false);
			int cnt = 0;
			System.out.println("select1");
			sql = "SELECT COUNT(*) cnt FROM seat_resev LEFT JOIN seat_used ON sr_slnum = seat_used.su_slnum WHERE su_mid = ? and su_slnum = ? and su_sort = 'N'";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, s_id);
			stmt.setInt(2, s_num);
			rs = stmt.executeQuery();
			while(rs.next()) {
				cnt = rs.getInt("cnt");
			}
			System.out.println(cnt);
			sql = "UPDATE seat_used SET su_sort = 'Y', su_edate = sysdate() WHERE su_mid = ? AND su_slnum = ? AND su_sort = 'N'";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, s_id);
			stmt.setInt(2, s_num);
			res = stmt.executeUpdate();
			if(res == 1) {
				if(cnt != 0) {
					System.out.println("insert1");
					sql = "INSERT INTO seat_used(su_slnum, su_mid) SELECT sr_slnum, sr_mid FROM seat_resev";
					stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
					System.out.println("delete1");
					sql = "DELETE FROM seat_resev WHERE sr_slnum IN (SELECT su_slnum FROM seat_used left JOIN seat_resev ON sr_slnum = su_slnum WHERE sr_index IS not NULL)";
					stmt = conn.prepareStatement(sql);
					stmt.executeUpdate();
				}
			}
			System.out.println("update2");
			sql = "UPDATE seat_list SET SL_USED = 'N'";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			System.out.println("update3");
			sql = "UPDATE seat_list SET SL_USED = 'Y' WHERE sl_num in (SELECT SU_SLNUM FROM seat_used where su_sort = 'N')";
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DataBase.close(stmt, conn);
		}
		return res;
	}

	public int Cancel(String id) {
		int res = 0;
		sql = "DELETE FROM seat_resev WHERE sr_mid = ?";
		conn = DataBase.getConnection();
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			res = stmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
				DataBase.close(stmt, conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
}
