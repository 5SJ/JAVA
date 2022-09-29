package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import dao.ManagementDAO;
import dao.NoteDAO;
import dao.SeatDAO;
import dao.borrowDAO;
import dto.ManagementDTO;
import dto.SeatDTO;
import dto.borrowDTO;
import net.UDPSocketNet;

public class MyPagePanel extends JPanel {
	private CardLayout card = new CardLayout();
	private JPanel subPan, subPanTitle, subPan_1, pan2, pan3;
	private DefaultTableModel model;
	private JTable jt;
	private JScrollPane js;
	private JTextField textField_id, textField_name, textField_pw, textField_pw_1, textField_email, textField_phone_2,
			textField_phone_3, tId;
	private JPasswordField tPassword;
	private JComboBox<String> comboBox_1, comboBox_2;
	private JLabel lblNewLabel_1, label_2, label_4, label_5, label_6, label_7, label_8, label_9, subLabel, lId,
			lPassword;
	private static JButton out, update, login, note;
	private ButtonGroup bg;
	private JRadioButton rdbtnNewRadioButton, rdbtnNewRadioButton_1;
	String id;

	public MyPagePanel(String id, UDPSocketNet usk) {
		this.id = id;
		setLayout(null);

		JPanel pan1 = new JPanel();
		pan1.setBackground(new Color(241, 230, 208));
		pan1.setBounds(0, 0, 1000, 107); // 0414
		pan1.setBorder(new LineBorder(new Color(0, 0, 0))); // 0418
		pan1.setName("마이페이지");
		pan1.setLayout(null);
		add(pan1);

		JLabel lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/text12.png")));
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblNewLabel_2.setBounds(380, 28, 236, 55);
		pan1.add(lblNewLabel_2);

		
		JLabel labelg = new JLabel();
		labelg.setIcon(new ImageIcon(BookSearch.class.getResource("/img/1005.png")));
		labelg.setBounds(0, -10, 150, 143); //0414
		pan1.add(labelg);
		
		JLabel labelgg = new JLabel();
		labelgg.setIcon(new ImageIcon(BookSearch.class.getResource("/img/1123.png")));
		labelgg.setBounds(755, -52, 230, 230); //0414
		pan1.add(labelgg);
		
		JLabel labelgg2= new JLabel();
		labelgg2.setIcon(new ImageIcon(BookSearch.class.getResource("/img/today.png")));
		labelgg2.setBounds(120, 15, 200, 60); //0414
		pan1.add(labelgg2);
		
		
		
		pan2 = new JPanel();
		pan2.setBounds(0, 107, 990, 45); // 0414
		pan2.setBackground(new Color(241, 230, 208));
		pan2.setLayout(null);
		add(pan2);

		JButton myInfo = new JButton("내 정보");
		myInfo.setBackground(new Color(172, 136, 102));
		myInfo.setForeground(new Color(255, 255, 255));
		myInfo.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		myInfo.setBounds(0, 0, pan2.getWidth() / 5, pan2.getHeight());
		myInfo.setVisible(false);
		pan2.add(myInfo);

		JButton seatInfo = new JButton("자리기록");
		seatInfo.setBackground(new Color(172, 136, 102));
		seatInfo.setForeground(new Color(255, 255, 255));
		seatInfo.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		seatInfo.setBounds(pan2.getWidth() / 5 * 1, 0, pan2.getWidth() / 5, pan2.getHeight());
		seatInfo.setVisible(false);
		pan2.add(seatInfo);

		JButton rentalInfo = new JButton("대출기록");
		rentalInfo.setBackground(new Color(172, 136, 102));
		rentalInfo.setForeground(new Color(255, 255, 255));
		rentalInfo.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		rentalInfo.setBounds(pan2.getWidth() / 5 * 2, 0, pan2.getWidth() / 5, pan2.getHeight());
		rentalInfo.setVisible(false);
		pan2.add(rentalInfo);

		note = new JButton("쪽지함");
		note.setBackground(new Color(172, 136, 102));
		note.setForeground(new Color(255, 255, 255));
		note.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		note.setBounds(pan2.getWidth() / 5 * 3, 0, pan2.getWidth() / 5, pan2.getHeight());
		note.setVisible(false);
		pan2.add(note);
		getNoteCount(id);

		JButton logout = new JButton("로그아웃");
		logout.setBackground(new Color(172, 136, 102));
		logout.setForeground(new Color(255, 255, 255));
		logout.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		logout.setBounds(pan2.getWidth() / 5 * 4, 0, pan2.getWidth() / 5, pan2.getHeight());
		logout.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
				new LoginFrame();
				UDPSocketNet.socket_close();
				((JFrame) ((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent()
						.getParent().getParent()).dispose();
			}
		});
		logout.setVisible(false);
		pan2.add(logout);
		pan3 = new JPanel();
		pan3.setBackground(new Color(241, 230, 208));
		pan3.setBounds(0, 152, 1000, 700); // 0414
		pan3.setLayout(card);
		add(pan3);

		myInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pan3.setVisible(true);
				MyInfo(id);
				card.show(pan3, "1");
			}
		});

		seatInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pan3.setVisible(true);
//				pan3.remove(SeatInfo(id));
//				pan3.add(SeatInfo(id), "2");
				card.show(pan3, "2");
			}
		});

		rentalInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pan3.setVisible(true);
//				RentalInfo(id);
				card.show(pan3, "3");
			}
		});

		note.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NoteFrame(id, usk);
			}
		});

		pan3.add(MyInfo(id), "1");
		pan3.add(SeatInfo(id), "2");
		pan3.add(RentalInfo(id), "3");
		pan3.add(myChk(id), "4");
		card.show(pan3, "4");
	}

	public JPanel MyInfo(String id) {
		ManagementDAO mdao = new ManagementDAO();
		ArrayList<ManagementDTO> list = (ArrayList<ManagementDTO>) mdao.managementList(id);

		subPanTitle = new JPanel();
		subPanTitle.setBounds(0, 0, 1000, 70); // 0414
		subPanTitle.setBackground(new Color(120, 81, 48)); // 0414
		subPanTitle.setLayout(null);

		subLabel = new JLabel();
		subLabel.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/text38.png"))); // 0414
		subLabel.setBounds(400, 0, 271, 73); // 0414
		subPanTitle.add(subLabel); // 0414

		subPan_1 = new JPanel();
		subPan_1.setLayout(null);
		subPan_1.setBackground(new Color(241, 230, 208));// 0414
		subPan_1.setBounds(0, 0, 1000, 420); // 0414

		JLabel book_img = new JLabel();
		book_img.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/bookimg_3.png")));
		book_img.setBounds(12, 224, 228, 257);
		subPan_1.add(book_img);

		JLabel img_ic = new JLabel();
		img_ic.setBounds(692, 310, 286, 183);
		img_ic.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/img_ic4.png")));
		subPan_1.add(img_ic);

		JLabel img_ic2 = new JLabel();
		img_ic2.setBounds(690, 200, 228, 183);
		img_ic2.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/img_ic5.png")));
		subPan_1.add(img_ic2);

		lblNewLabel_1 = new JLabel(); // 아이디
		lblNewLabel_1.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/id.png"))); 
		lblNewLabel_1.setBounds(240, 80, 89, 45);
		subPan_1.add(lblNewLabel_1);

		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setBounds(365, 85, 183, 31);
		textField_id.setText(list.get(0).getM_id());
		subPan_1.add(textField_id);
		textField_id.setColumns(10);

		label_5 = new JLabel(); // 비밀번호
		label_5.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/pw.png"))); 
		label_5.setBounds(240, 130, 105, 44);
		subPan_1.add(label_5);
		textField_pw_1 = new JTextField();
		textField_pw_1.setColumns(10);
		textField_pw_1.setBounds(365, 133, 183, 31);
		textField_pw_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (((JTextField) e.getSource()).getText().length() >= 15) {
					e.consume();
				}
			}
		});
		subPan_1.add(textField_pw_1);

		label_4 = new JLabel("새 비밀번호"); // 새 비밀번호
		label_4.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/img1111.png")));
		label_4.setBounds(240, 179, 130, 42); 
		subPan_1.add(label_4);

		textField_pw = new JTextField();
		textField_pw.setColumns(10);
		textField_pw.setBounds(365, 182, 183, 31); // 0420
		textField_pw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (((JTextField) e.getSource()).getText().length() >= 15) {
					e.consume();
				}
			}
		});
		subPan_1.add(textField_pw);

		label_2 = new JLabel(); // 이름
		label_2.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/name.png"))); // 0414
		label_2.setBounds(240, 228, 65, 42); // 0420
		subPan_1.add(label_2);
		textField_name = new JTextField();
		textField_name.setEditable(false);
		textField_name.setColumns(10);
		textField_name.setBounds(365, 230, 183, 31); // 0420
		textField_name.setText(list.get(0).getM_name());
		subPan_1.add(textField_name);

		label_5 = new JLabel(); // 번호
		label_5.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/num.png"))); // 0420
		label_5.setBounds(240, 275, 65, 42); // 0420
		subPan_1.add(label_5);
		comboBox_1 = new JComboBox<String>();
		comboBox_1.setModel(
				new DefaultComboBoxModel<String>(new String[] { " 선택", "010", "011", "016", "017", "018", "019" }));
		comboBox_1.setBounds(365, 280, 57, 30); // 0420
		comboBox_1.setSelectedItem(list.get(0).getM_tel().split("-")[0]);

		label_8 = new JLabel("-");
		label_8.setBounds(435, 283, 25, 15); // 0420
		label_8.setFont(new Font("맑은 고딕", Font.BOLD, 18));// 0414
		subPan_1.add(label_8);
		textField_phone_2 = new JTextField();
		textField_phone_2.setColumns(10);
		textField_phone_2.setBounds(455, 280, 62, 30); // 0420
		textField_phone_2.setText(list.get(0).getM_tel().split("-")[1]);
		textField_phone_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (((JTextField) e.getSource()).getText().length() >= 4) {
					e.consume();
				}
			}
		});
		subPan_1.add(textField_phone_2);
		
		label_9 = new JLabel("-");
		label_9.setBounds(535, 283, 25, 15); // 0420
		label_9.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		subPan_1.add(label_9);
		subPan_1.add(comboBox_1);
		textField_phone_3 = new JTextField();
		textField_phone_3.setColumns(10);
		textField_phone_3.setBounds(555, 280, 62, 30); // 0420
		textField_phone_3.setText(list.get(0).getM_tel().split("-")[2]);
		textField_phone_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (((JTextField) e.getSource()).getText().length() >= 4) {
					e.consume();
				}
			}
		});
		subPan_1.add(textField_phone_3);

		label_6 = new JLabel(); // 이메일
		label_6.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/em.png"))); // 0414
		label_6.setBounds(240, 325, 84, 42); // 0420
		subPan_1.add(label_6);

		textField_email = new JTextField();
		textField_email.setColumns(10);
		textField_email.setBounds(365, 327, 121, 34); // 0420
		textField_email.setText(list.get(0).getM_email().split("@")[0]);
		subPan_1.add(textField_email);
		label_7 = new JLabel("@");
		label_7.setFont(new Font("맑은 고딕", Font.BOLD, 15));// 0414
		label_7.setBounds(505, 335, 35, 15); // 0420
		subPan_1.add(label_7);

		comboBox_2 = new JComboBox<String>();
		comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] { "-이메일 선택-", "naver.com", "daum.net",
				"nate.com", "hotmail.com", "yahoo.com", "empas.com", "korea.com", "dreamwiz.com", "gmail.com" }));
		comboBox_2.setBounds(545, 327, 121, 34); // 0420
		comboBox_2.setSelectedItem(list.get(0).getM_email().split("@")[1]);
		subPan_1.add(comboBox_2);

		bg = new ButtonGroup();
		JLabel lblNewLabel = new JLabel(); // 성별
		lblNewLabel.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/se.png"))); 
		lblNewLabel.setBounds(240, 370, 65, 42); // 0420
		subPan_1.add(lblNewLabel);

		rdbtnNewRadioButton = new JRadioButton("남성");
		rdbtnNewRadioButton.setBounds(385, 381, 100, 20); // 0420
		rdbtnNewRadioButton.setBackground(new Color(241, 230, 208));
		bg.add(rdbtnNewRadioButton);
		subPan_1.add(rdbtnNewRadioButton);

		rdbtnNewRadioButton_1 = new JRadioButton("여성");
		rdbtnNewRadioButton_1.setBounds(525, 381, 113, 20); // 0420
		rdbtnNewRadioButton_1.setBackground(new Color(241, 230, 208));
		bg.add(rdbtnNewRadioButton_1);
		subPan_1.add(rdbtnNewRadioButton_1);

		if (rdbtnNewRadioButton.getText().equals(list.get(0).getM_gender())) {
			rdbtnNewRadioButton.setSelected(true);
		} else {
			rdbtnNewRadioButton_1.setSelected(true);
		}

		out = new JButton("탈퇴하기");
		out.setForeground(new Color(255, 255, 255));
		out.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		out.addActionListener(new EventHandler());
		out.setBackground(new Color(172, 136, 102));
		out.setActionCommand("out");
		out.setBounds(800, 150, 150, 45); 
		subPan_1.add(out);

		update = new JButton("수정하기");
		update.setForeground(new Color(255, 255, 255));
		update.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		update.addActionListener(new EventHandler());
		update.setBackground(new Color(172, 136, 102));
		update.setActionCommand("update");
		update.setBounds(800, 90, 150, 45); 
		subPan_1.add(update);
		subPan_1.add(subPanTitle);
		subPanTitle.add(subLabel);

		return subPan_1;
	}

	public JPanel SeatInfo(String id) {
		SeatDAO sdao = new SeatDAO();
		ArrayList<SeatDTO> list = (ArrayList<SeatDTO>) sdao.UsedHistory(id);
		subPan = new JPanel();
		subPan.setBackground(new Color(241, 230, 208));
		subPan.setLayout(null);
		subPan.setBounds(0, 0, 1000, 420); 
		subPanTitle = new JPanel();
		subPanTitle.setBackground(new Color(120, 81, 48));
		subPanTitle.setBounds(0, 0, 1000, 70); 
		subPanTitle.setLayout(null);

		subLabel = new JLabel();
		subLabel.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/textimg48.png")));
		subLabel.setBackground(new Color(241, 230, 208));
		subLabel.setBounds(0, 0, 1000, 73); 
		subLabel.setHorizontalAlignment(JLabel.CENTER);
		subPanTitle.add(subLabel);
		String[] c = { "NO", "좌석번호", "시작시간", "종료시간" };
		model = new DefaultTableModel(c, 0);
		model.setRowCount(0);
		for (SeatDTO s : list) {
			model.addRow(new Object[] { s.getS_index(), s.getS_num(), s.getS_sdate(), s.getS_edate() });
		}

		jt = new JTable(model);
		jt.setRowHeight(23);
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = jt.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {

			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);

			}
		jt.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		jt.setSelectionBackground(new Color(120, 81, 48));
		jt.setSelectionForeground(new Color(255, 255, 255));
		jt.setBackground(new Color(241, 230, 208));
		jt.getTableHeader().setBackground(new Color(172, 136, 102));
		jt.getTableHeader().setForeground(new Color(255, 255, 255));
		jt.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 15));

		js = new JScrollPane(jt);
		js.getViewport().setBackground(new Color(241, 230, 208));
		js.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		js.setBounds(0, 68, 989, 500); // 0414
		js.setViewportView(jt);
		js.setBackground(new Color(241, 230, 208));
		subPan.add(js);
		subPan.add(subPanTitle);
		jt.getTableHeader().setOpaque(false);
		return subPan;
	}

	public JPanel RentalInfo(String id) {
		borrowDAO bdao = new borrowDAO();
		ArrayList<borrowDTO> list = (ArrayList<borrowDTO>) bdao.RentalHistory(id);
		subPanTitle = new JPanel();
		subPanTitle.setBackground(new Color(120, 81, 48));
		subPanTitle.setBounds(0, 0, 1000, 70); // 0414
		subPanTitle.setLayout(null);
		subLabel = new JLabel("");
		subLabel.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/textimg49.png")));
		subLabel.setBackground(new Color(241, 230, 208));
		subLabel.setBounds(0, 0, 1000, 70); // 0414
		subLabel.setHorizontalAlignment(JLabel.CENTER);
		subPan = new JPanel();
		subPan.setBackground(new Color(241, 230, 208));
		subPan.setLayout(null);
		subPan.setBounds(0, 0, 989, 420); // 0414
		subPan.add(subPanTitle);
		subPanTitle.add(subLabel);
		String state;
		String[] c = { "NO", "도서번호", "도서명", "대출일", "반납일", "정보" };
		model = new DefaultTableModel(c, 0);
		for (borrowDTO b : list) {
			if (b.getState().equals("N")) {
				state = "대여중";
			} else {
				state = "반납완료";
			}
			model.addRow(new Object[] { b.getIndex(), b.getBOOK_CODE(), b.getBookname(), b.getBorrow_DATE(),
					b.getReturn_DATE(), state });
		}
		jt = new JTable(model);
		jt.setRowHeight(23);
		jt.getColumnModel().getColumn(0).setPreferredWidth(10);
		jt.getColumnModel().getColumn(1).setPreferredWidth(30);
		jt.getColumnModel().getColumn(2).setPreferredWidth(200);
		jt.getColumnModel().getColumn(3).setPreferredWidth(120);
		jt.getColumnModel().getColumn(4).setPreferredWidth(120);
		jt.getColumnModel().getColumn(5).setPreferredWidth(30);
		jt.setSelectionBackground(new Color(120, 81, 48));
		jt.setSelectionForeground(new Color(255, 255, 255));
		jt.setBackground(new Color(241, 230, 208));
		jt.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		jt.getTableHeader().setBackground(new Color(172, 136, 102));
		jt.getTableHeader().setForeground(new Color(255, 255, 255));
		jt.getTableHeader().setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		//테이블 가운데 정렬
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = jt.getColumnModel();
		tcmSchedule.getColumn(0).setCellRenderer(tScheduleCellRenderer);
		tcmSchedule.getColumn(3).setCellRenderer(tScheduleCellRenderer);
		tcmSchedule.getColumn(4).setCellRenderer(tScheduleCellRenderer);
		tcmSchedule.getColumn(5).setCellRenderer(tScheduleCellRenderer);

		js = new JScrollPane(jt);
		js.getViewport().setBackground(new Color(241, 230, 208));
		js.setBounds(0, 70, 989, 420); // 0414
		js.setViewportView(jt);
		js.setBackground(new Color(241, 230, 208));
		subPan.add(js);
		return subPan;
	}

	public JPanel myChk(String id) {
		ManagementDAO mdao = new ManagementDAO();
		subPan = new JPanel();
		subPan.setBackground(new Color(241, 230, 208));
		subPan.setLayout(null);
		subPan.setBounds(0, 0, 1000, 420); // 0414

		lPassword = new JLabel("비밀번호"); // 0420
		lPassword.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/pw.png"))); // 0420
		lPassword.setBounds(285, 162, 105, 44); // 0420
		lPassword.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		subPan.add(lPassword);

		tPassword = new JPasswordField();
		tPassword.setBounds(395, 160, 200, 44); // 0420
		tPassword.setColumns(10);
		subPan.add(tPassword);

		login = new JButton("확인");
		login.setBackground(new Color(120, 81, 48));
		login.setBounds(615, 150, 104, 60); // 0420
		login.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		login.setForeground(Color.white);
		subPan.add(login);

		JLabel imgg = new JLabel(); // 0420
		imgg.setBounds(50, 90, 228, 53); // 0420
		imgg.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/img1112.png"))); // 0420
		subPan.add(imgg); // 0420

		JLabel imggg = new JLabel(); // 0420
		imggg.setBounds(740, 140, 210, 210); // 0420
		imggg.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/out.png"))); // 0420
		subPan.add(imggg); // 0420

		JLabel imgggg = new JLabel(); // 0420
		imgggg.setBounds(240, -20, 550, 130); // 0420
		imgggg.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/img1113.png"))); // 0420
		subPan.add(imgggg); // 0420

		JLabel imggggg = new JLabel(); // 0420
		imggggg.setBounds(-70, 100, 400, 400); // 0420
		imggggg.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/img1114.png"))); // 0420
		subPan.add(imggggg); // 0420
		
		JLabel imgggggg = new JLabel(); // 0420
		imgggggg.setBounds(450, 240, 270, 280); // 0420
		imgggggg.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/img_gum.png"))); // 0420
		subPan.add(imgggggg); // 0420
		
		JLabel imgggggg2 = new JLabel(); // 0420
		imgggggg2.setBounds(340, 240, 200, 60); // 0420
		imgggggg2.setIcon(new ImageIcon(MyPagePanel.class.getResource("/img/noway.png"))); // 0420
		subPan.add(imgggggg2); // 0420

		

		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mdao.idPassword(id, tPassword.getText()) == 1) {
					for (int i = 0; i < pan2.getComponents().length; i++) {
						pan2.getComponents()[i].setVisible(true);
					}
					subPan.setVisible(true);
					card.show(pan3, "1");
				} else {
					JOptionPane.showMessageDialog(null, "비밀번호를 확인해주세요.");
					tPassword.setText("");
				}
			}
		});
		return subPan;
	}

	public static void getNoteCount(String loginID) {
		NoteDAO ndao = new NoteDAO();
		int cnt = ndao.getNewNote(loginID);
		note.setText("쪽지함[" + cnt + "]");
	}

	class EventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ManagementDAO mdao = new ManagementDAO();
			if (e.getActionCommand().equals("out")) {
				String pw = JOptionPane.showInputDialog("비밀번호를 입력해주세요.");
				System.out.println("입력받은 pw : " + pw);
				if (pw != null) {
					if (pw.equals("")) {
						JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
					} else if (mdao.idPassword(id, pw) == 1) {
						ManagementDTO dto = new ManagementDTO();
						String mID = textField_id.getText();
						dto.setM_state(2);
						dto.setM_id(mID);
						ManagementDAO dao = new ManagementDAO();
						if (dao.deleteMember(dto) == 1) {
							JOptionPane.showMessageDialog(null, "탈퇴가 완료되었습니다.", "그린도서관 탈퇴",
									JOptionPane.INFORMATION_MESSAGE);
							new LoginFrame();
							UDPSocketNet.socket_close();
							((JFrame) ((JButton) e.getSource()).getParent().getParent().getParent().getParent()
									.getParent().getParent().getParent().getParent()).dispose();
						}
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호를 확인해주세요.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "취소하셨습니다.");
				}
			} else if (e.getActionCommand().equals("update")) {
				ManagementDTO dto = new ManagementDTO();
				StringBuffer sb1 = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				// 아이디
				String mID = textField_id.getText();
				String mPw = textField_pw_1.getText();
				String newPw = textField_pw.getText();
				// 성명
				String mName = textField_name.getText();
				// 성별
				String mGender = "";
				if (rdbtnNewRadioButton.isSelected()) {
					mGender = rdbtnNewRadioButton.getText();
				}
				if (rdbtnNewRadioButton_1.isSelected()) {
					mGender = rdbtnNewRadioButton_1.getText();
				}
				// 전화번호
				sb1.append(comboBox_1.getSelectedItem().toString());
				sb1.append("-");
				sb1.append(textField_phone_2.getText());
				sb1.append("-");
				sb1.append(textField_phone_3.getText());
				// 이메일
				sb2.append(textField_email.getText() + "@");
				sb2.append(comboBox_2.getSelectedItem().toString());
				if (mPw.equals("")) {
					final Dialog mPwInfo = new Dialog(MemberMain.getFrames()[0], "Information", true);
					mPwInfo.setSize(200, 100);
					mPwInfo.setLocationRelativeTo(null);
					mPwInfo.setLayout(new FlowLayout());
					JLabel msg = new JLabel("현재 비밀번호를 입력해주세요.");
					JButton ok = new JButton("확인");
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							mPwInfo.setVisible(false);
							mPwInfo.dispose();
						}
					});
					mPwInfo.add(msg);
					mPwInfo.add(ok);
					mPwInfo.setVisible(true);
					textField_pw_1.requestFocus();
					textField_pw_1.selectAll();
				} else {
					if (mdao.idPassword(mID, mPw) == 1) {
						if (textField_phone_2.getText().equals("") || textField_phone_3.getText().equals("")) {
							final Dialog mPhoneInfo = new Dialog(MemberMain.getFrames()[0], "Information", true);
							mPhoneInfo.setSize(200, 100);
							mPhoneInfo.setLocationRelativeTo(null);
							mPhoneInfo.setLayout(new FlowLayout());
							JLabel msg = new JLabel("번호를 입력해주세요");
							JButton ok = new JButton("확인");
							ok.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									mPhoneInfo.setVisible(false);
									mPhoneInfo.dispose();
								}
							});
							mPhoneInfo.add(msg);
							mPhoneInfo.add(ok);
							mPhoneInfo.setVisible(true);
							textField_phone_2.requestFocus();
							textField_phone_2.selectAll();
						} else if (!integerOrNot(sb1.toString())) {
							final Dialog mPhoneInfo1 = new Dialog(MemberMain.getFrames()[0], "Information", true);
							mPhoneInfo1.setSize(200, 100);
							mPhoneInfo1.setLocationRelativeTo(null);
							mPhoneInfo1.setLayout(new FlowLayout());
							JLabel msg = new JLabel("번호는 숫자로 입력해주세요");
							JButton ok = new JButton("확인");
							ok.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									mPhoneInfo1.setVisible(false);
									mPhoneInfo1.dispose();
								}
							});
							mPhoneInfo1.add(msg);
							mPhoneInfo1.add(ok);
							mPhoneInfo1.setVisible(true);
							textField_phone_2.requestFocus();
							textField_phone_2.selectAll();
						} else if (textField_email.getText().equals("") || comboBox_1.getSelectedIndex() == 0) {
							final Dialog mMailInfo = new Dialog(MemberMain.getFrames()[0], "Information", true);
							mMailInfo.setSize(200, 100);
							mMailInfo.setLocationRelativeTo(null);
							mMailInfo.setLayout(new FlowLayout());
							JLabel msg = new JLabel("이메일을 입력해주세요");
							JButton ok = new JButton("확인");
							ok.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									mMailInfo.setVisible(false);
									mMailInfo.dispose();
								}
							});
							mMailInfo.add(msg);
							mMailInfo.add(ok);
							mMailInfo.setVisible(true);
							textField_email.requestFocus();
							textField_email.selectAll();
						} else if (!Pattern.matches("^[0-9a-zA-Z]*$", textField_email.getText())
								|| Pattern.matches("^[0-9]*$", textField_email.getText())) {
							final Dialog mMailInfo = new Dialog(MemberMain.getFrames()[0], "Information", true);
							mMailInfo.setSize(200, 100);
							mMailInfo.setLocationRelativeTo(null);
							mMailInfo.setLayout(new FlowLayout());
							JLabel msg = new JLabel("이메일을 확인해주세요");
							JButton ok = new JButton("확인");
							ok.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									mMailInfo.setVisible(false);
									mMailInfo.dispose();
								}
							});
							mMailInfo.add(msg);
							mMailInfo.add(ok);
							mMailInfo.setVisible(true);
							textField_email.requestFocus();
							textField_email.selectAll();
						} else {
							dto.setM_id(mID);
							dto.setM_name(mName);
							dto.setM_pw(newPw);
							dto.setM_gender(mGender);
							dto.setM_tel(sb1.toString());
							dto.setM_email(sb2.toString());
							System.out.println(mID);
							System.out.println(newPw);
							System.out.println(mName);
							System.out.println(sb1.toString());
							System.out.println(sb2.toString());
							int data = mdao.updateMember(dto);
							System.out.println("수정자료:" + data);
							if (data == 1) {
								JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "회원가입정보 수정",
										JOptionPane.INFORMATION_MESSAGE);
								textField_pw.setText("");
								textField_pw_1.setText("");
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호를 확인해주세요.");
						textField_pw_1.requestFocus();
						textField_pw_1.selectAll();
					}
				}
			}
		}
	}

	public boolean integerOrNot(String memData) { // 입력값이 숫자인지 문자인지 판별 :
		char[] charData = memData.replace("-", "").toCharArray();
		boolean check = true;
		while (check) {
			for (int i = 0; i < charData.length; i++) {
				if (Character.isDigit(charData[i]) == false) {
					check = false;
					break;
				}
			}
			break;
		}
		return check;
	}
}
