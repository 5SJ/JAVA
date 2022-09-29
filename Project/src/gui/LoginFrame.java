package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.ManagementDAO;

import java.awt.Color;
import javax.swing.ImageIcon;

public class LoginFrame extends JFrame {

	private JPanel lPanel;
	private JLabel lId, lPassword;
	private JTextField tId;
	private JPasswordField tPassword;
	private JButton login, register, exit, out, manager;

	public LoginFrame() {
		super("그린 도서관");
		super.setResizable(true);
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setResizable(false);
  
		lPanel = new JPanel();
		lPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(lPanel);
		lPanel.setBackground(new Color(120,81,48));
		lPanel.setLayout(null);
		
		lId = new JLabel("아이디");
		lId.setForeground(Color.WHITE);
		lId.setBounds(506, 262, 69, 35);
		lId.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		lPanel.add(lId);

		tId = new JTextField();
		tId.setBounds(615, 262, 193, 35);
		tId.setColumns(10);
		lPanel.add(tId);

		lPassword = new JLabel("비밀번호");
		lPassword.setForeground(Color.WHITE);
		lPassword.setBounds(506, 313, 69, 35);
		lPassword.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		lPanel.add(lPassword);

		tPassword = new JPasswordField();
		tPassword.setBounds(615, 313, 193, 35);
		tPassword.setColumns(10);
		lPanel.add(tPassword);

		login = new JButton("로그인");
		login.setBackground(new Color(245, 222, 179));
		login.setBounds(847, 262, 102, 86);
		login.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lPanel.add(login);
		
		register = new JButton("회원가입");
		register.setForeground(Color.WHITE);
		register.setBackground(new Color(172, 136, 102));
		register.setBounds(108, 525, 281, 42);
		register.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lPanel.add(register);
		
		exit = new JButton("닫기");
		exit.setBackground(new Color(245, 222, 179));
		exit.setBounds(847, 375, 102, 42);
		exit.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lPanel.add(exit);
		
		manager = new JButton("관리자로그인"); 
		manager.setBackground(new Color(245, 222, 179));
		manager.setBounds(789, 611, 166, 42);
		manager.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lPanel.add(manager);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 0, 449, 663);
		panel.setBackground(new Color(241,230,208));
		lPanel.add(panel);
		
		JLabel logo_img = new JLabel();
		logo_img.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/logo_main.png")));
		logo_img.setBounds(0, 50, 800, 150);
		panel.add(logo_img);
		
		JLabel text_img = new JLabel();
		text_img.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/name_1.png")));
		text_img.setBounds(536, 76, 375, 122);
		lPanel.add(text_img);
		
		JLabel login_img2 = new JLabel();
		login_img2.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/login_1.png")));
		login_img2.setBounds(489, 401, 505, 272);
		lPanel.add(login_img2);
		
		JLabel text_img2 = new JLabel();
		text_img2.setIcon(new ImageIcon(LoginFrame.class.getResource("/img/name_4.png")));
		text_img2.setBounds(489, 441, 207, 77);
		lPanel.add(text_img2);
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		login.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String id = tId.getText();
				String password = tPassword.getText();
				ManagementDAO mdao = ManagementDAO.getInstance();

				int result = mdao.idPassword(id, password);
				if (result == 1 || result == 9) {
					JOptionPane.showMessageDialog(null, id+"님 반갑습니다.");
					MemberMain mm = new MemberMain(id);
					mm.main(null);
					dispose();
				} else if(result == 2) {
					JOptionPane.showMessageDialog(null, "탈퇴한 회원입니다.");
					tId.setText("");
					tId.requestFocus(true);
					tPassword.setText("");
				} else if(result == 3) {
					JOptionPane.showMessageDialog(null, "이용이 정지된 회원입니다.");
					tId.setText("");
					tId.requestFocus(true);
					tPassword.setText("");
				} else {
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 확인해주세요.");
					tId.setText("");
					tId.requestFocus(true);
					tPassword.setText("");
				}
			}
		});

		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new MemberJoin();
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		manager.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManagerLogin();
				dispose();
			}
		});
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new LoginFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}