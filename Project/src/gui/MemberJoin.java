package gui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import dao.ManagementDAO;
import dto.ManagementDTO;

// 회원가입
@SuppressWarnings("serial")
public class MemberJoin extends JFrame {
	private JPanel rPanel;
	private JLabel rLabel , lblNewLabel_12, lblNewLabel_13;
	private JTextField textField_id, textField_pw, textField_name, textField_phone1, textField_phone2, textField_mail;
	private JComboBox<String> comboBox, comboBox_1;
	private ButtonGroup bg;
	private JRadioButton rdbtnNewRadioButton, rdbtnNewRadioButton_1;
	private static MemberJoin frame;
	private boolean flag; // ID 중복체크 했는지 확인
	public MemberJoin() {
		setBounds(700, 300, 580, 537);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		rPanel = new JPanel();
		rPanel.setBackground(new Color(241,230,208));
		rPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
		rPanel.setLayout(null);
		setContentPane(rPanel);

		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel.setToolTipText("");
		panel.setBounds(0, 0, 1000, 93);
		rPanel.add(panel);
		panel.setLayout(null);
		
		lblNewLabel_12 = new JLabel();  //0414
		lblNewLabel_12.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/img64.png")));
		lblNewLabel_12.setBounds(413, 169,  141, 161);
		rPanel.add(lblNewLabel_12);

		JLabel lblNewLabel_11 = new JLabel("그린도서관 회원가입");
		lblNewLabel_11.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/ghldnjs.png")));
		lblNewLabel_11.setBounds(140, 20, 300, 65);
		panel.add(lblNewLabel_11);

		lblNewLabel_13 = new JLabel();  //0414
		lblNewLabel_13.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/back.png")));
		lblNewLabel_13.setBounds(0, -650, 570, 950);
		panel.add(lblNewLabel_13);
		
		JLabel rId = new JLabel("아이디");
		rId.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		rId.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/id.png")));
		rId.setBounds(70, 116, 89, 45);
		rPanel.add(rId);

		textField_id = new JTextField();
		textField_id.setBounds(190, 120, 183, 31);
		textField_id.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(((JTextField)e.getSource()).getText().length() >= 10) {
					e.consume();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				flag = false;
				if(textField_id.getText().length() == 10) {
					rLabel.setForeground(Color.RED);
					rLabel.setText("10자리까지 입력가능");
					//textField_id.setText(textField_id.getText().substring(0, 10));
				}
				if(textField_id.getText().length() < 10) {
					rLabel.setText("");
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				flag = false;
				if(textField_id.getText().length() == 10) {
					rLabel.setForeground(Color.RED);
					rLabel.setText("10자리까지 입력가능");
					//textField_id.setText(textField_id.getText().substring(0, 10));
				}
				if(textField_id.getText().length() < 10) {
					rLabel.setText("");
				}
			}
		});
		textField_id.setColumns(10);
		rPanel.add(textField_id);
		

		JLabel mPW = new JLabel("비밀번호");
		mPW.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/pw.png")));
		mPW.setBounds(70, 171, 105, 44);
		rPanel.add(mPW);

