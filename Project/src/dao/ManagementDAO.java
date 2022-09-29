package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import dto.ManagementDTO;

public class ManagementDAO {

	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;

	public ManagementDAO() {
	}

	private static ManagementDAO instance = new ManagementDAO();

	public static ManagementDAO getInstance() {
		return instance;
	}

	public int idPassword(String id, String password) {
		String login = "select * from management where m_id = ? and m_pw = ?";
		conn = DataBase.getConnection();
		int res = 0;
		try {
			stmt = conn.prepareStatement(login);
			stmt.setString(1, id);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				res = rs.getInt("m_state");
			}
		} catch (SQLException e) {
			res = -1;
			e.printStackTrace();
		}
		return res;
	}

	public List<ManagementDTO> managementList(String id) {
		conn = DataBase.getConnection();
		ArrayList<ManagementDTO> list = new ArrayList<ManagementDTO>();
		String sql = "SELECT row_number() over(order BY m_date) AS NO, M_ID,M_PW,M_NAME,M_GENDER,M_EMAIL,M_TEL,M_DATE FROM management";
		if (id != null) {
			sql += " where m_id = ?";
		}
		try {
			stmt = conn.prepareStatement(sql);
			if (id != null) {
				stmt.setString(1, id);
			}
			rs = stmt.executeQuery();
			while (rs.next()) {
				ManagementDTO mdto = new ManagementDTO();
				mdto.setM_no(rs.getInt("no"));
				mdto.setM_id(rs.getString("m_id"));
				mdto.setM_pw(rs.getString("m_pw"));
				mdto.setM_name(rs.getString("m_name"));
				mdto.setM_tel(rs.getString("m_tel"));
				mdto.setM_email(rs.getString("m_email"));
				mdto.setM_gender(rs.getString("m_gender"));
				mdto.setM_date(rs.getTimestamp("m_date"));
				list.add(mdto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public int insertMember(ManagementDTO dto) {

		conn = DataBase.getConnection();
		String sql = "insert into management(m_id, m_pw, m_name, m_gender, m_email, m_tel) values(?,?,?,?,?,?)";
		int row = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, dto.getM_id());
			stmt.setString(2, dto.getM_pw());
			stmt.setString(3, dto.getM_name());
			stmt.setString(4, dto.getM_gender());
			stmt.setString(5, dto.getM_email());
			stmt.setString(6, dto.getM_tel());

			row = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}
		}
		return row;
	}

	public int updateMember(ManagementDTO dto) {
		conn = DataBase.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("update management");
		sb.append(" set m_tel = ?, m_email = ?, m_gender = ?");
		if(dto.getM_state() != 0) {
			sb.append(", m_state = ?");
		}
		if(!dto.getM_pw().equals("")) {
			sb.append(", m_pw = ?");
		}
		sb.append(" where m_id = ?");
		int row = 0;
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, dto.getM_tel());
			stmt.setString(2, dto.getM_email());
			stmt.setString(3, dto.getM_gender());
			if(dto.getM_state() != 0) {
				stmt.setInt(4, dto.getM_state());
				if(!dto.getM_pw().equals("")) {
					stmt.setString(5, dto.getM_pw());
					stmt.setString(6, dto.getM_id());
				}else {
					stmt.setString(5, dto.getM_id());
				}
			}else {
				if(!dto.getM_pw().equals("")) {
					stmt.setString(4, dto.getM_pw());
					stmt.setString(5, dto.getM_id());
				}else {
					stmt.setString(4, dto.getM_id());
				}
			}
			row = stmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}
		}
		return row;
	}

	public int deleteMember(ManagementDTO dto) {
		conn = DataBase.getConnection();
		String sql = "update management set m_state = ? where m_id = ?";
		int row = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, dto.getM_state());
			stmt.setString(2, dto.getM_id());
			row = stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}
		}
		return row;
	}

	public String idcheck(String id) {
		conn = DataBase.getConnection();
		String str = "";
		StringBuffer sb = new StringBuffer();
		sb.append("   select                      ");
		sb.append("       m_id              ");
		sb.append("   from management                ");
		sb.append("   where                       ");
		sb.append("      m_id=?             ");
		try {
			stmt = conn.prepareStatement(sb.toString());
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				str = rs.getString("m_id");
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
				}
		}
		return str;
	}
}