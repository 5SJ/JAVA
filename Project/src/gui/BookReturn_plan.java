package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.beans.EventHandler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.DataBase;

public class BookReturn_plan extends JPanel {

	private JTable table;
	private JTable table_1;
	public DefaultTableModel model; // 테이블 데이터 모델 객체 생성
	public DefaultTableModel model2; // 테이블 데이터 모델 객체 생성
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/**
	 * Create the panel.
	 */
	public BookReturn_plan() {
		setLayout(null);
		setBackground(new Color(241, 230, 208));
		JLabel lblNewLabel = new JLabel("금일반납예정도서");
		lblNewLabel.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/rmadlf2.png")));
		lblNewLabel.setBounds(404, 10, 200, 45);
		add(lblNewLabel);

		JLabel label = new JLabel("반납예정도서");
		label.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/rmadlf.png")));
		label.setBounds(420, 265, 160, 45);
		add(label);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new Color(241, 230, 208));
		scrollPane.getViewport().setBackground(new Color(241, 230, 208));
		scrollPane.setBounds(45, 60, 900, 200);
		add(scrollPane);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBackground(new Color(241, 230, 208));
		scrollPane_1.getViewport().setBackground(new Color(241, 230, 208));
		scrollPane_1.setBounds(45, 310, 900, 270);
		add(scrollPane_1);

		model = new DefaultTableModel(new String[] { "아이디", "이름", "도서명", "도서번호", "저자명", "출판사" }, 0);
		table = new JTable(model);
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.setRowHeight(20);
		table.setSelectionBackground(new Color(120, 81, 48));
		table.setSelectionForeground(new Color(255, 255, 255));
		table.getTableHeader().setBackground(new Color(172, 136, 102));
		table.getTableHeader().setForeground(Color.white);
		table.setBackground(new Color(241, 230, 208));
		table.setGridColor(new Color(0, 0, 0));
		table.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {

			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);

			}
		scrollPane.setViewportView(table);

		model2 = new DefaultTableModel(
				new String[] { "아이디", "이름", "도서명", "도서번호", "저자명", "출판사", "대출일자", "반납일자" }, 0);
		table_1 = new JTable(model2);
		table_1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table_1.setRowHeight(20);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(40);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(10);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(40);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(50);
		
		table_1.setSelectionBackground(new Color(120, 81, 48));
		table_1.setSelectionForeground(new Color(255, 255, 255));
		table_1.getTableHeader().setBackground(new Color(172, 136, 102));
		table_1.getTableHeader().setForeground(Color.white);
		table_1.setBackground(new Color(241, 230, 208));
		table_1.setGridColor(new Color(0, 0, 0));
		table_1.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		scrollPane.setViewportView(table);
		
		scrollPane_1.setViewportView(table_1);
		select();
		select2();
	}

	private void select() { // 테이블에 보이기 위해 검색

		StringBuffer sb = new StringBuffer();
		sb.append("select                     ");
		sb.append("       management.M_ID 	         ");
		sb.append("      ,management.M_NAME	         ");
		sb.append("      ,book.BOOK_NAME      ");
		sb.append("      ,book.BOOK_CODE      ");
		sb.append("      ,book.BOOK_AUTHOR    ");
		sb.append("      ,book.BOOK_COMPANY   ");
		sb.append("from book,borrowlist,management  ");
		sb.append("where                       ");
		sb.append("       borrowlist.return_DATE=to_char(sysdate(),'yyyy-mm-dd')");
		sb.append("       and borrowlist.BOOK_CODE=book.BOOK_CODE");
		sb.append("       and management.M_ID=borrowlist.cust_id");

		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성

			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(
						new Object[] { rs.getString("M_ID"), rs.getString("M_NAME"), rs.getString("BOOK_NAME"),
								rs.getString("BOOK_CODE"), rs.getString("BOOK_AUTHOR"), rs.getString("BOOK_COMPANY") });
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close(); // 객체 생성한 반대 순으로 사용한 객체는 닫아준다.
			} catch (Exception e2) {
			}
		}
	}

	private void select2() { // 테이블에 보이기 위해 검색

		StringBuffer sb = new StringBuffer();
		sb.append("select                     ");
		sb.append("       management.M_ID 	         ");
		sb.append("      ,management.M_NAME	         ");
		sb.append("      ,book.BOOK_NAME      ");
		sb.append("      ,book.BOOK_CODE      ");
		sb.append("      ,book.BOOK_AUTHOR    ");
		sb.append("      ,book.BOOK_COMPANY   ");
		sb.append("      ,BORROW_DATE   ");
		sb.append("      ,RETURN_DATE   ");
		sb.append("      ,book.BOOK_COMPANY   ");
		sb.append("from book,borrowlist,management  ");
		sb.append("where                       ");
		sb.append("         borrowlist.BOOK_CODE=book.BOOK_CODE");
		sb.append("       and management.M_ID=borrowlist.cust_id");

		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성

			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model2.addRow(new Object[] { rs.getString("M_ID"), rs.getString("M_NAME"),
						rs.getString("BOOK_NAME"), rs.getString("BOOK_CODE"), rs.getString("BOOK_AUTHOR"),
						rs.getString("BOOK_COMPANY"), rs.getString("BORROW_DATE"), rs.getString("RETURN_DATE") });
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close(); // 객체 생성한 반대 순으로 사용한 객체는 닫아준다.
			} catch (Exception e2) {
			}
		}
	}
}