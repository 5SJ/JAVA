package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Color;

public class ManagerMain extends JFrame {

	static JPanel panel;
	static CardLayout card = new CardLayout();
	
	JButton btnNewButton;
	JButton button;
	JButton button_1, button_2, button_3;
	static BookRental br;
	static BookOut bo;
	static BookManager bm;
	static MemberManager mm;
	static DelayManager dm;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Toolkit tk=Toolkit.getDefaultToolkit();
					Dimension screenSize=tk.getScreenSize();
					ManagerMain frame = new ManagerMain();
					br = new BookRental();
					bo = new BookOut();
					bm = new BookManager();
					mm = new MemberManager();
					dm = new DelayManager();
					frame.panel.add(br,"1");
					frame.panel.add(bo,"2");
					frame.panel.add(bm,"3");
					frame.panel.add(mm,"4");
					frame.panel.add(dm,"5");
					frame.panel.add(new BookReturn_plan(),"6");
					frame.card.show(frame.panel, "6");
					frame.add(frame.panel);
					frame.setVisible(true);
					frame.setLocation((screenSize.width-frame.getWidth())/2,(screenSize.height-frame.getHeight())/2);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManagerMain() {
		setTitle("그린도서관 관리자");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);		
		
		setLayout(null);	
		setResizable(false);
		
		btnNewButton = new JButton("도서대출");
		btnNewButton.setBackground(new Color(120, 81, 48));
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnNewButton.setForeground(new Color(241, 230, 208));
		btnNewButton.setBorderPainted(false); //0414
		btnNewButton.setFocusPainted(false);  //0414
		btnNewButton.setBounds(0, 0, 197, 50);
		add(btnNewButton);
		
		button = new JButton("도서반납");
		button.setBackground(new Color(120, 81, 48));
		button.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		button.setForeground(new Color(241, 230, 208));
		button.setBorderPainted(false); //0414
		button.setFocusPainted(false);  //0414
		button.setBounds(197, 0, 197, 50);
		add(button);
		
		button_1 = new JButton("도서관리");
		button_1.setBackground(new Color(120, 81, 48));
		button_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		button_1.setForeground(new Color(241, 230, 208));
		button_1.setBorderPainted(false); //0414
		button_1.setFocusPainted(false);  //0414
		button_1.setBounds(394, 0, 197, 50);
		add(button_1);
		
		button_2 = new JButton("회원관리");
		button_2.setBackground(new Color(120, 81, 48));
		button_2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		button_2.setForeground(new Color(241, 230, 208));
		button_2.setBorderPainted(false); //0414
		button_2.setFocusPainted(false);  //0414
		button_2.setBounds(591, 0, 197, 50);
		add(button_2);		
		
		button_3 = new JButton("연체관리");
		button_3.setBackground(new Color(120, 81, 48));
		button_3.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		button_3.setForeground(new Color(241, 230, 208));
		button_3.setBorderPainted(false); //0414
		button_3.setFocusPainted(false);  //0414
		button_3.setBounds(788, 0, 197, 50);
		add(button_3);	
		
		panel = new JPanel();
		panel.setBounds(0, 50, 1000, 650);
		panel.setBackground(new Color(241, 230, 208));
		panel.setLayout(card);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.remove(br);
				br = new BookRental();
				panel.add(br, "1");
				panel.setVisible(true);
				card.show(panel,"1");
			}
		});		
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.remove(bo);
				bo = new BookOut();
				panel.add(bo, "2");
				panel.setVisible(true);
				card.show(panel,"2");
			}
		});	
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.remove(br);
				bm = new BookManager();
				panel.add(bm, "3");
				panel.setVisible(true);
				card.show(panel,"3");
			}
		});		
			
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.remove(mm);
				mm = new MemberManager();
				panel.add(mm, "4");
				panel.setVisible(true);
				card.show(panel,"4");
			}
		});
		
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.remove(dm);
				dm = new DelayManager();
				panel.add(dm, "5");
				panel.setVisible(true);
				card.show(panel,"5");
			}
		});
	}
}