		textField_pw = new JTextField();
		textField_pw.setBounds(190, 176, 183, 30);
		textField_pw.setColumns(10);
		textField_pw.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(((JTextField)e.getSource()).getText().length() >= 15) {
					e.consume();
				}
			}
		});
		rPanel.add(textField_pw);
		

		JLabel mName = new JLabel("이름");
		mName.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/name.png")));
		mName.setBounds(70, 224, 65, 42);
		rPanel.add(mName);

		textField_name = new JTextField();
		textField_name.setBounds(190, 228, 183, 30);
		textField_name.setColumns(10);
		rPanel.add(textField_name);

		JLabel mPhone = new JLabel("번호");
		mPhone.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/num.png")));
		mPhone.setBounds(70, 279, 65, 42);
		rPanel.add(mPhone);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "010", "011", "016", "017", "018", "019" }));
		comboBox.setBounds(189, 283, 57, 30);
		rPanel.add(comboBox);

		JLabel lblNewLabel_8 = new JLabel("-");
		lblNewLabel_8.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		lblNewLabel_8.setBounds(258, 291, 25, 15);
		rPanel.add(lblNewLabel_8);

		textField_phone1 = new JTextField();
		textField_phone1.setBounds(280, 283, 62, 30);
		textField_phone1.setColumns(10);
		textField_phone1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(((JTextField)e.getSource()).getText().length() >= 4) {
					e.consume();
				}
			}
		});
		rPanel.add(textField_phone1);		

		JLabel label_2 = new JLabel("-");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		label_2.setBounds(354, 283, 13, 31);
		rPanel.add(label_2);

		textField_phone2 = new JTextField();
		textField_phone2.setColumns(10);
		textField_phone2.setBounds(370, 283, 62, 30);
		textField_phone2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(((JTextField)e.getSource()).getText().length() >= 4) {
					e.consume();
				}
			}
		});
		rPanel.add(textField_phone2);

		JLabel mMail = new JLabel("이메일");
		mMail.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/em.png")));
		mMail.setBounds(70, 336, 84, 42);
		rPanel.add(mMail);

		textField_mail = new JTextField();
		textField_mail.setBounds(188, 340, 121, 34);
		textField_mail.setColumns(10);
		rPanel.add(textField_mail);

		JLabel lblNewLabel_10 = new JLabel("@");
		lblNewLabel_10.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		lblNewLabel_10.setBounds(324, 347, 27, 15);
		rPanel.add(lblNewLabel_10);

		comboBox_1 = new JComboBox<String>();
		comboBox_1.setModel(new DefaultComboBoxModel<String>(
				new String[] { "-이메일 선택-", "naver.com", "daum.net", "nate.com",
						"hotmail.com", "yahoo.com", "empas.com", "korea.com", "dreamwiz.com", "gmail.com" }));
		comboBox_1.setBounds(355, 340, 121, 34);
		rPanel.add(comboBox_1);

		bg = new ButtonGroup();

		JLabel lblNewLabel = new JLabel("성별");
		lblNewLabel.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/se.png")));
		lblNewLabel.setBounds(70, 390, 65, 42);
		rPanel.add(lblNewLabel);

		rdbtnNewRadioButton = new JRadioButton("남성");
		rdbtnNewRadioButton.setBackground(new Color(241,230,208));
		rdbtnNewRadioButton.setBounds(200, 400, 113, 23);
		bg.add(rdbtnNewRadioButton);
		rPanel.add(rdbtnNewRadioButton);

		rdbtnNewRadioButton_1 = new JRadioButton("여성");
		rdbtnNewRadioButton_1.setBackground(new Color(241,230,208));
		rdbtnNewRadioButton_1.setBounds(325, 400, 113, 23);
		bg.add(rdbtnNewRadioButton_1);
		rPanel.add(rdbtnNewRadioButton_1);

		JButton bt2 = new JButton("등록하기");
		bt2.setBackground(new Color(172, 136, 102));
		bt2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		bt2.setForeground(Color.WHITE);
		bt2.addActionListener(new EventHandler());

		bt2.setBounds(190, 445, 196, 45);
		rPanel.add(bt2);

		JButton bt3 = new JButton("닫기");
		bt3.setBackground(new Color(172, 136, 102));
		bt3.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		bt3.setForeground(Color.WHITE);
		bt3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		bt3.setBounds(486, 447, 68, 45);
		rPanel.add(bt3);

		// 중복검사
		JButton btnNewButton = new JButton("중복검사");
		btnNewButton.setBackground(new Color(172, 136, 102));
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField_id.getText().equals("")) {
					final Dialog mIDInfo = new Dialog(frame, "Information", true);
					mIDInfo.setSize(200, 100);
					mIDInfo.setLayout(new FlowLayout());
					JLabel msg = new JLabel("아이디를 입력해주세요");
					JButton ok = new JButton("확인");
					mIDInfo.setLocationRelativeTo(null);
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							mIDInfo.setVisible(false);
							mIDInfo.dispose();
						}
					});
					mIDInfo.add(msg);
					mIDInfo.add(ok);
					mIDInfo.setVisible(true);
					textField_id.requestFocus();
				} else if (!Pattern.matches("^[0-9a-zA-Z]*$", textField_id.getText()) || Pattern.matches("^[0-9]*$", textField_id.getText()) || Pattern.matches("^[ㄱ-ㅎㅏ-ㅣ가-힣]*$", textField_id.getText()) || Pattern.matches("^[a-zA-Z]*$", textField_id.getText())) {
					final Dialog mIDInfo1 = new Dialog(frame, "Information", true);
					mIDInfo1.setSize(200, 100);
					mIDInfo1.setLocationRelativeTo(null);
					mIDInfo1.setLayout(new FlowLayout());
					JLabel msg = new JLabel("영어와 숫자 조합으로 해주세요.");
					JButton ok = new JButton("확인");
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							mIDInfo1.setVisible(false);
							mIDInfo1.dispose();
						}
					});
					mIDInfo1.add(msg);
					mIDInfo1.add(ok);
					mIDInfo1.setVisible(true);
					textField_id.requestFocus();
					textField_id.selectAll();
				} else {
					ManagementDAO dao = new ManagementDAO();
					String str = dao.idcheck(textField_id.getText());
					if (textField_id.getText().equals(str)) {
						textField_id.requestFocus();
						textField_id.setText("");
						rLabel.setForeground(Color.RED);
						rLabel.setText("아이디중복");
					} else {
						rLabel.setForeground(Color.BLUE);
						rLabel.setText("사용가능");
						flag = true;
					}
				}
			}
		});
		btnNewButton.setBounds(400, 120, 97, 32);
		rPanel.add(btnNewButton);

		rLabel = new JLabel("");
		rLabel.setBounds(190, 155, 126, 15);
		rPanel.add(rLabel);

		setVisible(true);
	}

	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			StringBuffer sb1 = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();

			// 아이디
			String mID = textField_id.getText();

			// 비번
			String mPw = textField_pw.getText();

			// 성명
			String mName = textField_name.getText();

			String mGender = "";
			if (rdbtnNewRadioButton.isSelected()) {
				mGender = rdbtnNewRadioButton.getText();
			}
			if (rdbtnNewRadioButton_1.isSelected()) {
				mGender = rdbtnNewRadioButton_1.getText();
			}
			
			// 전화번호
			sb1.append(comboBox.getSelectedItem().toString());
			sb1.append("-");
			sb1.append(textField_phone1.getText());
			sb1.append("-");
			sb1.append(textField_phone2.getText());

			// 이메일
			sb2.append(textField_mail.getText() + "@");
			sb2.append(comboBox_1.getSelectedItem().toString());
			
			if (mID.equals("")) {
				final Dialog mIDInfo = new Dialog(frame, "Information", true);
				mIDInfo.setSize(200, 100);
				mIDInfo.setLayout(new FlowLayout());
				JLabel msg = new JLabel("아이디를 입력해주세요");
				JButton ok = new JButton("확인");
				mIDInfo.setLocationRelativeTo(null);
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mIDInfo.setVisible(false);
						mIDInfo.dispose();
					}
				});
				mIDInfo.add(msg);
				mIDInfo.add(ok);
				mIDInfo.setVisible(true);
				textField_id.requestFocus();
				textField_id.selectAll();
			} else if (mPw.equals("")) {
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
				textField_pw.requestFocus();
				textField_pw.selectAll();
			} else if (mName.equals("")) {
				final Dialog mNameInfo = new Dialog(frame, "Information", true);
				mNameInfo.setSize(200, 100);
				mNameInfo.setLocationRelativeTo(null);
				mNameInfo.setLayout(new FlowLayout());
				JLabel msg = new JLabel("이름을 입력해주세요");
				JButton ok = new JButton("확인");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mNameInfo.setVisible(false);
						mNameInfo.dispose();
					}
				});
				mNameInfo.add(msg);
				mNameInfo.add(ok);
				mNameInfo.setVisible(true);
				textField_name.requestFocus();
				textField_name.selectAll();
			} else if (!Pattern.matches("^[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z]*$", mName)) {
				final Dialog mNameInfo = new Dialog(frame, "Information", true);
				mNameInfo.setSize(200, 100);
				mNameInfo.setLocationRelativeTo(null);
				mNameInfo.setLayout(new FlowLayout());
				JLabel msg = new JLabel("이름은 영어 또는 한글만 입력가능.");
				JButton ok = new JButton("확인");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mNameInfo.setVisible(false);
						mNameInfo.dispose();
					}
				});
				mNameInfo.add(msg);
				mNameInfo.add(ok);
				mNameInfo.setVisible(true);
				textField_name.requestFocus();
				textField_name.selectAll();
			} else if (textField_phone1.getText().equals("") || textField_phone2.getText().equals("")) {
				final Dialog mPhoneInfo = new Dialog(frame, "Information", true);
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
				textField_phone1.requestFocus();
				textField_phone1.selectAll();
			} else if (!integerOrNot(sb1.toString())) {
				final Dialog mPhoneInfo1 = new Dialog(frame, "Information", true);
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
				textField_phone1.requestFocus();
				textField_phone1.selectAll();
			} else if (textField_mail.getText().equals("") || comboBox_1.getSelectedIndex() == 0) {
				final Dialog mMailInfo = new Dialog(frame, "Information", true);
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
				textField_mail.requestFocus();
				textField_mail.selectAll();
			} else if (!Pattern.matches("^[0-9a-zA-Z]*$", textField_mail.getText()) || Pattern.matches("^[0-9]*$", textField_mail.getText())) {
				final Dialog mMailInfo = new Dialog(frame, "Information", true);
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
				textField_mail.requestFocus();
				textField_mail.selectAll();
			} else if (!rdbtnNewRadioButton.isSelected() && !rdbtnNewRadioButton_1.isSelected()) {
				final Dialog mGenderInfo = new Dialog(frame, "Information", true);
				mGenderInfo.setSize(200, 100);
				mGenderInfo.setLocationRelativeTo(null);
				mGenderInfo.setLayout(new FlowLayout());
				JLabel msg = new JLabel("성별을 선택해주세요");
				JButton ok = new JButton("확인");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mGenderInfo.setVisible(false);
						mGenderInfo.dispose();
					}
				});
				mGenderInfo.add(msg);
				mGenderInfo.add(ok);
				mGenderInfo.setVisible(true);
			} else if (!flag) {
				final Dialog mChkInfo = new Dialog(frame, "Information", true);
				mChkInfo.setSize(200, 100);
				mChkInfo.setLocationRelativeTo(null);
				mChkInfo.setLayout(new FlowLayout());
				JLabel msg = new JLabel("아이디 중복검사를 해주세요");
				JButton ok = new JButton("확인");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mChkInfo.setVisible(false);
						mChkInfo.dispose();
					}
				});
				mChkInfo.add(msg);
				mChkInfo.add(ok);
				mChkInfo.setVisible(true);
			} else {
				ManagementDTO dto = new ManagementDTO();
				dto.setM_id(mID);
				dto.setM_pw(mPw);
				dto.setM_name(mName);
				dto.setM_gender(mGender);
				dto.setM_tel(sb1.toString());
				dto.setM_email(sb2.toString());
				ManagementDAO dao = new ManagementDAO();
				int data = dao.insertMember(dto);
				System.out.println("입력자료:" + data);
				dispose();
			}
		}
	}

	public boolean integerOrNot(String memData){ // 입력값이 숫자인지 문자인지 판별 : 
		char[] charData = memData.replace("-", "").toCharArray();
		boolean check=true;
		while(check){
			for(int i=0; i<charData.length; i++){		
				if(Character.isDigit(charData[i]) == false){
						check = false;
						break;
				}
			}
			break;	
		}
		return check;
	}
}
