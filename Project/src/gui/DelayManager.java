package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
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
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.DataBase;
import dao.NoteDAO;
import dto.NoteDTO;
import dto.borrowDTO;

public class DelayManager extends JPanel {
	private JTextField textField;
	public JTable table;
	
	private static String colNames[] = { "아이디", "이름", "도서명", "도서번호", "전화번호", "대출일", "반납일" }; // 테이블 컬럼 값들
	public static DefaultTableModel model; // 테이블 데이터 모델 객체 생성
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null; // 리턴받아 사용할 객체 생성 ( select에서 보여줄 때 필요 )
	private JScrollPane scrollPane_1;
	private JComboBox<String> comboBox;
	private String[] modifyresult = new String[8];
	
	public DelayManager() {
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBackground(new Color(241,230,208));
		panel.setBounds(0, 0, 987, 107);
	
		panel.setLayout(null);

		JLabel label = new JLabel();
		label.setIcon(new ImageIcon(BookManager.class.getResource("/img/img3.png")));
		label.setBounds(390, 13, 200, 62);
		panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(241,230,208));
		panel_1.setBounds(0, 100, 987, 43);
		add(panel_1);
		add(panel);
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		comboBox.setForeground(new Color(255, 255, 255));
		comboBox.setBackground(new Color(172, 136, 102));
		comboBox.setModel(
				new DefaultComboBoxModel<String>(new String[] { "전체", "아이디", "이름", "도서명", "도서번호", "전화번호" }));
		comboBox.setBounds(170, 7, 115, 30);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(((JComboBox)e.getSource()).getSelectedIndex() == 0) {
					textField.setText("");
					textField.setEditable(false);
				}else {
					textField.setEditable(true);
				}
			}
		});
		panel_1.add(comboBox);

		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		textField.setBounds(300, 7, 300, 30);
		textField.setEditable(false);
		panel_1.add(textField);
		textField.setColumns(10);

		JButton button = new JButton("검색");
		button.setForeground(Color.white);
		button.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		button.setBackground(new Color(172,136,102));
		button.addActionListener(new EventHandler());
		button.setActionCommand("search");
		button.setBounds(610, 7, 90, 30);
		panel_1.add(button);

		JButton btnNewButton_1 = new JButton("전체쪽지");
		btnNewButton_1.setForeground(Color.white);
		btnNewButton_1.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		btnNewButton_1.setBackground(new Color(172,136,102));
		btnNewButton_1.addActionListener(new EventHandler());
		btnNewButton_1.setActionCommand("allWrite");
		btnNewButton_1.setBounds(860, 7, 115, 30);
		panel_1.add(btnNewButton_1);

		JButton button_1 = new JButton("쪽지전송");
		button_1.setForeground(Color.white);
		button_1.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		button_1.setBackground(new Color(172,136,102));
		button_1.addActionListener(new EventHandler());
		button_1.setActionCommand("noteWrite");
		button_1.setBounds(730, 7, 115, 30);
		panel_1.add(button_1);

		model = new DefaultTableModel(colNames, 0);
		table = new JTable(model);
		table.setRowHeight(23);
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = table.getColumnModel();
		tcmSchedule.getColumn(0).setCellRenderer(tScheduleCellRenderer);
		tcmSchedule.getColumn(1).setCellRenderer(tScheduleCellRenderer);
		tcmSchedule.getColumn(3).setCellRenderer(tScheduleCellRenderer);
		tcmSchedule.getColumn(4).setCellRenderer(tScheduleCellRenderer);
		table.setSelectionBackground(new Color(120, 81, 48));
		table.setSelectionForeground(new Color(255, 255, 255));
		table.setGridColor(new Color(0, 0, 0));
		table.setBackground(new Color(241, 230, 208 ));
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		table.getTableHeader().setBackground(new Color(172,136,102));
		table.getTableHeader().setForeground(new Color(241, 230, 208));
		table.getTableHeader().setFont(new Font("맑은 고딕",Font.PLAIN,15));
		
		table.addMouseListener(new JTableMouseListener());
		scrollPane_1 = new JScrollPane(table);
		scrollPane_1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		scrollPane_1.getViewport().setBackground(new Color(241, 230, 208 ));
		scrollPane_1.setBounds(0, 140, 989, 475);
		table.getColumnModel().getColumn(0).setPreferredWidth(63);
		table.getColumnModel().getColumn(1).setPreferredWidth(63);
		table.getColumnModel().getColumn(2).setPreferredWidth(186);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);

		scrollPane_1.setViewportView(table);
		select();
		add(scrollPane_1);
	}

	public ArrayList<borrowDTO> select() { // 테이블에 보이기 위해 검색
		conn = DataBase.getConnection();
		ArrayList<borrowDTO> borrowList = new ArrayList<borrowDTO>();
		borrowDTO bdto;
		String sql = "SELECT * FROM delay_search";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
			while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
				model.addRow(new Object[] { rs.getString("id"), rs.getString("name"), rs.getString("bookname"),
						rs.getInt("booknum"), rs.getString("tel"), rs.getString("bdate"),
						rs.getString("rdate") });
				bdto = new borrowDTO();
				bdto.setId(rs.getString("id"));
				bdto.setName(rs.getString("name"));
				bdto.setBookname(rs.getString("bookname"));
				bdto.setBOOK_CODE(rs.getInt("booknum"));
				bdto.setTel(rs.getString("tel"));
				bdto.setBdate(rs.getTimestamp("bdate"));
				bdto.setRdate(rs.getTimestamp("rdate"));
				borrowList.add(bdto);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			DataBase.close(rs, pstmt, conn);
		}
		return borrowList;
	}

	private void select(String text) { // 테이블에 보이기 위해 검색
		String type = "";
		conn = DataBase.getConnection();
		if (comboBox.getSelectedItem() != "전체") {
			if (comboBox.getSelectedItem() == "이름") {
				type = "name";
			}else if(comboBox.getSelectedItem() == "아이디") { // 전화번호
				type = "id";
			}else if(comboBox.getSelectedItem() == "전화번호") {
				type = "tel";
			}else if(comboBox.getSelectedItem() == "도서명") {
				type = "bookname";
			}else if(comboBox.getSelectedItem() == "도서번호") {
				type = "booknum";
			}
			String sql = "SELECT * FROM delay_search where "+ type +" like ?";
			try {
				pstmt = conn.prepareStatement(sql);
				// pstmt.setString(1, type);
				pstmt.setString(1, "%"+text+"%");
				
				rs = pstmt.executeQuery(); // 리턴받아와서 데이터를 사용할 객체생성
				while (rs.next()) { // 각각 값을 가져와서 테이블값들을 추가
					model.addRow(new Object[] { rs.getString("id"), rs.getString("name"), rs.getString("bookname"),
							rs.getInt("booknum"), rs.getString("tel"), rs.getTimestamp("bdate"),
							rs.getTimestamp("rdate") });
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			} finally {
				DataBase.close(rs, pstmt, conn);
			}
		}else {
			select();
		}
	}

	class EventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("search")) {
				if(comboBox.getSelectedIndex() != 0 && textField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "검색어를 입력해주세요.", "검색실패", JOptionPane.ERROR_MESSAGE);
				}else {
					model.setRowCount(0);
					select(textField.getText());
				}
			} else if (e.getActionCommand().equals("allWrite")) {
				ArrayList<borrowDTO> list = new ArrayList<borrowDTO>();
				NoteDTO ndto = new NoteDTO();
				NoteDAO ndao = new NoteDAO();
				model.setRowCount(0);
				list = select();
				int res = 0;
				for (borrowDTO bb : list) {
					ndto.setN_sid("lib2022");
					ndto.setN_rid(bb.getId());
					ndto.setN_subject(bb.getName()+" 회원님, 대여도서 반납 요청");
					ndto.setN_contents(bb.getName()+"님이 '"+bb.getBorrow_DATE()+"' 에 대여하신 \r\n["+bb.getBookname()+"] 도서의 반납기간이 '"+bb.getReturn_DATE()+"' 까지였는데 \r\n아직 미반납 상태이기 때문에 빠른 시일내에 반납 부탁드립니다.");
					res += ndao.note_write(ndto);
				}
				if(res != list.size()) {
					JOptionPane.showMessageDialog(null, "다시 시도해주세요.", "전송실패", JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "쪽지 "+res+"개 전송 완료", "전송완료", JOptionPane.PLAIN_MESSAGE);
				}
			} else {
				NoteDTO ndto = new NoteDTO();
				NoteDAO ndao = new NoteDAO();
				ndto.setN_sid("lib2022");
				ndto.setN_rid(modifyresult[0]);
				ndto.setN_subject(modifyresult[1]+" 회원님, 대여도서 반납 요청");
				ndto.setN_contents(modifyresult[1]+"님이 '"+modifyresult[5]+"' 에 대여하신 \r\n["+modifyresult[2]+"] 도서의 반납기간이 '"+modifyresult[6]+"' 까지였는데 \r\n아직 미반납 상태이기 때문에 빠른 시일내에 반납 부탁드립니다.");
				int res = ndao.note_write(ndto);
				
				if(res == 0) {
					JOptionPane.showMessageDialog(null, "다시 시도해주세요.", "전송실패", JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					JOptionPane.showMessageDialog(null, "쪽지전송 완료", "전송완료", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}

	private class JTableMouseListener extends MouseAdapter { // 마우스로 눌려진값확인하기
		public void mouseClicked(java.awt.event.MouseEvent e) { // 선택된 위치의 값을 출력
			JTable jtable = (JTable) e.getSource();
			int row = jtable.getSelectedRow(); // 선택된 테이블의 행값
			int c1 = jtable.getColumnCount();
			System.out.println(c1);
			for (int i = 0; i < c1; i++) {
				modifyresult[i] = model.getValueAt(row, i).toString();
				System.out.println(modifyresult[i]);
			}
			// 선택된 위치의 값을 얻어내서 출력
		}
	}
}