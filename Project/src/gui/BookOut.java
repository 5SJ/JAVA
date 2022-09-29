package gui;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BookOut extends JPanel {
	JTextField textField, textField_1, textField_2, textField_3, textField_4;
	JTable table;
	DefaultTableModel model; // 테이블 데이터 모델 객체 생성
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String selectID = null, code, date;
	SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

	/**
	 * Create the panel.
	 */
	public BookOut() {
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(0, 0, 987, 107);
		panel.setBackground(new Color(241, 230, 208));
		add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(BookOut.class.getResource("/img/textimg41.png")));
		label.setBounds(410, 30, 194, 49);
		panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(241, 230, 208));
		panel_1.setBounds(0, 108, 987, 167);
		add(panel_1);
		panel_1.setLayout(null);

//		JLabel lblNewLabel = new JLabel("이름");
//		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 27));
//		lblNewLabel.setBounds(61, 25, 83, 45);
//		panel_1.add(lblNewLabel);

//		textField = new JTextField();
//		textField.setBorder(null);
//		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
//		textField.setBounds(176, 30, 170, 35);
//		panel_1.add(textField);
//		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("도서명");
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 27));
		lblNewLabel_1.setBounds(61, 56, 103, 45);
		panel_1.add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setBorder(null);
		textField_1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField_1.setBounds(176, 61, 170, 35);
		panel_1.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("아이디");
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.PLAIN, 27));
		lblNewLabel_2.setBounds(500, 25, 97, 45);
		panel_1.add(lblNewLabel_2);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField_2.setEditable(false);
		textField_2.setBounds(620, 30, 170, 35);
		panel_1.add(textField_2);
		textField_2.setColumns(10);

