package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.DatagramPacket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.NoteDAO;
import dto.NoteDTO;
import net.UDPSocketNet;

public class NoteWriteFrame extends JFrame {
   NoteDTO ndto;
   NoteDAO ndao;
   String subject;
   String contents;
   static JTextField nametf;
   private JButton name_search;

   public NoteWriteFrame(String id, String rid, UDPSocketNet usk) {
      super("그린 도서관 쪽지 작성");
      System.out.println("생성자 호출");
      getContentPane().setLayout(null);
      setBounds(0, 0, 486, 410);// 0416
      setBackground(Color.pink);
      JPanel namePan = new JPanel();
      namePan.setBackground(new Color(172, 136, 102));
      namePan.setLayout(null);
      namePan.setBounds(0, 0, 485, 30);// 0416
      requestFocus();
      nametf = new JTextField();
      nametf.setBorder(null);
      nametf.setForeground(new Color(255, 255, 255));
      nametf.setBackground(new Color(172, 136, 102));
      nametf.setFont(new Font("맑은 고딕", Font.BOLD, 13));
      nametf.setBounds(82, 0, 323, 30);// 0416
      
      name_search = new JButton("목록");
      name_search.setFont(new Font("맑은 고딕", Font.BOLD, 15));// 0416
      name_search.setBackground(new Color(120, 81, 48));
      name_search.setForeground(Color.WHITE);
      name_search.setBounds(406, 0, 67, 30);
      name_search.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            new SearchMember(id);
         }
      });
      name_search.setVisible(false);
      namePan.add(name_search);
      
      if (rid != null) {
         nametf.setText(rid);
         nametf.setFocusable(false);
      } else {
         nametf.setText("");
         name_search.setVisible(true);
         nametf.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
               if (nametf.getText().equals("")) {
                  nametf.setText("받는사람을 작성하세요");
               }
            }

            @Override
            public void focusGained(FocusEvent e) {
               // TODO Auto-generated method stub
               if (nametf.getText().equals("받는사람을 작성하세요")) {
                  nametf.setText("");
               }
            }
         });
      }

      namePan.add(nametf);

      JPanel titlePan = new JPanel();
      titlePan.setBackground(new Color(241, 230, 208));
      titlePan.setLayout(null);
      titlePan.setBounds(0, 30, 485, 30);// 0416

      JPanel conPan = new JPanel();
      conPan.setLayout(null);
      conPan.setBounds(0, 60, 485, 200);// 0416

      JPanel btnPan = new JPanel();
      btnPan.setLayout(null);
      btnPan.setBounds(0, 260, 485, 410);// 0416
      btnPan.setBackground(new Color(241, 230, 208));

      JLabel namelb = new JLabel("받는 사람: ");
      namelb.setForeground(Color.WHITE);
      namelb.setBounds(0, 0, 82, 30);
      namePan.add(namelb);
      namelb.setBackground(new Color(255, 255, 255));
      namelb.setFont(new Font("맑은 고딕", Font.BOLD, 15));
      getContentPane().add(titlePan);

      JLabel titlelb = new JLabel("제목: ");
      titlelb.setFont(new Font("맑은 고딕", Font.BOLD, 15));
      titlelb.setBounds(0, 0, 40, 30);
      titlePan.add(titlelb);
      getContentPane().add(conPan);
      getContentPane().add(btnPan);
      setVisible(true);
      setLocationRelativeTo(null);
      setResizable(false);
      
      JButton send_btn = new JButton("보내기");
      send_btn.setForeground(new Color(255, 255, 255));
      send_btn.setBackground(new Color(120, 81, 48));// 0416
      send_btn.setFont(new Font("맑은 고딕", Font.BOLD, 20));// 0416
      send_btn.setLayout(null);
      send_btn.setBounds(100, 28, 115, 45);// 0416

      JButton exit_btn = new JButton("닫기");
      exit_btn.setForeground(new Color(255, 255, 255));
      exit_btn.setBackground(new Color(120, 81, 48));// 0416
      exit_btn.setFont(new Font("맑은 고딕", Font.BOLD, 20));// 0416
      exit_btn.setLayout(null);
      exit_btn.setBounds(270, 28, 115, 45);// 0416
      exit_btn.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
      });
      btnPan.add(send_btn);
      btnPan.add(exit_btn);

      JTextField title = new JTextField();
      title.setBorder(null);
      title.setBackground(new Color(241, 230, 208));
      title.setFont(new Font("맑은 고딕", Font.BOLD, 13));

      title.addFocusListener(new FocusListener() {

         @Override
         public void focusLost(FocusEvent e) {
            if (title.getText().equals("")) {
               title.setText("제목이 비어있습니다.");
            }
         }

         @Override
         public void focusGained(FocusEvent e) {
            // TODO Auto-generated method stub
            if (title.getText().equals("제목이 비어있습니다.")) {
               title.setText("");
            }
         }
      });
      title.requestFocus(false);
      title.setOpaque(true);
      title.setBounds(46, 0, 427, 30);// 0416
      titlePan.add(title);
      JTextArea con = new JTextArea();
      con.setBorder(null);
      con.setForeground(new Color(255, 255, 255));
      con.setBackground(new Color(172, 136, 102));
      con.setFont(new Font("맑은 고딕", Font.BOLD, 14));
      con.setLayout(null);
      con.setBounds(0, 0, 475, 200);// 0416
      con.setLineWrap(true);

      con.addFocusListener(new FocusListener() {

         @Override
         public void focusLost(FocusEvent e) {
            // TODO Auto-generated method stub
            if (con.getText().equals("")) {
               con.setText("내용이 비어있습니다.");
            }
         }

         @Override
         public void focusGained(FocusEvent e) {
            if (con.getText().equals("내용이 비어있습니다.")) {
               con.setText("");
            }

         }
      });

      JScrollPane sp = new JScrollPane(con);
      sp.setOpaque(true);
      sp.setBounds(0, 0, 475, 200);// 0416
      conPan.add(sp);

      send_btn.addActionListener(new ActionListener() {
         int res = 0;

         @Override
         public void actionPerformed(ActionEvent e) {
            subject = title.getText();
            contents = con.getText();
            String rid = nametf.getText();
            if (!((subject.equals("") || subject.equals("제목")) && (contents.equals("") || contents.equals("내용"))
                  && (rid.equals("") || rid.equals("받는사람")))) {
               ndto = new NoteDTO();
               ndto.setN_sid(id);
               ndto.setN_rid(rid);
               ndto.setN_subject(subject);
               ndto.setN_contents(contents);
               ndao = new NoteDAO();
               res = ndao.note_write(ndto);
               if (res == 0) {
                  JOptionPane.showMessageDialog(null, "그린도서관에 등록된 회원이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE);
               } else if(title.getText().equals("")) {
                  JOptionPane.showMessageDialog(null, "제목을 입력해주세요.", "전송실패", JOptionPane.ERROR_MESSAGE);
               } else if(con.getText().equals("")) {
                  JOptionPane.showMessageDialog(null, "내용을 입력해주세요.", "전송실패", JOptionPane.ERROR_MESSAGE);
               } else if (res != 0) {
                  try {
                     usk.sender_socket
                           .send(new DatagramPacket(rid.getBytes(), rid.length(), usk.sender_addr, 8888));

                  } catch (IOException e1) {
                     e1.printStackTrace();
                     
                  }
                  JOptionPane.showMessageDialog(null, "쪽지보내기 완료", "전송완료", JOptionPane.PLAIN_MESSAGE);
                  dispose();
               }
            } 
         }
      });
      getContentPane().add(namePan);
   }
}