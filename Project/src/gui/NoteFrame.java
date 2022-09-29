package gui;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.EventListenerList;

import dao.NoteDAO;
import dto.NoteDTO;
import net.UDPSocketNet;
import java.awt.Font;

public class NoteFrame extends JFrame {
	public static JPanel tablePan, btnPan;
	JLabel row;
	EventListenerList listenerList = new EventListenerList();
	public String sort, id, rid, subject;
	public static ArrayList<Checkbox> chkList;
	ArrayList<NoteDTO> list;
	Checkbox chkBox;
	NoteDTO ndto;
	NoteDAO ndao;
	public static TablePanel tp;
	public static JScrollPane sp;
	public static UDPSocketNet usk;
	boolean selectAll = false;
	int x = 0, y = 30, tx, ty, index;
	public NoteFrame(String id, UDPSocketNet usk) {
		this.id = id;
		this.usk = usk;
		setSize(486, 410);
		getContentPane().setLayout(null);
		setUndecorated(true);
		ndao = new NoteDAO();
		list = ndao.note_list(id);
		chkList = new ArrayList<Checkbox>();
		tablePan = new JPanel();
		tablePan.setBounds(0, 55, 470, list.size()*40);
		tablePan.setPreferredSize(new Dimension(470, list.size()*40));
		tablePan.setOpaque(true);
		tablePan.setBackground(new Color(253,245,230));
		String[] column = {"전체 선택/해제", "보낸 사람", "제목", "받은 날짜"};
		for(int i = 0; i < column.length; i++) {
			row = new JLabel(column[i]);
			row.setHorizontalAlignment(JLabel.CENTER);
			row.setLayout(null);
			row.setOpaque(true);
			row.setBackground(new Color(120,81,48));
			row.setForeground(Color.white);
			row.setBounds(110+(110*i), 0, 0, 0);
			row.setPreferredSize(new Dimension(110, 30));
			if(i == 0) {
				row.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(selectAll == false) {
							selectAll = true;
							for (Checkbox box : chkList) {
								box.setState(selectAll);
							}
							for(int i = 1; i<e.getComponent().getParent().getComponents().length;i++) {
								if(i%4 == 0) {
									((JLabel)(e.getComponent().getParent().getComponents()[i])).setText("ν");
								}
							}
						}else {
							selectAll = false;
							for (Checkbox box : chkList) {
								box.setState(false);
							}
							for(int i = 1; i<e.getComponent().getParent().getComponents().length;i++) {
								if(i%4 == 0) {
									((JLabel)(e.getComponent().getParent().getComponents()[i])).setText("");
								}
							}
						}
					}
				});
			}
			tablePan.add(row);
		}
		
		ArrayList<String> data;
		for(int i = 0; i < list.size(); i++) {
			ndto = list.get(i);
			chkBox = new Checkbox(ndto.getN_index()+"");
			chkBox.setVisible(false);
			chkBox.setBounds(50, 0, 30, 30);
			data = new ArrayList<String>();
			data.add(ndto.getN_sid());
			data.add(ndto.getN_subject());
			data.add(ndto.getN_dateStr());
			row = new JLabel("");
			row.setHorizontalAlignment(JLabel.CENTER);
			chkList.add(chkBox);
			row.add(chkList.get(i));
			row.setLayout(null);
			row.setOpaque(true);
			row.setBounds(x, y, 0, 0);
			row.setPreferredSize(new Dimension(110, 30));
			row.setBackground(new Color(241,230,208));
			row.setForeground(Color.red);
			row.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					tx = (int) e.getComponent().getLocation().getX();
					ty = (int) e.getComponent().getLocation().getY();
					if(((JLabel) e.getComponent()).getText().equals("")) {
						((JLabel) e.getComponent()).setText("ν");
					}else {
						((JLabel) e.getComponent()).setText("");
					}
					sort = "1";
					fireActionPerformed(
						new ActionEvent(e, ActionEvent.ACTION_PERFORMED, sort)
					);
					
				}
			});
			tablePan.add(row);
			x += 110;
			for(int j = 0; j < data.size(); j++) {
				row = new JLabel();
				row.setHorizontalAlignment(JLabel.CENTER);
				row.setText(data.get(j));
				row.setOpaque(true);
				row.setBackground(new Color(241,230,208));
				if(j == 0) {
					row.setForeground(new Color(139,69,19));
				}
				if(j == 1) {
					row.setForeground(new Color(128,0,0));
				}
				if(j == 2) {
					row.setForeground(new Color(160,82,45));
				}
				if(ndto.getN_sort().equals("Y") && j == 1) {
					row.setForeground(Color.gray);
				}
				row.setBounds(x, y, 0, 0);
				row.setPreferredSize(new Dimension(110, 30));
				row.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						tx = (int) e.getComponent().getLocation().getX();
						ty = (int) e.getComponent().getLocation().getY();
						System.out.println(tx);
						switch (tx) {
						case 130:
							sort = "2";
							rid = ((JLabel) e.getComponent()).getText();
							break;
						case 245:
							e.getComponent().setForeground(Color.gray);
							sort = "3";
							break;
						case 360:
							sort = "4";
							break;
						default:
							break;
						}
						fireActionPerformed(
							new ActionEvent(e, ActionEvent.ACTION_PERFORMED, sort)
						);
					}
				});
				tablePan.add(row);
				x += 110;
				if(x >= 440) {
					x = 0;
					y += 30;
				}
			}
		}
	
		btnPan = new JPanel();
		JButton delBtn = new JButton("삭제");
		delBtn.setForeground(new Color(255, 255, 255));
		delBtn.setBackground(new Color(172, 136, 102));
		delBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		delBtn.setBounds(0, 0, 50, 50);
		delBtn.setLayout(null);
		delBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int cnt = 0;
				int res = 0;
				if(chkList != null) {
					for (int i = 0; i < chkList.size(); i++) {
						if(chkList.get(i).getState() == true) {
							cnt += 1;
							res += ndao.note_del(Integer.parseInt(chkList.get(i).getLabel()));
						}
					}
					MyPagePanel.getNoteCount(id);
					if(cnt == res) {
						sp.remove(tablePan);
						tp = new TablePanel(id, usk);
						chkList = tp.chkList;
						tablePan = tp;
						sp.setViewportView(tablePan);
						sp.revalidate();
					}
				}
			}
		});
		JButton writeBtn = new JButton("쓰기");
		writeBtn.setForeground(new Color(255, 255, 255));
		writeBtn.setBackground(new Color(172, 136, 102));
		writeBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		writeBtn.setBounds(0, 0, 50, 50);
		writeBtn.setLayout(null);
		writeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new NoteWriteFrame(id, null, usk);
			}
		});
		JButton exitBtn = new JButton("닫기");
		exitBtn.setForeground(new Color(255, 255, 255));
		exitBtn.setBackground(new Color(172, 136, 102));
		exitBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		exitBtn.setBounds(0, 0, 50, 50);
		exitBtn.setLayout(null);
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyPagePanel.getNoteCount(id);
				dispose();
			}
		});
		
		btnPan.setBounds(0, 5, 484, 50);
		btnPan.setLayout(new FlowLayout());
		btnPan.setOpaque(true);
		btnPan.setBackground(new Color(253,245,230));
		btnPan.add(writeBtn);
		btnPan.add(delBtn);
		btnPan.add(exitBtn);
		getContentPane().add(btnPan);
		sp = new JScrollPane(22, 31);
		sp.setViewportView(tablePan);
		sp.setBounds(0, 55, 505, 410);
		sp.setOpaque(true);
		sp.setPreferredSize(new Dimension(505, 410));
		sp.getVerticalScrollBar().setUnitIncrement(16);
		sp.getVerticalScrollBar().setOpaque(false);
		sp.setBorder(new LineBorder(new Color(253,245,230)));
		getContentPane().add(sp);
		
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
	}
	
	void fireActionPerformed(ActionEvent e) {
		Object[] listeners = listenerList.getListeners(ActionListener.class);
		for (int i = 0; i < listeners.length; i++) {
			((ActionListener) listeners[i]).actionPerformed(e);
		}
		if(e.getActionCommand().equals("1")) {
			System.out.println((ty-40)/35);
			if(chkList.get((ty-40)/35).getState() == false) {
				chkList.get((ty-40)/35).setState(true);
			}else {
				chkList.get((ty-40)/35).setState(false);
			}
		}
		if(e.getActionCommand().equals("2")) {
			new NoteWriteFrame(id, rid, usk);
		}
		if(e.getActionCommand().equals("3")) {
			ndao = new NoteDAO();
			index = Integer.parseInt(chkList.get((ty-40)/35).getLabel());
			ndto = ndao.note_read(index);
			new NoteReadFrame(ndto, usk);
			MyPagePanel.getNoteCount(id);
		}
	}
}