//		JButton name_search = new JButton("검색");
//		name_search.setBorder(null);
//		name_search.setFont(new Font("맑은 고딕", Font.BOLD, 19));
//		name_search.setBounds(358, 30, 73, 35);
//		name_search.setBackground(new Color(172, 136, 102));
//		name_search.setForeground(new Color(241, 230, 208));
//		name_search.addActionListener(new EventHandler(this));
//		name_search.setActionCommand("name_search");
//		panel_1.add(name_search);

		JButton book_search = new JButton("검색");
		book_search.setBorder(null);
		book_search.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		book_search.setBackground(new Color(172, 136, 102));
		book_search.setForeground(new Color(241, 230, 208));
		book_search.setBounds(358, 61, 73, 35);
		book_search.addActionListener(new EventHandler(this));
		book_search.setActionCommand("book_search");
		panel_1.add(book_search);

		JLabel lblNewLabel_3 = new JLabel("도서번호");
		lblNewLabel_3.setFont(new Font("맑은 고딕", Font.PLAIN, 27));
		lblNewLabel_3.setBounds(500, 95, 137, 45);
		panel_1.add(lblNewLabel_3);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField_3.setEditable(false);
		textField_3.setBounds(620, 100, 170, 35);
		panel_1.add(textField_3);
		textField_3.setColumns(10);

		JButton out = new JButton("반납");
		out.addActionListener(new EventHandler(this));
		out.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		out.setBackground(new Color(172, 136, 102));
		out.setForeground(new Color(241, 230, 208));
		out.setActionCommand("out");
		out.setBounds(870, 100, 100, 50);
		panel_1.add(out);

		JButton contiBtn = new JButton("연장");
		contiBtn.addActionListener(new EventHandler(this));
		contiBtn.setActionCommand("continue");
		contiBtn.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		contiBtn.setBackground(new Color(120, 81, 48));
		contiBtn.setForeground(new Color(241, 230, 208));
		contiBtn.setBounds(870, 15, 100, 50);
		panel_1.add(contiBtn);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBackground(new Color(120, 81, 48));
		panel_2.setBounds(0, 275, 987, 70);
		add(panel_2);
		panel_2.setLayout(null);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(BookOut.class.getResource("/img/textimg43.png")));
		label_1.setBounds(422, 11, 145, 45);
		panel_2.add(label_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		scrollPane.setBounds(0, 345, 987, 270);
		scrollPane.getViewport().setBackground(new Color(241, 230, 208));
		add(scrollPane);
		model = new DefaultTableModel(new String[] { "아이디", "도서번호", "도서명", "저자", "출판사", "대출일", "반납일" }, 0);

		table = new JTable(model);
		table.setRowHeight(23);
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();
		tcmSchedule.getColumn(0).setCellRenderer(tScheduleCellRenderer);
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		table.addMouseListener(new JTableMouseListener());
		table.setSelectionBackground(new Color(120, 81, 48));
		table.setSelectionForeground(new Color(255, 255, 255));
		table.getTableHeader().setBackground(new Color(172, 136, 102));
		table.getTableHeader().setForeground(Color.white);
		table.setBackground(new Color(241, 230, 208));
		table.setGridColor(new Color(0, 0, 0));
		table.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		select();
		scrollPane.setViewportView(table);

	}

	class EventHandler implements ActionListener {
		private BookOut book;

		public EventHandler(BookOut book) {
			this.book = book;
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("name_search")) {
				SearchMember name = new SearchMember(book);
				name.select(textField.getText().toString());
				name.setVisible(true);
			} else if (e.getActionCommand().equals("book_search")) {
				model.setRowCount(0);
				select(textField_1.getText());
				textField_1.setText("");
			} else if (e.getActionCommand().equals("out")) {
				if (selectID != null) {
					borrowDTO dto = new borrowDTO();
					dto.setId(selectID);
					dto.setBOOK_CODE(Integer.parseInt(code));
					borrowDAO dao = new borrowDAO();
					int res = dao.deleteBorrow(dto);
					if (res == 1) {
						JOptionPane.showMessageDialog(null, selectID + " 님 반납완료", "반납완료",
								JOptionPane.INFORMATION_MESSAGE);
						model.setRowCount(0);
						select();
						textField_2.setText("");
						textField_3.setText("");
						selectID = null;
					}

					else {
						JOptionPane.showMessageDialog(null, "다시 시도해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					}

				}

				else {
					JOptionPane.showMessageDialog(null, "목록에서 선택해주세요.", "반납오류", JOptionPane.ERROR_MESSAGE);
				}

			} else if (e.getActionCommand().equals("continue")) {

				if (selectID != null) {
					borrowDAO dao = new borrowDAO();
					borrowDTO dto = new borrowDTO();
					dto.setId(selectID);
					dto.setBOOK_CODE(Integer.parseInt(code));
					int res = dao.dateUpdate(dto);
					if (res == 1) {
						JOptionPane.showMessageDialog(null, selectID + " 님 반납기간 연장 완료", "연장완료",
								JOptionPane.PLAIN_MESSAGE);
						model.setRowCount(0);
						select();
						textField_2.setText("");
						textField_3.setText("");
						selectID = null;
					} 
					
					else {
						JOptionPane.showMessageDialog(null, "다시 시도해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
					}
				} 
				
				else {
					JOptionPane.showMessageDialog(null, "목록에서 선택해주세요.", "연장오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private class JTableMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			JTable jtable = (JTable) e.getSource();
			int row = jtable.getSelectedRow();
			code = model.getValueAt(row, 1).toString();
			textField_3.setText(code);
			selectID = model.getValueAt(row, 0).toString();
			textField_2.setText(selectID);
			date = model.getValueAt(row, 5).toString();
			System.out.println(date);
		}
	}

	public void setData(String str) {
		textField_2.setText(str);
	}

	private void select() { // 테이블에 보이기 위해 검색
		String sql = "SELECT * FROM book LEFT JOIN borrowlist ON book.book_code = borrowlist.book_code WHERE borrowlist.INDEX IS NOT NULL AND state = 'N'";
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성

			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(new Object[] { rs.getString("cust_id"), rs.getString("BOOK_CODE"),
						rs.getString("BOOK_NAME"), rs.getString("BOOK_AUTHOR"), rs.getString("BOOK_COMPANY"),
						sdf.format(rs.getTimestamp("BORROW_DATE")), sdf.format(rs.getTimestamp("RETURN_DATE")) });
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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

	private void select(String BOOK_NAME) { // 테이블에 보이기 위해 검색
		String sql = "SELECT * FROM book LEFT JOIN borrowlist ON book.book_code = borrowlist.book_code WHERE borrowlist.INDEX IS NOT NULL AND state = 'N' and book_name like ?";
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + BOOK_NAME + "%");
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(new Object[] { rs.getString("cust_id"), rs.getString("BOOK_CODE"),
						rs.getString("BOOK_NAME"), rs.getString("BOOK_AUTHOR"), rs.getString("BOOK_COMPANY"),
						sdf.format(rs.getTimestamp("BORROW_DATE")), sdf.format(rs.getTimestamp("RETURN_DATE")) });
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
}