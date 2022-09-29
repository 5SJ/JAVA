package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.NoteDTO;

public class NoteDAO {
	String sql;
	NoteDTO ndto;
	private Connection conn = null;
	private PreparedStatement stmt = null;
	private ResultSet rs = null;
	
	public NoteDAO() {
	
	}
	
	public int note_write(NoteDTO ndto) {
		int res = 0;
		sql = "insert into note(n_sid, n_rid, n_subject, n_contents) values(?, ?, ?, ?)";
		
		conn = DataBase.getConnection();
		
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, ndto.getN_sid());
			stmt.setString(2, ndto.getN_rid());
			stmt.setString(3, ndto.getN_subject());
			stmt.setString(4, ndto.getN_contents());
			if((res = stmt.executeUpdate()) == 0) {
				throw new Exception();
			}else {
				conn.commit();
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			DataBase.close(stmt, conn);
		}
		return res;
	}
	
	public NoteDTO note_read(int index) {
		sql = "update note set n_sort='Y' where n_index = ?";
		conn = DataBase.getConnection();
		
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, index);
			if(stmt.executeUpdate() == 0) {
				throw new Exception();
			}else {
				conn.commit();
				ndto = new NoteDTO();
				sql = "select * from note where n_index = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, index);
				rs = stmt.executeQuery();
				while(rs.next()) {
					ndto.setN_index(rs.getInt("n_index"));
					ndto.setN_sid(rs.getString("n_sid"));
					ndto.setN_rid(rs.getString("n_rid"));
					ndto.setN_subject(rs.getString("n_subject"));
					ndto.setN_contents(rs.getString("n_contents"));
					ndto.setN_date(rs.getTimestamp("n_date"));
					ndto.setN_sort(rs.getString("n_sort"));
				}
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
				DataBase.close(rs, stmt, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ndto;
	}
	
	public int note_del(int index) {
		int res = 0;
		sql = "delete from note where n_index = ?";
		conn = DataBase.getConnection();
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, index);
			res = stmt.executeUpdate();
			if(res != 1) {
				throw new Exception();
			}else {
				conn.commit();
			}
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
				DataBase.close(stmt, conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public ArrayList<NoteDTO> note_list(String id) {
		ArrayList<NoteDTO> nlist = new ArrayList<NoteDTO>();
		sql = "select * from note where n_rid = ? order by n_date desc";
		conn = DataBase.getConnection();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs = stmt.executeQuery();
			while(rs.next()) {
				ndto = new NoteDTO();
				ndto.setN_index(rs.getInt("n_index"));
				ndto.setN_sid(rs.getString("n_sid"));
				ndto.setN_rid(rs.getString("n_rid"));
				ndto.setN_subject(rs.getString("n_subject"));
				ndto.setN_contents(rs.getString("n_contents"));
				ndto.setN_date(rs.getTimestamp("n_date"));
				ndto.setN_sort(rs.getString("n_sort"));
				nlist.add(ndto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBase.close(rs, stmt, conn);
		}
		return nlist;
	}

	public int getNewNote(String loginID) {
		sql = "select count(*) as cnt from note where n_sort = 'N' and n_rid = ?";
		conn = DataBase.getConnection();
		int res = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, loginID);
			rs = stmt.executeQuery();
			while(rs.next()) {
				res = rs.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataBase.close(rs, stmt, conn);
		}
		return res;
	}
}