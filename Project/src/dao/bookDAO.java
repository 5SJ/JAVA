package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.bookDTO;

public class bookDAO {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	String sql;
	int chk;
	public int updateBook(bookDTO dto) {
		conn = DataBase.getConnection();
		sql = "update book set BOOK_CATEGORY=?,BOOK_NAME=?,BOOK_AUTHOR=?,BOOK_COMPANY=? where BOOK_CODE = ?";
		int row = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBOOK_CATEGORY());
			pstmt.setString(2, dto.getBOOK_NAME());
			pstmt.setString(3, dto.getBOOK_AUTHOR());
			pstmt.setString(4, dto.getBOOK_COMPANY());
			pstmt.setInt(5, dto.getBOOK_CODE());
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

	public int insertBook(bookDTO dto) {
		conn = DataBase.getConnection();
		sql = "insert into book(BOOK_CODE,BOOK_CATEGORY,BOOK_NAME,BOOK_AUTHOR,BOOK_COMPANY) values (?,?,?,?,?)";
		int row = 0;
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getBOOK_CODE());
			pstmt.setString(2, dto.getBOOK_CATEGORY());
			pstmt.setString(3, dto.getBOOK_NAME());
			pstmt.setString(4, dto.getBOOK_AUTHOR());
			pstmt.setString(5, dto.getBOOK_COMPANY());
			if((row = pstmt.executeUpdate()) == 0) {
				throw new Exception();
			}else {
				conn.commit();
			}
		} catch (Exception e) {
			System.out.println(e);
			try {
				conn.rollback();
			} catch (Exception e2) {}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return row;
	}

	public int deleteBook(bookDTO dto) {
		conn = DataBase.getConnection();
		int row = 0;
		try {
			sql = "select count(*) cnt from borrowlist where BOOK_CODE = ? and state = 'N'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getBOOK_CODE());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				chk = rs.getInt("cnt");
			}
			if(chk == 0) {
				sql = "delete from book where BOOK_CODE = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, dto.getBOOK_CODE());
				row = pstmt.executeUpdate();
			}
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

	public ArrayList<bookDTO> selectBook(String name) {
		conn = DataBase.getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select                ");
		sb.append("      BOOK_CODE            ");
		sb.append("     , BOOK_CATEGORY            ");
		sb.append("      ,BOOK_NAME          ");
		sb.append("      ,BOOK_AUTHOR          ");
		sb.append("      ,BOOK_COMPANY          ");
		sb.append(" from book       ");
		sb.append(" where                ");
		sb.append("         BOOK_NAME=?       ");

		ArrayList<bookDTO> al = new ArrayList<bookDTO>();
		try {

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bookDTO dto = new bookDTO();
				dto.setBOOK_CODE(rs.getInt("BOOK_CODE"));
				dto.setBOOK_CATEGORY(rs.getString("BOOK_CATEGORY"));
				dto.setBOOK_NAME(rs.getString("BOOK_NAME"));
				dto.setBOOK_AUTHOR(rs.getString("BOOK_AUTHOR"));
				dto.setBOOK_COMPANY(rs.getString("BOOK_COMPANY"));
				al.add(dto);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
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
		return al;
	}

}