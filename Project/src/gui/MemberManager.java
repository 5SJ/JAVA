package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.DataBase;

public class MemberManager extends JPanel {
	private JTextField textField;
	public JTable table;
	private static String colNames[] = { "아이디", "비밀번호", "이름", "성별", "이메일", "핸드폰번호", "생성일", "정보" }; // 테이블 컬럼 값들
	public static DefaultTableModel model; // 테이블 데이터 모델 객체 생성

	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null; // 리턴받아 사용할 객체 생성 ( select에서 보여줄 때 필요 )
	private JScrollPane scrollPane_1;
	private JComboBox<String> comboBox;
	private String[] modifyresult = new String[8];
	static String state, sql;
	static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

	/**
	 * Create the panel.
	 */
	public MemberManager() {
		setLayout(null);
		setBackground(new Color(241, 230, 208));

		JPanel contentPane = new JPanel();
		contentPane.setBounds(0, 0, 1000, 700);
		contentPane.setBackground(new Color(241, 230, 208));
		add(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBackground(new Color(241, 230, 208));
		panel.setBounds(0, 0, 987, 107);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(BookManager.class.getResource("/img/img2.png")));
		label.setBounds(390, 13, 200, 62);
		panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(241, 230, 208));
		panel_1.setBounds(0, 100, 987, 43);
		contentPane.add(panel_1);
		contentPane.add(panel);

		comboBox = new JComboBox<String>();
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setBackground(new Color(172, 136, 102));
		comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "이름", "번호" }));
		comboBox.setBounds(170, 7, 115, 30);
		panel_1.add(comboBox);

		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		textField.setBounds(300, 7, 300, 30);
		panel_1.add(textField);
		textField.setColumns(10);

		JButton button = new JButton("검색");
		button.addActionListener(new EventHandler());
		button.setForeground(Color.white);
		button.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		button.setBackground(new Color(172, 136, 102));
		button.setActionCommand("search");
		button.setBounds(610, 7, 90, 30);
		panel_1.add(button);

		JButton btnNewButton_1 = new JButton("수정");
		btnNewButton_1.addActionListener(new EventHandler());
		btnNewButton_1.setForeground(Color.white);
		btnNewButton_1.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		btnNewButton_1.setActionCommand("re");
		btnNewButton_1.setBackground(new Color(172, 136, 102));
		btnNewButton_1.setBounds(750, 7, 90, 30);
		panel_1.add(btnNewButton_1);

		JButton button_1 = new JButton("회원등록");
		button_1.addActionListener(new EventHandler());
		button_1.setBackground(new Color(172, 136, 102));
		button_1.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		button_1.setForeground(Color.white);
		button_1.setActionCommand("Enroll");
		button_1.setBounds(850, 7, 130, 30);
		panel_1.add(button_1);

		model = new DefaultTableModel(colNames, 0);
		table = new JTable(model);
		table.setRowHeight(23);
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
		table.addMouseListener(new JTableMouseListener());
		table.setSelectionBackground(new Color(120, 81, 48));
		table.setSelectionForeground(new Color(255, 255, 255));
		table.setGridColor(new Color(0, 0, 0));
		table.setBackground(new Color(241, 230, 208));
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		table.getTableHeader().setBackground(new Color(172, 136, 102));
		table.getTableHeader().setForeground(new Color(241, 230, 208));
		table.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 15));

		scrollPane_1 = new JScrollPane(table);
		scrollPane_1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		scrollPane_1.setBounds(0, 140, 987, 475);
		scrollPane_1.setBackground(new Color(241, 230, 208));
		scrollPane_1.getViewport().setBackground(new Color(241, 230, 208));
		table.getColumnModel().getColumn(0).setPreferredWidth(63);
		table.getColumnModel().getColumn(1).setPreferredWidth(63);
		table.getColumnModel().getColumn(2).setPreferredWidth(63);
		table.getColumnModel().getColumn(3).setPreferredWidth(59);
		table.getColumnModel().getColumn(4).setPreferredWidth(156);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(30);
		scrollPane_1.setViewportView(table);
		select();
		contentPane.add(scrollPane_1);
	}

	public static void select() { // 테이블에 보이기 위해 검색
		model.setRowCount(0);
		sql = "select M_ID, M_PW, M_NAME, M_GENDER, M_EMAIL, M_TEL, M_DATE, M_STATE "
				+ "from management where M_STATE = 1 OR M_STATE = 2 OR M_STATE = 3 " + "order by m_date desc";
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성

			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				if (rs.getInt("M_STATE") == 1) {
					state = "일반";
				} else if (rs.getInt("M_STATE") == 2) {
					state = "탈퇴";
				} else if (rs.getInt("M_STATE") == 3) {
					state = "정지";
				}
				model.addRow(new Object[] { rs.getString("M_ID"), rs.getString("M_PW"), rs.getString("M_NAME"),
						rs.getString("M_GENDER"), rs.getString("M_EMAIL"), rs.getString("M_TEL"),
						sdf.format(rs.getTimestamp("M_DATE")), state });
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

	private void select(String text) { // 테이블에 보이기 위해 검색
		if (comboBox.getSelectedItem() == "이름") {
			sql = "select M_ID, M_PW, M_NAME, M_GENDER, M_EMAIL, M_TEL, M_DATE, M_STATE "
					+ "from management where (M_STATE = 1 OR M_STATE = 2 OR M_STATE = 3) and M_NAME like ? "
					+ "order by m_date desc";
		} else { // 전화번호
			sql = "select M_ID, M_PW, M_NAME, M_GENDER, M_EMAIL, M_TEL, M_DATE, M_STATE "
					+ "from management where (M_STATE = 1 OR M_STATE = 2 OR M_STATE = 3) and M_TEL like ? "
					+ "order by m_date desc";
		}
		try {
			conn = DataBase.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + text + "%");
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				if (rs.getInt("M_STATE") == 1) {
					state = "일반";
				} else if (rs.getInt("M_STATE") == 2) {
					state = "탈퇴";
				} else if (rs.getInt("M_STATE") == 3) {
					state = "정지";
				}
				model.addRow(new Object[] { rs.getString("M_ID"), rs.getString("M_PW"), rs.getString("M_NAME"),
						rs.getString("M_GENDER"), rs.getString("M_EMAIL"), rs.getString("M_TEL"),
						sdf.format(rs.getTimestamp("M_DATE")), state });
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

	class EventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("search")) {
				model.setRowCount(0);
				select(textField.getText());
			} else if (e.getActionCommand().equals("re")) {
				MemberModify obj = new MemberModify();
				obj.setDate(modifyresult);
			} else {
				MemberJoin obj = new MemberJoin();
				obj.setVisible(true);
			}
		}
	}

	private class JTableMouseListener extends MouseAdapter { // 마우스로 눌려진값확인하기
		public void mouseClicked(MouseEvent e) { // 선택된 위치의 값을 출력
			JTable jtable = (JTable) e.getSource();
			int row = jtable.getSelectedRow(); // 선택된 테이블의 행값

			int c1 = jtable.getColumnCount();

			for (int i = 0; i < c1; i++) {
				modifyresult[i] = model.getValueAt(row, i).toString();
				System.out.println(modifyresult[i]);
			}
		}

		public void mousePressed(MouseEvent e) { // 선택된 위치의 값을 출력
			JTable jtable = (JTable) e.getSource();
			int row = jtable.getSelectedRow(); // 선택된 테이블의 행값

			int c1 = jtable.getColumnCount();

			for (int i = 0; i < c1; i++) {
				modifyresult[i] = model.getValueAt(row, i).toString();
				System.out.println(modifyresult[i]);
			}
		}
	}
}