package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.DataBase;
import java.awt.Component;

public class BookSearch extends JPanel {
	private JTextField textField;
	private JTable table;
	private JButton deleteButton = null;

	private JScrollPane scrollPane;
	private JComboBox choice;
	private JPanel panel_1;

	private Toolkit tk = Toolkit.getDefaultToolkit();
	private Dimension screenSize = tk.getScreenSize();

	private static String colNames[] = { "도서번호", "분류", "도서명", "저자", "출판사" }; // 테이블 컬럼 값들
	public static DefaultTableModel model = new DefaultTableModel(colNames, 0); // 테이블 데이터 모델 객체 생성

	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;

	private String[] modifyresult = new String[5]; // 수정할 화면 자료들..

	public BookSearch() {
		setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		setAlignmentX(Component.LEFT_ALIGNMENT);
		setBackground(new Color(241,230,208));
		setLayout(null);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(241,230,208));
		contentPane.setBounds(0, 0, 1000, 700);  //0414
		add(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(241,230,208));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(0, 107, 1000, 43);   //0414
		panel_1.setLayout(null);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		

		choice = new JComboBox();
		
		choice.setForeground(new Color(255, 255, 255));
		choice.setBackground(new Color(172, 136, 102));
		choice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		choice.addItem("출판사");
		choice.addItem("저자");
		choice.addItem("도서명");
		choice.setBounds(200, 7, 115, 30);  //0414
		panel_1.add(choice);

		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField.setColumns(10);
		textField.setBounds(330, 7, 350, 30); //0414
		panel_1.add(textField);

		JButton btnNewButton = new JButton("검색");
		btnNewButton.setBackground(new Color(172, 136, 102));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnNewButton.addActionListener(new EventHandler());
		btnNewButton.setActionCommand("btnNewButton");
		btnNewButton.setBounds(695, 7, 90, 30);  //0414
		panel_1.add(btnNewButton);
		
	
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		scrollPane.setBackground(new Color(241, 230, 208));
		scrollPane.getViewport().setBackground(new Color(241, 230, 208));
		
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
		tcmSchedule.getColumn(1).setCellRenderer(tScheduleCellRenderer);
		
		table.setSelectionBackground(new Color(120, 81, 48));
		table.setSelectionForeground(new Color(255,255,255));
		table.setGridColor(new Color(0, 0, 0));
		table.setBackground(new Color(241, 230, 208 ));
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		table.getTableHeader().setBackground(new Color(172, 136, 102));
		table.getTableHeader().setForeground(new Color(255,255,255));
		table.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.addMouseListener(new JTableMouseListener());

		//// this.......
		scrollPane.setBounds(0, 150, 988, 465); //0414
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		table.getSelectedRow();
		table.getTableHeader().setOpaque(false);
		// initialize();
		select();

		JPanel panel = new JPanel();
		panel.setBackground(new Color(241,230,208 ));
		panel.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(0, 0, 1000, 107); //0414
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(BookSearch.class.getResource("/img/text11.png")));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setBounds(407, 28, 200, 50); //0414
		panel.add(label);
		
		
		JLabel labelg = new JLabel();
		labelg.setIcon(new ImageIcon(BookSearch.class.getResource("/img/img1115.png")));
		labelg.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		labelg.setBounds(240, -35, 200, 199); //0414
		panel.add(labelg);
		
		JLabel labelgg = new JLabel();
		labelgg.setIcon(new ImageIcon(BookSearch.class.getResource("/img/222.png")));
		labelgg.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		labelgg.setBounds(215, 10, 75, 37); //0414
		panel.add(labelgg);
		
		JLabel labelgg2 = new JLabel();
		labelgg2.setIcon(new ImageIcon(BookSearch.class.getResource("/img/1120.png")));
		labelgg2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		labelgg2.setBounds(800, 0, 120,120); //0414
		panel.add(labelgg2);
	}

	class EventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("btnNewButton")) {
				model.setRowCount(0);
				select(textField.getText());
				textField.setText("");
			}
		}
	}
	
	private void select(String text) { // 테이블에 보이기 위해 검색

		StringBuffer sb = new StringBuffer();
		if (choice.getSelectedItem() == "출판사") {
			sb.append("select               ");
			sb.append("       BOOK_CODE      ");
			sb.append("      ,BOOK_CATEGORY  ");
			sb.append("      ,BOOK_NAME      ");
			sb.append("      ,BOOK_AUTHOR    ");
			sb.append("      ,BOOK_COMPANY   ");
			sb.append("from book            ");
			sb.append("where                ");
			sb.append("      BOOK_COMPANY like ? ");
		} else if (choice.getSelectedItem() == "저자") {
			sb.append("select               ");
			sb.append("       BOOK_CODE      ");
			sb.append("      ,BOOK_CATEGORY  ");
			sb.append("      ,BOOK_NAME      ");
			sb.append("      ,BOOK_AUTHOR    ");
			sb.append("      ,BOOK_COMPANY   ");
			sb.append("from book            ");
			sb.append("where                ");
			sb.append("      BOOK_AUTHOR like ? ");
		} else if (choice.getSelectedItem() == "도서명") {
			sb.append("select               ");
			sb.append("       BOOK_CODE      ");
			sb.append("      ,BOOK_CATEGORY  ");
			sb.append("      ,BOOK_NAME      ");
			sb.append("      ,BOOK_AUTHOR    ");
			sb.append("      ,BOOK_COMPANY   ");
			sb.append("from book            ");
			sb.append("where                ");
			sb.append("      BOOK_NAME like ? ");
		}

		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, "%" + text + "%");
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

	private class JTableMouseListener extends MouseAdapter { // 마우스로 눌려진값확인하기
		public void mouseClicked(java.awt.event.MouseEvent e) { // 선택된 위치의 값을 출력

			JTable jtable = (JTable) e.getSource();
			int row = jtable.getSelectedRow(); // 선택된 테이블의 행값

			int c1 = jtable.getColumnCount();
			for (int i = 0; i < c1; i++) {
				modifyresult[i] = model.getValueAt(row, i).toString();
			}
			// 선택된 위치의 값을 얻어내서 출력
		}
	}

	public static void select() { // 테이블에 보이기 위해 검색
		model.setRowCount(0);
		String query = "select BOOK_CODE, BOOK_CATEGORY, BOOK_NAME, BOOK_AUTHOR, BOOK_COMPANY from book";
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성

			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(new Object[] { rs.getString("BOOK_CODE"), rs.getString("BOOK_CATEGORY"),
						rs.getString("BOOK_NAME"), rs.getString("BOOK_AUTHOR"), rs.getString("BOOK_COMPANY") });
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