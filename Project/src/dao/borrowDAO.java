package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.borrowDTO;

public class borrowDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	public int insertBorrow(borrowDTO dto) {
		conn = DataBase.getConnection();
		int row = 0;
		String sql = "insert into borrowlist(CUST_ID,BOOK_CODE,BORROW_DATE,RETURN_DATE) VALUES (?,?,sysdate(),date_add(sysdate(), interval 7 day))";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setInt(2, dto.getBOOK_CODE());

			row = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
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

	public int deleteBorrow(borrowDTO dto) {
		conn = DataBase.getConnection();
		String sql = "update borrowlist set state = 'Y' where cust_id = ? and book_code = ? and state = 'N'";
		int row = 0;
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setInt(2, dto.getBOOK_CODE());
			row = pstmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			row = -1;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println(e);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e1) {
			}
			if (pstmt != null)
				try {
					pstmt.close();
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

	public int dateUpdate(borrowDTO dto) {
		conn = DataBase.getConnection();
		String sql = "UPDATE borrowlist SET "
				+ "return_date = ADDDATE((SELECT return_date FROM borrowlist where cust_id = ? and book_code = ? and state = 'N'), INTERVAL 7 DAY) "
				+ "WHERE cust_id = ? and book_code = ? and state = 'N'";
		int row = 0;
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setInt(2, dto.getBOOK_CODE());
			pstmt.setString(3, dto.getId());
			pstmt.setInt(4, dto.getBOOK_CODE());
			row = pstmt.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			row = -1;
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
			if (pstmt != null)
				try {
					pstmt.close();
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
	
	public ArrayList<borrowDTO> RentalHistory(String id) { // 테이블에 보이기 위해 검색
		String sql = 
				"SELECT *, row_number() over(order by borrow_date) as no FROM book LEFT JOIN borrowlist ON "
				+ "book.book_code = borrowlist.book_code WHERE borrowlist.INDEX IS NOT NULL AND cust_id = ? order by no desc";
		ArrayList<borrowDTO> list = new ArrayList<borrowDTO>();
		borrowDTO bdto;
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				bdto = new borrowDTO();
				bdto.setIndex(rs.getInt("no"));
				bdto.setBOOK_CODE(rs.getInt("BOOK_CODE"));
				bdto.setBookname(rs.getString("BOOK_NAME"));
				bdto.setBdate(rs.getTimestamp("BORROW_DATE"));
				bdto.setRdate(rs.getTimestamp("RETURN_DATE"));
				bdto.setState(rs.getString("state"));
				list.add(bdto);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DataBase.close(rs, pstmt, conn);
		}
		return list;
	}
}