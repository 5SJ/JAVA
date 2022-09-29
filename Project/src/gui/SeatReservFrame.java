package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dao.NoteDAO;
import dao.SeatDAO;
import dto.NoteDTO;
import dto.SeatDTO;
import net.UDPSocketNet;

public class SeatReservFrame {
	static SeatDAO sdao;
	public static SeatPanel sp;
	public static JScrollPane jsp;
	static String id = null;
	static ArrayList<SeatDTO> list;
	static UDPSocketNet usk;
	public SeatReservFrame(String id, UDPSocketNet usk) {
		sdao = new SeatDAO();
		this.id = id;
		this.usk = usk;
		sp = new SeatPanel();
		
		sdao.Used_Drop();
		jsp = new JScrollPane();
		jsp.setViewportView(sp);
		jsp.setLayout(null);
		jsp.setBounds(0, 0, 783, 419);
		jsp.setOpaque(true);
		jsp.setPreferredSize(new Dimension(783, 419));
		jsp.getHorizontalScrollBar();
		jsp.add(sp);
	}
	public static class SeatPanel extends JPanel{
		public SeatPanel() {
			JPanel panel = new JPanel();
			list = sdao.Seat_List();
			setBounds(0, 1, 1000, 650); //0414
			
		
//			setLayout(null);
			setPreferredSize(new Dimension(783, 819));
			setBackground(new Color(241,230,208));
			setOpaque(true);
			
			GridLayout layout = new GridLayout();
			panel.setLayout(layout);
			
			JButton seat = null;
//			int x = 50;   //0414
//			int y = 20;
			SeatResever sr;
			for (SeatDTO s : list) {
				sr = new SeatResever(s.getS_num(), s.getS_used());
				seat = new JButton(s.getS_num()+"");
				seat.setFont(new Font("맑은 고딕",Font.BOLD,15));
				seat.setLayout(null);
				seat.setOpaque(true);
				if(s.getS_used().equals("N")) { //빈자리
					seat.setBackground(new Color(241,230,208));	
					seat.setBorderPainted(false); //0414
					seat.setFocusPainted(false);  //0414
					seat.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/ch1.png"))); //0414 
					seat.addActionListener(sr);;
				}else {
					if(s.getS_id() !=null && s.getS_id().equals(id)) { //예약
						seat.setBackground(new Color(241,230,208));
						seat.setBorderPainted(false); //0414
						seat.setFocusPainted(false);  //0414
						seat.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/here1.png"))); //0414
						seat.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								String msg = "좌석반납을 하시겠습니까?";
								String title = "반납";
								int result = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION);
								if(result == 0) {
									if(sdao.returnSeat(s.getS_id(), s.getS_num()) == 1) {
										JOptionPane.showMessageDialog(null, s.getS_id()+"님 "+s.getS_num()+"번 자리 반납이 완료 되었습니다.", "반납완료", JOptionPane.PLAIN_MESSAGE);
										ref();
										/*
										jsp.remove(sp);
										sp = new SeatPanel();
										jsp.add(sp);
										jsp.revalidate();
										*/
									}
								}
							}
						});
					}else {
						seat.setBorderPainted(false); //0414
						seat.setFocusPainted(false);  //0414
						seat.setBackground(new Color(241,230,208)); //쓰고있는자리
						seat.setIcon(new ImageIcon(MemberJoin.class.getResource("/img/ch4.png"))); //0414
						seat.addActionListener(sr);;
					}
				}
				add(seat);
