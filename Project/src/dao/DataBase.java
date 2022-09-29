package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBase {

	public static Connection dbConn;

	public static Connection getConnection() {

		Connection conn = null;

		try {
			String id = "library_user";
			String pw = "1234";
			// String url = "jdbc:mariadb://127.0.0.1:3306/library_db"; // 현재 작업중인 컴 내부 DB
			String url = "jdbc:mariadb://osjpc.iptime.org:523/library_db"; // 내 집 컴 DB 접속할 때 이거 주석 풀고
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);

			System.out.println("Database 연결 성공");

		} catch (Exception e) {
			System.out.println("Database 연결 실패");
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(PreparedStatement stmt, Connection conn) {
		if (stmt != null) {
			try {
				if (!stmt.isClosed())
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				stmt = null;
			}
		}

		if (conn != null) {
			try {
				if (!conn.isClosed())
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}

	public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
		if (rs != null) {
			try {
				if (!rs.isClosed())
					rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				rs = null;
			}
		}

		if (stmt != null) {
			try {
				if (!stmt.isClosed())
					stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				stmt = null;
			}
		}
		if (conn != null) {
			try {
				if (!conn.isClosed())
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn = null;
			}
		}
	}

	public static void main(String[] args) {
		getConnection();
	}
}