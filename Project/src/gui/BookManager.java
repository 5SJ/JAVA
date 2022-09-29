package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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

public class BookManager extends JPanel {
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

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String[] modifyresult = new String[5]; // 수정할 화면 자료들..

	public BookManager() {
		setLayout(null);

		JPanel contentPane = new JPanel();
		contentPane.setBounds(0,0, 1000, 700);
		contentPane.setBackground(new Color(241,230,208));
		add(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBackground(new Color(241,230,208));
		panel_1.setBounds(0, 100, 987, 43);
		panel_1.setLayout(null);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		choice = new JComboBox();
		choice.setForeground(Color.WHITE);
		choice.setBackground(new Color(172, 136, 102));
		choice.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		choice.addItem("출판사");
		choice.addItem("저자");
		choice.addItem("도서명");
		choice.setBounds(170, 7, 115, 30);
		panel_1.add(choice);

		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField.setColumns(10);
		textField.setBounds(300, 7, 300, 30);
		panel_1.add(textField);

		JButton btnNewButton = new JButton("검색");
		btnNewButton.addActionListener(new EventHandler());
		btnNewButton.setForeground(Color.white);
		btnNewButton.setActionCommand("btnNewButton");
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		btnNewButton.setBackground(new Color(172,136,102));
		btnNewButton.setBounds(610, 7, 90, 30);
		panel_1.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("도서등록");
		btnNewButton_1.setForeground(Color.white);
		btnNewButton_1.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		btnNewButton_1.setBackground(new Color(172,136,102));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookEnroll be = new BookEnroll();
				be.setVisible(true);
				be.setLocation((screenSize.width - be.getWidth()) / 2, (screenSize.height - be.getHeight()) / 2);
			}
		});
		btnNewButton_1.setBounds(710, 7, 130, 30);
		panel_1.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("수정/삭제");
		btnNewButton_2.setBackground(new Color(172,136,102));
		btnNewButton_2.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		btnNewButton_2.setForeground(Color.white);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// 생성자를 호출하는 곳!!! 새로운 화면에서 수정을 처리...
				System.out.println(modifyresult[0]);
				System.out.println(modifyresult[1]);
				System.out.println(modifyresult[2]);
				System.out.println(modifyresult[3]);
				System.out.println(modifyresult[4]);

				BookModify d = new BookModify(modifyresult);
				d.setVisible(true);
				d.setLocation((screenSize.width - d.getWidth()) / 2, (screenSize.height - d.getHeight()) / 2);
			}
		});
		btnNewButton_2.setBounds(850, 7, 130, 30);
		panel_1.add(btnNewButton_2);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		scrollPane.getViewport().setBackground(new Color(241, 230, 208 ));
		scrollPane.setBackground(new Color(241, 230, 208 ));
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
		tcmSchedule.getColumn(1).setCellRenderer(tScheduleCellRenderer);
		table.addMouseListener(new JTableMouseListener());
		table.setSelectionBackground(new Color(120, 81, 48));
		table.setSelectionForeground(new Color(255, 255, 255));
		table.setGridColor(new Color(0, 0, 0));
		table.setBackground(new Color(241, 230, 208 ));
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		table.getTableHeader().setBackground(new Color(172,136,102));
		table.getTableHeader().setForeground(new Color(241, 230, 208));
		table.getTableHeader().setFont(new Font("맑은 고딕",Font.PLAIN,15));
		
		//// this.......
		scrollPane.setBounds(0,140, 987, 475);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(table);
		table.getSelectedRow();
		// initialize();
		model.setRowCount(0);
		select();

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 0, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(0, 0, 987, 107);
		panel.setBackground(new Color(241,230,208));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(BookManager.class.getResource("/img/img.png")));
		label.setBounds(390, 20, 200, 62);
		panel.add(label);
	}

	class EventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("btnNewButton")) {
				model.setRowCount(0);
				select(textField.getText());
			}
		}
	}

	private void select(String text) { // 테이블에 보이기 위해 검색
		StringBuffer sb = new StringBuffer();
		sb.append("select               ");
		sb.append("       BOOK_CODE      ");
		sb.append("      ,BOOK_CATEGORY  ");
		sb.append("      ,BOOK_NAME      ");
		sb.append("      ,BOOK_AUTHOR    ");
		sb.append("      ,BOOK_COMPANY   ");
		sb.append("from book            ");
		sb.append("where                ");
		if (choice.getSelectedItem() == "출판사") {
			sb.append("      BOOK_COMPANY like ? ");
		} else if (choice.getSelectedItem() == "저자") {
			sb.append("      BOOK_AUTHOR like ? ");
		} else if (choice.getSelectedItem() == "도서명") {
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
				} catch (Exception e) {}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception e) {}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {}
		}
	}

	private class JTableMouseListener extends MouseAdapter{ // 마우스로 눌려진값확인하기
		public void mouseClicked(MouseEvent e) { // 선택된 위치의 값을 출력

			JTable jtable = (JTable) e.getSource();
			int row = jtable.getSelectedRow(); // 선택된 테이블의 행값

			int c1 = jtable.getColumnCount();
			for (int i = 0; i < c1; i++) {
				modifyresult[i] = model.getValueAt(row, i).toString();
			}
			// 선택된 위치의 값을 얻어내서 출력
		}
	}

	public void select() { // 테이블에 보이기 위해 검색
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