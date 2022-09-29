package gui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import dao.ManagementDAO;
import dto.ManagementDTO;

public class MemberModify extends JFrame {

	private JPanel contentPane;
	private JTextField text_id, text_name;
	private JTextField text_pw;
	private JTextField text_email;
	private JTextField text_phone_2;
	private JTextField text_phone_3;
	private static MemberModify frame;
	private JComboBox comboBox_1;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;
	// 버튼 선언
	private JButton out;
	private JButton update;
	private JButton cancel;
	private ButtonGroup bg;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;

	// 라벨 선언
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel label_2;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private JLabel label_9;
	int state;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public MemberModify() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(700, 300, 580, 537);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(241,230,208));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		ImageIcon im = new ImageIcon("ff/img/logo.png");
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(new Color(120,81,48));
		panel.setBounds(0, 0, 638, 83);
		contentPane.add(panel);
		panel.setLayout(null);

		lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon(MemberModify.class.getResource("/img/KakaoTalk_20220415_153732288.png")));
		
		lblNewLabel.setBounds(150, 23, 283, 44);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		JLabel logo = new JLabel();
		logo.setIcon(new ImageIcon(MemberModify.class.getResource("/img/KakaoTalk_20220415_124034637.png")));
		logo.setBounds(0, -650, 570, 950);
		panel.add(logo);
		
		lblNewLabel_1 = new JLabel("아이디");
		lblNewLabel_1.setIcon(new ImageIcon(MemberModify.class.getResource("/img/id.png")));
		lblNewLabel_1.setBounds(70, 115, 89, 45);
		contentPane.add(lblNewLabel_1);

		label_2 = new JLabel("이름");
		label_2.setIcon(new ImageIcon(MemberModify.class.getResource("/img/name.png")));
		label_2.setBounds(70, 223, 65, 42);
		contentPane.add(label_2);

		label_4 = new JLabel("비밀번호");
		label_4.setIcon(new ImageIcon(MemberModify.class.getResource("/img/pw.png")));
		label_4.setBounds(70, 171, 105, 43);
		contentPane.add(label_4);

		label_5 = new JLabel("번호");
		label_5.setIcon(new ImageIcon(MemberModify.class.getResource("/img/num.png")));
		label_5.setBounds(70, 279, 65, 42);
		contentPane.add(label_5);

		label_6 = new JLabel("이메일");
		label_6.setIcon(new ImageIcon(MemberModify.class.getResource("/img/em.png")));
		label_6.setBounds(70, 336, 84, 42);
		contentPane.add(label_6);
		
		/*
		JLabel logo2 = new JLabel();
		logo2.setIcon(new ImageIcon(MemberModify.class.getResource("/img/member_img.png")));
		logo2.setBounds(400, 120, 70, 70);
		logo2.setBackground(new Color(241,230,208));
		contentPane.add(logo2);
		*/
		
		comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"일반", "정지", "탈퇴"}));
		comboBox_3.setBounds(400, 120, 70, 30);
		contentPane.add(comboBox_3);
		
		bg = new ButtonGroup();

		JLabel lblNewLabel = new JLabel("성별");
		lblNewLabel.setIcon(new ImageIcon(MemberModify.class.getResource("/img/se.png")));
		lblNewLabel.setBounds(70, 390, 65, 43);
		contentPane.add(lblNewLabel);

		rdbtnNewRadioButton = new JRadioButton("남성");
		rdbtnNewRadioButton.setBounds(200, 400, 113, 23);
		rdbtnNewRadioButton.setBackground(new Color(241,230,208));
		bg.add(rdbtnNewRadioButton);
		contentPane.add(rdbtnNewRadioButton);

		rdbtnNewRadioButton_1 = new JRadioButton("여성");
		rdbtnNewRadioButton_1.setBounds(325, 400, 113, 23);
		rdbtnNewRadioButton_1.setBackground(new Color(241,230,208));
		bg.add(rdbtnNewRadioButton_1);
		contentPane.add(rdbtnNewRadioButton_1);
		/*
		out = new JButton("탈퇴하기");
		out.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		out.setBackground(new Color(184, 134, 11));
		out.addActionListener(new EventHandler());
		out.setForeground(Color.white);
		out.setActionCommand("out");
		out.setBounds(94, 445, 115, 45);
		contentPane.add(out);
		*/
		update = new JButton("수정");
		update.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		update.setBackground(new Color(172, 136, 102));
		update.setForeground(Color.white);
		//0419
		update.setBounds(94, 445, 115, 45);
		
		
		update.addActionListener(new EventHandler());
		update.setActionCommand("update");
		
		contentPane.add(update);

		cancel = new JButton("취소");
		cancel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		cancel.setBackground(new Color(172, 136, 102));
		cancel.setForeground(Color.white);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancel.setBounds(353, 445, 115, 45);
		contentPane.add(cancel);

		text_id = new JTextField();
		text_id.setEditable(false);
		text_id.setBounds(190, 120, 183, 31);
		contentPane.add(text_id);
		text_id.setColumns(10);

		text_name = new JTextField();
		text_name.setEditable(false);
		text_name.setColumns(10);
		text_name.setBounds(190, 228, 183, 30);
		contentPane.add(text_name);

		text_pw = new JTextField();
		text_pw.setColumns(10);
		text_pw.setBounds(190, 176, 183, 30);
		text_pw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(((JTextField)e.getSource()).getText().length() >= 15) {
					e.consume();
				}
			}
		});
		contentPane.add(text_pw);

		text_email = new JTextField();
		text_email.setColumns(10);
		text_email.setBounds(188, 340, 121, 34);
		contentPane.add(text_email);

		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { " 선택", "010", "011", "016", "017", "018", "019" }));
		comboBox_1.setBounds(189, 283, 57, 30);
		contentPane.add(comboBox_1);

		label_7 = new JLabel("@");
		label_7.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_7.setBounds(324, 347, 27, 15);
		contentPane.add(label_7);

		comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] { "-이메일 선택-", "naver.com", "daum.net", "nate.com",
				"hotmail.com", "yahoo.com", "empas.com", "korea.com", "dreamwiz.com", "gmail.com" }));
		comboBox_2.setBounds(355, 340, 121, 34);
		contentPane.add(comboBox_2);

		label_8 = new JLabel("-");
		label_8.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label_8.setBounds(258, 291, 25, 15);
		contentPane.add(label_8);

		text_phone_2 = new JTextField();
		text_phone_2.setColumns(10);
		text_phone_2.setBounds(280, 283, 62, 30);
		text_phone_2.setHorizontalAlignment(JTextField.CENTER);
		text_phone_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(((JTextField)e.getSource()).getText().length() >= 4) {
					e.consume();
				}
			}
		});
		contentPane.add(text_phone_2);

		text_phone_3 = new JTextField();
		text_phone_3.setColumns(10);
		text_phone_3.setBounds(370, 283, 62, 30);
		text_phone_3.setHorizontalAlignment(JTextField.CENTER);
		text_phone_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(((JTextField)e.getSource()).getText().length() >= 4) {
					e.consume();
				}
			}
		});
		contentPane.add(text_phone_3);

		label_9 = new JLabel("-");
		label_9.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label_9.setBounds(354, 283, 13, 31);
		contentPane.add(label_9);
		setVisible(true);
	}

	public void setDate(String[] modifyresult) {

		text_id.setText(modifyresult[0]);
		text_pw.setText(modifyresult[1]);
		text_name.setText(modifyresult[2]);
		comboBox_1.setSelectedItem(modifyresult[5].split("-")[0]);
		text_phone_2.setText(modifyresult[5].split("-")[1]);
		text_phone_3.setText(modifyresult[5].split("-")[2]);
		text_email.setText(modifyresult[4].split("@")[0]);
		comboBox_2.setSelectedItem(modifyresult[4].split("@")[1]);
		if (rdbtnNewRadioButton.getText().equals(modifyresult[3])) {
			rdbtnNewRadioButton.setSelected(true);
		} else {
			rdbtnNewRadioButton_1.setSelected(true);
		}
		comboBox_3.setSelectedItem(modifyresult[7]);
	}

	class EventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("out")) {
				ManagementDTO dto = new ManagementDTO();
				String mID = text_id.getText();
				dto.setM_state(3);
				dto.setM_id(mID);
				ManagementDAO dao = new ManagementDAO();
				int data = dao.deleteMember(dto);
				System.out.println("삭제자료:" + data);
				dispose();
			} else if (e.getActionCommand().equals("update")) {
				StringBuffer sb1 = new StringBuffer();
				StringBuffer sb2 = new StringBuffer();
				// 아이디
				String mID = text_id.getText();

				String mPw = text_pw.getText();

				// 성명
				String mName = text_name.getText();

				// 성별
				String mGender = "";
				if (rdbtnNewRadioButton.isSelected()) {
					mGender = rdbtnNewRadioButton.getText();
				}
				if (rdbtnNewRadioButton_1.isSelected()) {
					mGender = rdbtnNewRadioButton_1.getText();
				}
				System.out.println(mGender);

				// 전화번호
				sb1.append(comboBox_1.getSelectedItem().toString());
				sb1.append("-");
				sb1.append(text_phone_2.getText());
				sb1.append("-");
				sb1.append(text_phone_3.getText());
				
				// 이메일
				sb2.append(text_email.getText() + "@");
				sb2.append(comboBox_2.getSelectedItem().toString());
				if(comboBox_3.getSelectedItem().equals("일반")) {
					state = 1;
				}else if(comboBox_3.getSelectedItem().equals("탈퇴")) {
					state = 2;
				}else if(comboBox_3.getSelectedItem().equals("정지")) {
					state = 3;
				}
				if(text_pw.getText().equals("")) {
					final Dialog mPwInfo = new Dialog(frame, "Information", true);
					mPwInfo.setSize(200, 100);
					mPwInfo.setLocationRelativeTo(null);
					mPwInfo.setLayout(new FlowLayout());
					JLabel msg = new JLabel("비밀번호를 입력해주세요.");
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
					text_pw.requestFocus();
				}
				else if (text_phone_2.getText().equals("") || text_phone_3.getText().equals("")) {
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
					text_phone_2.requestFocus();
				} else if (!integerOrNot(sb1.toString())) {
					final Dialog mPhoneInfo1 = new Dialog(frame, "Information", true);
					mPhoneInfo1.setSize(200, 100);
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
					text_phone_2.requestFocus();
					text_phone_2.selectAll();
				} else if (text_email.getText().equals("") || comboBox_1.getSelectedIndex() == 0) {
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
					text_email.requestFocus();
					text_email.selectAll();
				} else if (!Pattern.matches("^[0-9a-zA-Z]*$", text_email.getText()) || Pattern.matches("^[0-9]*$", text_email.getText())) {
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
					text_email.requestFocus();
					text_email.selectAll();
				} else {
					ManagementDTO dto = new ManagementDTO();
					dto.setM_id(mID);
					dto.setM_name(mName);
					dto.setM_pw(mPw);
					dto.setM_gender(mGender);
					dto.setM_tel(sb1.toString());
					dto.setM_email(sb2.toString());
					dto.setM_state(state);
					ManagementDAO dao = new ManagementDAO();
					int data = dao.updateMember(dto);
					System.out.println("수정자료:" + data);
					JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.", "관리자 회원수정", JOptionPane.INFORMATION_MESSAGE);
					MemberManager.select();
					dispose();
				}
			}
		}
	}

	public boolean integerOrNot(String memData) { // 입력값이 숫자인지 문자인지 판별 :
		char[] charData = memData.replace("-", "").toCharArray();
		boolean check = true;
		while (check) {
			for (int i = 0; i < charData.length; i++) {
				if (!Character.isDigit(charData[i])) {
					check = !check;
					break;
				}
			}
			break;
		}
		return check;
	}
}