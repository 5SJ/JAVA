package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import dao.NoteDAO;
import dto.NoteDTO;
import net.UDPSocketNet;

public class TablePanel extends JPanel{
	JPanel btnPan;
	JLabel row;
	EventListenerList listenerList = new EventListenerList();
	public ArrayList<Checkbox> chkList;
	ArrayList<NoteDTO> list;
	Checkbox chkBox;
	String sort, id, rid, subject;
	NoteDTO ndto;
	NoteDAO ndao;
	UDPSocketNet usk;
	boolean selectAll = false;
	int x = 0, y = 30, tx, ty, index;
	public TablePanel(String id, UDPSocketNet usk) {
		this.id = id;
		this.usk = usk;
		ndao = new NoteDAO();
		list = ndao.note_list(id);
		chkList = new ArrayList<Checkbox>();
		setBounds(0, 55, 470, (list.size()*36)+25);
		setPreferredSize(new Dimension(470, (list.size()*36)+25));
		setOpaque(true);
		setBackground(new Color(253,245,230));
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
			add(row);
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
			add(row);
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
				add(row);
				x += 110;
				if(x >= 440) {
					x = 0;
					y += 30;
				}
			}
		}
	}
	void fireActionPerformed(ActionEvent e) {
		System.out.println("이벤트");
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
		}
	}
}
