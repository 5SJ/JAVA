package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.MatteBorder;
import java.awt.Color;

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

public class BookRental extends JPanel {
	public JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTable table;
	public DefaultTableModel model; // 테이블 데이터 모델 객체 생성
	private JButton name_search;
	private JButton book_search;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/**
	 * Create the panel.
	 */
	public BookRental() {
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(0, 0, 987, 107);
		panel.setBackground(new Color(241, 230, 208));
		add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/img11.png")));
		lblNewLabel.setBounds(410, 30, 194, 49);
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBackground(new Color(241, 230, 208));
		panel_1.setBounds(0, 108, 987, 167);
		add(panel_1);

		JLabel label_4 = new JLabel("이름");
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 27));
		label_4.setBounds(61, 25, 83, 45);
		panel_1.add(label_4);

		textField_4 = new JTextField();
		textField_4.setBorder(null);
		textField_4.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField_4.setColumns(10);
		textField_4.setBounds(176, 100, 170, 35);
		panel_1.add(textField_4);

		name_search = new JButton("검색"); // 이름검색
		name_search.addActionListener(new EventHandler(this));
		name_search.setActionCommand("name_search");
		name_search.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		name_search.setBackground(new Color(172, 136, 102));
		name_search.setForeground(new Color(241, 230, 208));
		name_search.setBounds(358, 30, 73, 35);
		panel_1.add(name_search);

		JLabel label = new JLabel("아이디");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 27));
		label.setBounds(500, 25, 97, 45);
		panel_1.add(label);

		textField = new JTextField();
		textField.setSelectionColor(new Color(120, 81, 48));
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(650, 30, 170, 35);
		panel_1.add(textField);

		JLabel label_1 = new JLabel("도서명");
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 27));
		label_1.setBounds(61, 95, 103, 45);
		panel_1.add(label_1);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField_2.setBorder(null);
		textField_2.setColumns(10);
		textField_2.setBounds(176, 30, 170, 35);
		panel_1.add(textField_2);

		book_search = new JButton("검색");
		book_search.addActionListener(new EventHandler(this));
		book_search.setActionCommand("book_search");
		book_search.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		book_search.setBackground(new Color(172, 136, 102));
		book_search.setForeground(new Color(241, 230, 208));
		book_search.setBounds(358, 100, 73, 35);
		panel_1.add(book_search);

		JLabel label_2 = new JLabel("도서번호");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 27));
		label_2.setBounds(500, 100, 137, 45);
		panel_1.add(label_2);

		textField_1 = new JTextField();
		textField_1.setSelectionColor(new Color(120, 81, 48));
		textField_1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField_1.setColumns(10);
		textField_1.setBounds(650, 100, 170, 35);
		textField_1.setEditable(false);
		panel_1.add(textField_1);

		JButton rental = new JButton("대출");
		rental.setBounds(860, 90, 100, 50);
		rental.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		rental.setBackground(new Color(120, 81, 48));
		rental.setForeground(new Color(241, 230, 208));
		rental.addActionListener(new EventHandler(this));
		rental.setActionCommand("rental");
		panel_1.add(rental);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBounds(0, 275, 987, 70);
		panel_2.setBackground(new Color(120, 81, 48));
		add(panel_2);
		panel_2.setLayout(null);

		JLabel label_5 = new JLabel("도서현황");
		label_5.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/text41.png")));
		label_5.setBounds(410, 8, 160, 50);
		panel_2.add(label_5);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		scrollPane.setBounds(0, 345, 987, 270);
		scrollPane.setBackground(new Color(241, 230, 208));
		scrollPane.getViewport().setBackground(new Color(241, 230, 208));
		add(scrollPane);
		model = new DefaultTableModel(new String[] { "도서번호", "분류", "도서명", "저자", "출판사" }, 0);

		table = new JTable(model);
		table.setRowHeight(23);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();
		tcmSchedule.getColumn(0).setCellRenderer(tScheduleCellRenderer);
		tcmSchedule.getColumn(1).setCellRenderer(tScheduleCellRenderer);
		table.addMouseListener(new JTableMouseListener());
		table.setSelectionBackground(new Color(120, 81, 48));
		table.setSelectionForeground(new Color(255, 255, 255));
		table.getTableHeader().setBackground(new Color(172, 136, 102));
		table.getTableHeader().setForeground(Color.white);
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.setBackground(new Color(241, 230, 208));
		table.setGridColor(new Color(0, 0, 0));
		table.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		select();
		scrollPane.setViewportView(table);
	}

	class EventHandler implements ActionListener {
		private BookRental book;

		public EventHandler(BookRental book) {
			this.book = book;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("name_search")) {
				SearchMember name = new SearchMember(book);
				name.select(textField_2.getText().toString());
				textField_2.setText("");
				name.setVisible(true);
			} else if (e.getActionCommand().equals("book_search")) {
				model.setRowCount(0);
				select(textField_4.getText());
				textField_4.setText("");
			} else if (e.getActionCommand().equals("rental")) {
				if (textField_1.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "책과 회원을 선택해주세요", "그린도서관 대출", JOptionPane.INFORMATION_MESSAGE);
				} else if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "책과 회원을 선택해주세요", "그린도서관 대출", JOptionPane.INFORMATION_MESSAGE);
				}else {
					borrowDTO dto = new borrowDTO();
					dto.setBOOK_CODE(Integer.parseInt(textField_1.getText()));
					dto.setId(textField.getText());
					borrowDAO dao = new borrowDAO();
					if(dao.insertBorrow(dto) == 1) {
						JOptionPane.showMessageDialog(null, "대출완료", "그린도서관 대출", JOptionPane.INFORMATION_MESSAGE);
						textField.setText("");
						textField_1.setText("");
					}
				} 
			}
		}
	}

	private class JTableMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			JTable jtable = (JTable) e.getSource();
			int row = jtable.getSelectedRow();
			String str = model.getValueAt(row, 0).toString();
			textField_1.setText(str);
		}
	}

	public void setData(String str) {
		textField.setText(str);
	}

	private void select() { // 테이블에 보이기 위해 검색
		String sql = "select * from book";
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성

			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(new Object[] { rs.getString("BOOK_CODE"), rs.getString("BOOK_CATEGORY"),
						rs.getString("BOOK_NAME"), rs.getString("BOOK_AUTHOR"), rs.getString("BOOK_COMPANY") });
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
		String sql = "select * from book where book_name like ?";
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + BOOK_NAME + "%");
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(new Object[] { rs.getString("BOOK_CODE"), rs.getString("BOOK_CATEGORY"),
						rs.getString("BOOK_NAME"), rs.getString("BOOK_AUTHOR"), rs.getString("BOOK_COMPANY") });
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