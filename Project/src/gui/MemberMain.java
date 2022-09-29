package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

import java.awt.CardLayout;
import net.UDPSocketNet;
import java.awt.Font;
import java.awt.Color;

public class MemberMain extends JFrame {
	static UDPSocketNet usk;
	JPanel panel;
	CardLayout card=new CardLayout();
	
	JButton btnNewButton, button, button_1, button_3;
	public static JButton button_2;
	static String loginId;
	static SeatReservFrame srf;
	static MyPagePanel mpp;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Toolkit tk=Toolkit.getDefaultToolkit();
					Dimension screenSize=tk.getScreenSize();
					
					MemberMain frame = new MemberMain(loginId);
					usk = new UDPSocketNet(loginId);
					srf = new SeatReservFrame(loginId, usk);
					mpp = new MyPagePanel(loginId, usk);
					frame.panel.add(new BookSearch(),"1");
					frame.panel.add(new BookRentalList(loginId),"2");
					frame.panel.add(srf.jsp,"3"); //도서 자리예약
					frame.panel.add(mpp,"4");
					frame.getContentPane().add(frame.panel);
					frame.setVisible(true);
					frame.setLocation((screenSize.width-frame.getWidth())/2,(screenSize.height-frame.getHeight())/2);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Map<String, String> mm = new HashMap<String, String>();
		mm.forEach((key, value){
			
		});
	}

	/**
	 * Create the frame.
	 * @param id 
	 */
	public MemberMain(String id) {
		MemberMain.loginId = id;
		
		setTitle("그린도서관 회원");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);		//0414	
		
		getContentPane().setLayout(null);	
		setResizable(false);
		
		btnNewButton = new JButton("도서검색");
		btnNewButton.setBackground(new Color(120, 81, 48));
		btnNewButton.setForeground(new Color(241, 230, 208));
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnNewButton.setBounds(0, 0, this.getWidth()/4, 49);
		btnNewButton.setBorderPainted(false);
		getContentPane().add(btnNewButton);
		
		
		button = new JButton("도서대출목록");
		button.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		button.setBackground(new Color(120, 81, 48));
		button.setForeground(new Color(241, 230, 208));
		button.setBounds(this.getWidth()/4, 0, this.getWidth()/4, 49);
		button.setBorderPainted(false);
		getContentPane().add(button);
		
		button_1 = new JButton("도서관자리예약");
		button_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		button_1.setBackground(new Color(120, 81, 48));
		button_1.setForeground(new Color(241, 230, 208));
		button_1.setBounds(this.getWidth()/4*2, 0, this.getWidth()/4, 49);
		button_1.setBorderPainted(false);
		getContentPane().add(button_1);

		button_3 = new JButton("마이페이지");
		button_3.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		button_3.setBackground(new Color(120, 81, 48));
		button_3.setForeground(new Color(241, 230, 208));
		button_3.setBounds(this.getWidth()/4*3, 0, this.getWidth()/4, 49);
		button_3.setBorderPainted(false);
		getContentPane().add(button_3);
		panel = new JPanel();
		panel.setBounds(0, 48, 1000, 700); //0414
		panel.setLayout(card);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookSearch.select();
				panel.setVisible(true);
				card.show(panel,"1");
			}
		});		
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BookRentalList.select(loginId);
				panel.setVisible(true);
				card.show(panel,"2");
			}
		});	
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.remove(srf.jsp);
				srf = new SeatReservFrame(loginId, usk);
				panel.add(srf.jsp,"3"); //도서 자리예약
				panel.setVisible(true);
				card.show(panel,"3");
			}
		});	
		
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.remove(mpp);
				mpp = new MyPagePanel(id, usk);
				panel.add(mpp, "4");
				panel.setVisible(true);
				card.show(panel,"4");
			}
		});
	}
}