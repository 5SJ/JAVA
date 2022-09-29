package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.DataBase;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchMember extends JDialog {

	private static String[] colNames = { "아이디", "이름", "핸드폰번호" }; // 테이블 컬럼 값들
	public static DefaultTableModel model; // 테이블 데이터 모델 객체 생성
	private java.sql.Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null; // 리턴받아 사용할 객체 생성 ( select에서 보여줄 때 필요 )
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	public String result;
	private BookRental book;
	private BookOut book2;

	/**
	 * Create the dialog.
	 */
	public SearchMember(String id) {
		setBounds(100, 100, 450, 300);
		contentPanel.setBackground(new Color(120, 81, 48));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("회원정보");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		lblNewLabel.setBounds(170, 10, 108, 30);
		contentPanel.add(lblNewLabel);
		setContentPane(contentPanel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(new Color(241, 230, 208));
		scrollPane.setBackground(new Color(241, 230, 208));
		scrollPane.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		scrollPane.setBounds(12, 44, 410, 208);
		contentPanel.add(scrollPane);
		String[] colName = { "아이디", "이름"};
		
		model = new DefaultTableModel(colName, 0);
		table = new JTable(model);
		table.setRowHeight(20);
		table.setSelectionBackground(new Color(172, 136, 102));
		table.setSelectionForeground(new Color(255, 255, 255));
		table.getTableHeader().setBackground(new Color(172, 136, 102));
		table.getTableHeader().setForeground(Color.white);
		table.setBackground(new Color(241, 230, 208));
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.addMouseListener(new JTableMouseListener2());
		scrollPane.setViewportView(table);
		setLocationRelativeTo(null);
		select2(id);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);

	}
	
	public SearchMember(BookRental book) {
		this.book = book;
		setBounds(100, 100, 450, 300);
		contentPanel.setBackground(new Color(120, 81, 48));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("회원정보");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		lblNewLabel.setBounds(170, 10, 108, 30);
		contentPanel.add(lblNewLabel);
		setContentPane(contentPanel);


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(new Color(241, 230, 208));
		scrollPane.setBackground(new Color(241, 230, 208));
		scrollPane.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		scrollPane.setBounds(12, 44, 410, 208);
		contentPanel.add(scrollPane);

		model = new DefaultTableModel(colNames, 0);
		table = new JTable(model);
		table.setRowHeight(20);
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.setSelectionBackground(new Color(172, 136, 102));
		table.setSelectionForeground(new Color(255, 255, 255));
		table.getTableHeader().setBackground(new Color(172, 136, 102));
		table.getTableHeader().setForeground(Color.white);
		table.setBackground(new Color(241, 230, 208));
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.addMouseListener(new JTableMouseListener());
		scrollPane.setViewportView(table);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);

	}

	public SearchMember(BookOut book) {
		this.book2 = book;
		setBounds(100, 100, 450, 300);
		contentPanel.setBackground(new Color(120, 81, 48));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("회원정보");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		lblNewLabel.setBounds(170, 10, 108, 30);
		contentPanel.add(lblNewLabel);
		setContentPane(contentPanel);


		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().setBackground(new Color(241, 230, 208));
		scrollPane.getViewport().setBackground(new Color(241, 230, 208));
		scrollPane.setBackground(new Color(241, 230, 208));
		scrollPane.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		scrollPane.setBounds(12, 44, 410, 208);
		contentPanel.add(scrollPane);

		model = new DefaultTableModel(colNames, 0);
		table = new JTable(model);
		table.setRowHeight(20);
		table.setSelectionBackground(new Color(172, 136, 102));
		table.setSelectionForeground(new Color(255, 255, 255));
		table.getTableHeader().setBackground(new Color(172, 136, 102));
		table.getTableHeader().setForeground(Color.white);
		table.setBackground(new Color(241, 230, 208));
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.addMouseListener(new JTableMouseListener1());
		scrollPane.setViewportView(table);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);

	}

	public void select(String member_name) { // 테이블에 보이기 위해 검색
		String sql = "select * from management where m_name like ? and m_state = 1";
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + member_name + "%");
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(new Object[] { rs.getString("M_ID"), rs.getString("M_NAME"), rs.getString("M_TEL") });
			}
		} catch (Exception e) {
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
	}
	
	public void select2(String id) { // 테이블에 보이기 위해 검색
		String sql = "select * from management where m_state = 1 and m_id not in(?)";
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(new Object[] { rs.getString("M_ID"), rs.getString("M_NAME")});
			}
		} catch (Exception e) {
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
	}
	private class JTableMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			JTable jtable = (JTable) e.getSource();
			int row = jtable.getSelectedRow();
			result = model.getValueAt(row, 0).toString();
			System.out.println(result);
			// new BookRental();
			book.textField.setText(result);
			dispose();
		}
	}

	private class JTableMouseListener1 extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			JTable jtable = (JTable) e.getSource();
			int row = jtable.getSelectedRow();
			result = model.getValueAt(row, 0).toString();
			System.out.println(result);
			//new BookOut();
			book2.textField_2.setText(result);
			dispose();
		}
	}
	
	private class JTableMouseListener2 extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			JTable jtable = (JTable) e.getSource();
			int row = jtable.getSelectedRow();
			result = model.getValueAt(row, 0).toString();
			System.out.println(result);
			NoteWriteFrame.nametf.setText(result);
			dispose();
		}
	}
}
