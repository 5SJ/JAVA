package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.NoteDAO;
import dto.NoteDTO;
import net.UDPSocketNet;

public class NoteReadFrame extends JFrame {
	NoteDTO ndto;
	NoteDAO ndao;
	String subject;
	String contents;
	public NoteReadFrame(NoteDTO ndto, UDPSocketNet usk) {
		super("그린도서관 받은 쪽지");
		this.ndto = ndto;
		System.out.println("생성자 호출");
		getContentPane().setLayout(null);
		setBounds(0, 0, 486, 410);//0416
		setBackground(Color.pink);
		JPanel namePan = new JPanel();
		namePan.setBackground(new Color(172, 136, 102));
		namePan.setLayout(null);
		namePan.setBounds(0, 0, 472, 30);//0416
		
		
		
		JTextField name = new JTextField();
		name.setBorder(null);
		name.setForeground(new Color(255, 255, 255));
		name.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		name.setBackground(new Color(172, 136, 102));
		name.setBounds(82, 0, 391, 30);//0416
		name.setText(ndto.getN_sid());
		name.setFocusable(false);
		namePan.add(name);
		
		JPanel titlePan = new JPanel();
		titlePan.setBackground(new Color(241, 230, 208));
		titlePan.setLayout(null);
		titlePan.setBounds(0, 30, 472, 30);//0416
		
		JPanel conPan = new JPanel();
		conPan.setLayout(null);
		conPan.setBounds(0, 60, 485, 200);//0416
		
		JPanel btnPan = new JPanel();
		btnPan.setLayout(null);
		btnPan.setBounds(0, 260, 485, 410);//0416
		btnPan.setBackground(new Color(241,230,208));
		
		JButton resend_btn = new JButton("답장");
		resend_btn.setForeground(Color.WHITE);
		resend_btn.setBackground(new Color(120, 81, 48));
		resend_btn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		resend_btn.setLayout(null);
		resend_btn.setBounds(100, 28, 115, 45);
		
		JButton exit_btn = new JButton("닫기");
		exit_btn.setForeground(Color.WHITE);
		exit_btn.setBackground(new Color(120, 81, 48));
		exit_btn.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		exit_btn.setLayout(null);
		exit_btn.setBounds(270, 28, 115, 45);
		exit_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnPan.add(resend_btn);
		btnPan.add(exit_btn);
		
		JTextField title = new JTextField(ndto.getN_subject());
		title.setBorder(null);
		title.setBackground(new Color(241, 230, 208));
		title.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		title.requestFocus(false);
		title.setOpaque(true);
		title.setBounds(40, 0, 391, 30);
		title.setFocusable(false);
		titlePan.add(title);
		JTextArea con = new JTextArea(ndto.getN_contents());
		con.setBorder(null);
		con.setBackground(new Color(172, 136, 102));
		con.setForeground(new Color(255, 255, 255));
		con.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		JScrollPane sp = new JScrollPane(con);
		con.setLineWrap(true);
		con.setFocusable(false);
		sp.setOpaque(true);
		sp.setBounds(0, 0, 475, 200);
		conPan.add(sp);
		
		resend_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NoteWriteFrame(ndto.getN_rid(), ndto.getN_sid(), usk);
				dispose();
			}
		});
		getContentPane().add(namePan);
		
		JLabel namelb = new JLabel("보낸 사람:");
		namelb.setForeground(Color.WHITE);
		namelb.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		namelb.setBounds(0, 0, 82, 30);
		namePan.add(namelb);
		getContentPane().add(titlePan);
		
		JLabel lblNewLabel = new JLabel("제목:");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblNewLabel.setBounds(0, 0, 40, 30);
		titlePan.add(lblNewLabel);
		getContentPane().add(conPan);
		getContentPane().add(btnPan);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		
		
	}
}