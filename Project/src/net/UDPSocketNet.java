package net;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import gui.MyPagePanel;
import gui.NoteFrame;
import gui.SeatReservFrame;
import gui.TablePanel;
import gui.SeatReservFrame.SeatPanel;

public class UDPSocketNet {
	public static MulticastSocket sender_socket = null;
	public InetAddress sender_addr;
	
	String loginID;
	public boolean signal;

	class MultiReceiver extends Thread{
		MulticastSocket socket = null;
		String msg;
		@Override
		public void run() {
			try {
				socket = new MulticastSocket(Integer.parseInt("8888"));
				socket.joinGroup(InetAddress.getByName("230.0.0.1"));
				while(true) {
					byte [] buf = new byte[1024];
					DatagramPacket dp = new DatagramPacket(buf, buf.length);
					socket.receive(dp);
					signal = false;
					msg = new String(buf).trim();
					if(msg != null && msg.equals("seatUsed")) {
						SeatReservFrame.ref();
					}
					if(msg != null && msg.equals(loginID)) {
						signal = true;
						System.out.println("나한테 쪽지 왔다");
						MyPagePanel.getNoteCount(loginID);
						NoteFrame.sp.remove(NoteFrame.tablePan);
						NoteFrame.tp = new TablePanel(loginID, NoteFrame.usk);
						NoteFrame.chkList = NoteFrame.tp.chkList;
						NoteFrame.tablePan = NoteFrame.tp;
						NoteFrame.sp.setViewportView(NoteFrame.tablePan);
						NoteFrame.sp.revalidate();
					}
				}
			} catch (Exception e) {
			} finally {
				try {
					socket.leaveGroup(InetAddress.getByName("230.0.0.1"));
					socket.close();
				} catch (Exception e) {
					System.out.println("여기서 에러");
					// e.printStackTrace();
				}
			}
		}
	}
	public static MultiReceiver mr;
	public UDPSocketNet(String loginID) {
		this.loginID = loginID;
		try {
			sender_socket = new MulticastSocket();
			sender_addr = InetAddress.getByName("230.0.0.1");
			
			mr = new MultiReceiver();
			mr.start();
		} catch (Exception e2) {
			System.out.println("여기냐");
			e2.printStackTrace();
		}
		// sender_socket.send(new DatagramPacket(id.getBytes(), id.length(), sender_addr, 8888));
	}
	
	// 소켓 닫기
	public static void socket_close() {
		try {
			sender_socket.close();
			sender_socket = null;

			mr.stop();
			mr.socket.close();
			mr.socket = null;
		} catch (Exception e) {
			System.out.println("저기냐");
			e.printStackTrace();
		}
	}
}