//				x += 120;  //0414
//				if(x >= 930) { //0414
//					x = 50;    //0414
//					y += 120; //0414
//				}
			}
		}
	}
	public static class SeatResever implements ActionListener {
		String msg;
		String title;
		int num;
		String used;
		int res;
		public SeatResever(int num, String used) {
			this.num = num;
			this.used = used;
			
			if(used.equals("N")) {
				msg = "좌석을 이용하시겠습니까?";
				title = "사용";
			}else {
				msg = "좌석예약을 하시겠습니까?";
				title = "예약";
			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION);
			System.out.println(result);
			if(used.equals("N") && result == 0) {
				res = sdao.Used(num, used, id);
				if(res == 1) {
					System.out.println("자리사용 성공");
					JOptionPane.showMessageDialog(null, id+"님 "+num+"번 자리 사용이 완료 되었습니다.", "사용완료", JOptionPane.PLAIN_MESSAGE);
					try {
						String msg = "seatUsed";
						usk.sender_socket
								.send(new DatagramPacket(msg.getBytes(), msg.length(), usk.sender_addr, 8888));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}else if(res == -2){
					System.out.println("자리중복사용 불가");
					ArrayList<SeatDTO> al = sdao.Used_List(id);
					JOptionPane.showMessageDialog(null, 
							al.get(0).getS_id()+"님은 "+al.get(0).getS_num()+"번 자리 "+al.get(0).getS_edate()+"까지 사용중이십니다.",
							"중복사용", JOptionPane.ERROR_MESSAGE);
				}else if(res == -3){
					System.out.println("예약중사용 불가");
					ArrayList<SeatDTO> al = sdao.Resever_list(id);
					JOptionPane.showMessageDialog(null, 
							al.get(0).getS_id()+"님은 "+al.get(0).getS_num()+"번 자리 "+al.get(0).getS_date()+"에 예약중이십니다.",
							"중복사용", JOptionPane.ERROR_MESSAGE);
				}
			}
			if(used.equals("Y") && result == 0) {
				res = sdao.Resever(num, id);
				if(res == -2) {
					System.out.println("자리예약 불가");
					ArrayList<SeatDTO> al = sdao.Used_List(id);
					al.get(0).getS_id();
					JOptionPane.showMessageDialog(null, 
							al.get(0).getS_id()+"님은 "+al.get(0).getS_num()+"번 자리 "+al.get(0).getS_edate()+"까지 사용중이십니다.",
							"중복사용", JOptionPane.ERROR_MESSAGE);
				}else if(res == -3){
					System.out.println("예약 불가");
					ArrayList<SeatDTO> al = sdao.Resever_list(id);
					JOptionPane.showMessageDialog(null, 
							al.get(0).getS_id()+"님은 "+al.get(0).getS_num()+"번 자리 "+al.get(0).getS_date()+"에 예약중이십니다.",
							"중복사용", JOptionPane.ERROR_MESSAGE);
					if(num == al.get(0).getS_num()) {
						int rr = JOptionPane.showConfirmDialog(null, "예약을 취소 하시겠습니까?", "예약취소", JOptionPane.YES_NO_OPTION);
						if(rr == JOptionPane.YES_OPTION) {
							int c = sdao.Cancel(id);
							if(c == 1) {
								NoteDTO ndto = new NoteDTO();
								NoteDAO ndao = new NoteDAO();
								ndto.setN_sid("lib2022");
								ndto.setN_rid(id);
								ndto.setN_subject(id + " 회원님, 자리예약 취소");
								ndto.setN_contents(id+"님, "+ al.get(0).getS_date() + " 에 예약하신" + al.get(0).getS_num() +"번 자리 예약취소하셨습니다.\r\n다른 자리예약 또는 사용 가능합니다.");
								int r = ndao.note_write(ndto);
								if(r == 1) {
									JOptionPane.showMessageDialog(null, id+"님 "+num+"번 자리 예약취소되었습니다.", "예약취소", JOptionPane.PLAIN_MESSAGE);
								}
							}
						}
					}
				} else if(res == -1){
					JOptionPane.showMessageDialog(null, "예약오류", "예약오류", JOptionPane.ERROR_MESSAGE);
				} else {
					System.out.println("예약 성공");
					ArrayList<SeatDTO> al = sdao.Resever_list(id);
					NoteDTO ndto = new NoteDTO();
					NoteDAO ndao = new NoteDAO();
					ndto.setN_sid("lib2022");
					ndto.setN_rid(id);
					ndto.setN_subject(id + " 회원님, 자리예약 내역");
					ndto.setN_contents(id+"님, "+ al.get(0).getS_num() +"번 자리 예약하셨습니다.\r\n예약하신 자리는 "+ al.get(0).getS_date() + " 부터 사용 가능합니다.");
					int r = ndao.note_write(ndto);
					if(r == 1) {
						JOptionPane.showMessageDialog(null, id+"님 "+num+"번 자리 예약되었습니다.", "예약완료", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
	}
	public static void ref() {
		jsp.remove(sp);
		sp = new SeatPanel();
		jsp.add(sp);
		jsp.revalidate();
	}
}