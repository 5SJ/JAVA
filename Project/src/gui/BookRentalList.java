package gui;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.DataBase;
import dao.borrowDAO;
import dto.borrowDTO;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;

public class BookRentalList extends JPanel {
	public JTextField textField_2;
	private JTable table;
	public static DefaultTableModel model; // 테이블 데이터 모델 객체 생성
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	String id;
	static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	/**
	 * Create the panel.
	 * @param id 
	 */
	public BookRentalList(String id) {
		setBackground(new Color(241, 230, 208));
		this.id = id;
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(241,230,208));
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(0, 0, 1000, 107);   //0414
		add(panel);
		panel.setLayout(null);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(BookRentalList.class.getResource("/img/text13.png")));
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setBounds(402, 28, 201, 55);  //0414
		panel.add(label_1);
		
		JLabel labelg = new JLabel();
		labelg.setIcon(new ImageIcon(BookSearch.class.getResource("/img/1118.png")));
		labelg.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		labelg.setBounds(0, -90, 230, 230); //0414
		panel.add(labelg);
		
		JLabel labelgg = new JLabel();
		labelgg.setIcon(new ImageIcon(BookSearch.class.getResource("/img/223.png")));
		labelgg.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		labelgg.setBounds(160, 10, 85, 37); //0414
		panel.add(labelgg);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		scrollPane.setBackground(new Color(241,230,208));
		scrollPane.getViewport().setBackground(new Color(241, 230, 208));
		scrollPane.setBounds(0,107, 987, 560);   //0414
		add(scrollPane);
		model = new DefaultTableModel(new String[] {"도서번호", "도서명", "저자", "출판사", "대출일", "반납일" }, 0);

		table = new JTable(model);
		table.setRowHeight(25);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();
		tcmSchedule.getColumn(4).setCellRenderer(tScheduleCellRenderer);
		tcmSchedule.getColumn(5).setCellRenderer(tScheduleCellRenderer);
		
		table.setSelectionBackground(new Color(172, 136, 102));
		table.setSelectionForeground(new Color(255,255,255));
		table.setGridColor(new Color(0, 0, 0));
		table.setBackground(new Color(241, 230, 208 ));
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		table.getTableHeader().setBackground(new Color(120, 81, 48));
		table.getTableHeader().setForeground(new Color(255,255,255));
		table.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.getTableHeader().setOpaque(false);
		select(id);
		scrollPane.setViewportView(table);
	}
	
	// 대출기록
	static void select(String id) { // 테이블에 보이기 위해 검색
		model.setRowCount(0);
		String sql = 
				"SELECT * FROM book LEFT JOIN borrowlist ON"
				+ " book.book_code = borrowlist.book_code WHERE borrowlist.INDEX IS NOT NULL AND state = 'N' and cust_id = ?";
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(new Object[] {rs.getString("BOOK_CODE"),
						rs.getString("BOOK_NAME"), rs.getString("BOOK_AUTHOR"), rs.getString("BOOK_COMPANY"),
						sdf.format(rs.getTimestamp("BORROW_DATE")), sdf.format(rs.getTimestamp("RETURN_DATE"))});
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DataBase.close(rs, pstmt, conn);
		}
	}
}