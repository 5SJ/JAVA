package gui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ManagementDAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class ManagerLogin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnNewButton;
	private static ManagerLogin frame;
	private static int x, y;

	public ManagerLogin() {
		setTitle("관리자 로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 309);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(241, 230, 208));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel logoimg = new JLabel();
		logoimg.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/logoimg_1.png")));
		logoimg.setBounds(120, 0, 232, 109);
		contentPane.add(logoimg);
		
		JLabel adminID = new JLabel();
		adminID.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/textimg_2.png")));
		adminID.setBounds(12, 124, 106, 37);
		contentPane.add(adminID);
		
		JLabel adminPWD = new JLabel();
		adminPWD.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/textimg_1.png")));
		adminPWD.setBounds(12, 170, 106, 37);
		contentPane.add(adminPWD);
		
		textField = new JTextField();
		textField.setBounds(130, 124, 181, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(130, 170, 181, 29);
		contentPane.add(passwordField);
		
		btnNewButton = new JButton("로그인");
		btnNewButton.setBackground(new Color(172, 136, 102));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnNewButton.setBounds(317, 124, 97, 75);
		contentPane.add(btnNewButton);
		
		textField.addActionListener(new EventHandler());
		passwordField.addActionListener(new EventHandler());
		btnNewButton.addActionListener(new EventHandler());
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		setSize(440,300);
		setLocation((screenSize.width-getWidth())/2,(screenSize.height-getHeight())/2);
		x=screenSize.width-getWidth()/2;
		y=screenSize.height-getHeight()/2;
		setVisible(true);
	}
	
	
//	class EventHandler implements ActionListener{
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// TODO Auto-generated method stub
//			String id=textField.getText();
//			String pwd=passwordField.getText();
//			if(!id.equals("aaaa")){
//				final Dialog idInfo=new Dialog(frame,"Information",true);
//				idInfo.setSize(150,90);
//				idInfo.setLocation((x-idInfo.getWidth())/2+70,(y-idInfo.getHeight())/2+70);	
//				idInfo.setLayout(new FlowLayout());
//				JLabel msg=new JLabel("아이디를 확인해주세요");
//				JButton ok=new JButton("확인");
//				ok.addActionListener(new ActionListener(){
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						// TODO Auto-generated method stub
//						idInfo.setVisible(false);
//						idInfo.dispose();
//					}				
//				});
//				idInfo.add(msg);
//				idInfo.add(ok);
//				idInfo.setVisible(true);
//				textField.requestFocus();
//				textField.selectAll();
//			}
//			else if(!pwd.equals("1111")){
//				final Dialog pwdInfo=new Dialog(frame,"Information",true);
//				pwdInfo.setSize(170,90);
//				pwdInfo.setLocation((x-pwdInfo.getWidth())/2+90,(y-pwdInfo.getHeight())/2+70);	
//				pwdInfo.setLayout(new FlowLayout());
//				JLabel msg=new JLabel("비밀번호를 확인해주세요");
//				JButton ok=new JButton("확인");
//				ok.addActionListener(new ActionListener(){
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						// TODO Auto-generated method stub
//						pwdInfo.setVisible(false);
//						pwdInfo.dispose();
//					}				
//				});
//				pwdInfo.add(msg);
//				pwdInfo.add(ok);
//				pwdInfo.setVisible(true);
//				passwordField.requestFocus();
//				passwordField.selectAll();
//			}
//			else
//			{
//				ManagerMain Mainframe=new ManagerMain();
//				String[] str={"메인호출"};
//				Mainframe.main(str);
//				frame.setVisible(false);
//			}
//		}
//		
//	}

	class EventHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String id = textField.getText();
			String pwd = passwordField.getText();
			
			
			
			System.out.println(id+" : "+pwd);
			ManagementDAO mdao = new ManagementDAO();
			int result = mdao.idPassword(id, pwd);
			if (result == 9) {
				JOptionPane.showMessageDialog(null, id + "관리자님 반갑습니다.");
				ManagerMain Mainframe = new ManagerMain();
				String[] str = { "메인호출" };
				Mainframe.main(str);
				dispose();
			} else if (result == 2 || result == 3) {
				JOptionPane.showMessageDialog(null, "관리자 전용입니다.");
			} else {
				final Dialog idInfo = new Dialog(frame, "Information", true);
				idInfo.setSize(150, 90);
				idInfo.setLocation((x - idInfo.getWidth()) / 2 + 70, (y - idInfo.getHeight()) / 2 + 70);
				idInfo.setLayout(new FlowLayout());
				JLabel msg = new JLabel("다시 입력해주세요.");
				JButton ok = new JButton("확인");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						idInfo.setVisible(false);
						idInfo.dispose();
					}
				});
				idInfo.add(msg);
				idInfo.add(ok);
				idInfo.setVisible(true);
				textField.requestFocus();
				textField.selectAll();
				textField.setText("");
				passwordField.setText("");
			}
				
		}

	}
}
