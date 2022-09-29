package gui;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import dao.bookDAO;
import dto.bookDTO;

public class BookModify extends JFrame {

	private JPanel contentPane;
	private JLabel label_1;
	private JLabel bookname;
	private JTextField textField_2;
	private JLabel author;
	private JTextField textField_3;
	private JLabel publisher;
	private JTextField textField_4;
	private JButton ModifyButton;
	private JButton DeleteButton;
	private JButton button_1;
	private JPanel panel;
	private JLabel lblNewLabel_2;
	private static BookEnroll frame;
	private static int x, y;
	JComboBox<String> comboBox;
	int code;

	public BookModify(String[] modifyresult) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(241,230,208));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		label_1 = new JLabel("분류");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		label_1.setBounds(64, 86, 57, 45);
		contentPane.add(label_1);

		bookname = new JLabel("도서명");
		bookname.setBounds(64, 154, 94, 45);
		bookname.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		contentPane.add(bookname);
		textField_2 = new JTextField("");
		textField_2.setBounds(201, 160, 151, 33);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		author = new JLabel("저자명");
		author.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		author.setBounds(64, 223, 90, 45);
		contentPane.add(author);

		textField_3 = new JTextField("");

		textField_3.setBounds(201, 229, 151, 33);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		publisher = new JLabel("출판사명");
		publisher.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		publisher.setBounds(64, 292, 106, 45);
		contentPane.add(publisher);

		textField_4 = new JTextField();
		textField_4.setBounds(201, 298, 151, 33);
		contentPane.add(textField_4);
		textField_4.setColumns(10);

		ModifyButton = new JButton("수정");
		ModifyButton.addActionListener(new EventHandler());
		ModifyButton.setActionCommand("modify");
		ModifyButton.setBackground(new Color(172, 136, 102));
		ModifyButton.setBounds(26, 398, 82, 45);
		ModifyButton.setForeground(new Color(241, 230, 208));
		ModifyButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		contentPane.add(ModifyButton);

		DeleteButton = new JButton("삭제");
		DeleteButton.addActionListener(new EventHandler());
		DeleteButton.setForeground(new Color(241, 230, 208));
		DeleteButton.setActionCommand("del");
		DeleteButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		DeleteButton.setBackground(new Color(172, 136, 102));
		DeleteButton.setBounds(278, 398, 82, 45);
		contentPane.add(DeleteButton);

		button_1 = new JButton("취소");
		button_1.setForeground(new Color(241, 230, 208));
		button_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		button_1.setBackground(new Color(172, 136, 102));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_1.setBounds(147, 398, 94, 45);
		contentPane.add(button_1);

		panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel.setBackground(new Color(120, 81, 48));
		panel.setBounds(0, 0, 400, 60);
		contentPane.add(panel);
		panel.setLayout(null);

		lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setIcon(new ImageIcon(BookModify.class.getResource("/img/img53.png")));
		lblNewLabel_2.setBounds(120, 0, 163, 60);
		panel.add(lblNewLabel_2);

		comboBox = new JComboBox<String>();
		comboBox.setForeground(Color.white);
		comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		comboBox.setBackground(new Color(172, 136, 102));
		comboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "컴퓨터", "철 학", "종 교", "과 학", "예 술", "언 어", "문 학", "역 사" })
				);
		comboBox.setBounds(201, 93, 151, 33);
		contentPane.add(comboBox);
		this.code = Integer.parseInt(modifyresult[0]);
		comboBox.setSelectedItem(modifyresult[1]);
		textField_2.setText(modifyresult[2]);
		textField_3.setText(modifyresult[3]);
		textField_4.setText(modifyresult[4]);
	}

	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String category = (String) comboBox.getSelectedItem();
			String bookname = textField_2.getText();
			String author = textField_3.getText();
			String publisher = textField_4.getText();
			if (e.getActionCommand().equals("del")) {
				if (bookname.equals("")) {
					final Dialog booknameInfo = new Dialog(frame, "Information", true);
					booknameInfo.setSize(200, 100);
					booknameInfo.setLocationRelativeTo(null);
//					booknameInfo.setLocation((x - booknameInfo.getWidth()) / 2 + 70,
//							(y - booknameInfo.getHeight()) / 2 + 70);
					booknameInfo.setLayout(new FlowLayout());
					JLabel msg = new JLabel("도서명을 입력해주세요");
					JButton ok = new JButton("확인");
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							booknameInfo.setVisible(false);
							booknameInfo.dispose();
						}
					});
					booknameInfo.add(msg);
					booknameInfo.add(ok);
					booknameInfo.setVisible(true);
					textField_2.requestFocus();
					textField_2.selectAll();
				} else if (author.equals("")) {
					final Dialog authorInfo = new Dialog(frame, "Information", true);
					authorInfo.setSize(200, 100);
					authorInfo.setLocationRelativeTo(null);
//					authorInfo.setLocation((x - authorInfo.getWidth()) / 2 + 70, (y - authorInfo.getHeight()) / 2 + 70);
//					authorInfo.setLayout(new FlowLayout());
					JLabel msg = new JLabel("저자를 입력해주세요");
					JButton ok = new JButton("확인");
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							authorInfo.setVisible(false);
							authorInfo.dispose();
						}
					});
					authorInfo.add(msg);
					authorInfo.add(ok);
					authorInfo.setVisible(true);
					textField_3.requestFocus();
					textField_3.selectAll();
				} else if (publisher.equals("")) {
					final Dialog publisherInfo = new Dialog(frame, "Information", true);
					publisherInfo.setSize(200, 100);
					publisherInfo.setLocationRelativeTo(null);
//					publisherInfo.setLocation((x - publisherInfo.getWidth()) / 2 + 70,
//							(y - publisherInfo.getHeight()) / 2 + 70);
					publisherInfo.setLayout(new FlowLayout());
					JLabel msg = new JLabel("출판사를 입력해주세요");
					JButton ok = new JButton("확인");
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							publisherInfo.setVisible(false);
							publisherInfo.dispose();
						}
					});
					publisherInfo.add(msg);
					publisherInfo.add(ok);
					publisherInfo.setVisible(true);
					textField_4.requestFocus();
					textField_4.selectAll();
				} else {
					bookDTO dto = new bookDTO();
					bookDAO dao = new bookDAO();
					dto.setBOOK_CODE(code);
					dto.setBOOK_NAME(textField_2.getText());
					int data = dao.deleteBook(dto);
					System.out.println("입력자료:" + data);
					if(data == 1) {
						final Dialog finishInfo = new Dialog(frame, "Information", true);
						finishInfo.setSize(200, 100);
						finishInfo.setLocationRelativeTo(null);
						finishInfo.setLayout(new FlowLayout());
						JLabel msg = new JLabel("책이 삭제되었습니다.");
						JButton ok = new JButton("확인");
						ok.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								finishInfo.setVisible(false);
								finishInfo.dispose();
							}
						});
						finishInfo.add(msg);
						finishInfo.add(ok);
						finishInfo.setVisible(true);
						dispose();
						ManagerMain.panel.remove(ManagerMain.bm);
						ManagerMain.bm = new BookManager();
						ManagerMain.panel.add(ManagerMain.bm, "3");
						ManagerMain.panel.setVisible(true);
						ManagerMain.card.show(ManagerMain.panel,"3");
					}else {
						final Dialog finishInfo = new Dialog(frame, "Information", true);
						finishInfo.setSize(200, 100);
						finishInfo.setLocationRelativeTo(null);
						finishInfo.setLayout(new FlowLayout());
						JLabel msg = new JLabel("책이 대출중입니다.");
						JButton ok = new JButton("확인");
						ok.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								finishInfo.setVisible(false);
								finishInfo.dispose();
							}
						});
						finishInfo.add(msg);
						finishInfo.add(ok);
						finishInfo.setVisible(true);
						dispose();
					}
				}
			}else if(e.getActionCommand().equals("modify")){
				if (bookname.equals("")) {
					final Dialog booknameInfo = new Dialog(frame, "Information", true);
					booknameInfo.setSize(200, 100);
					booknameInfo.setLocationRelativeTo(null);
//					booknameInfo.setLocation((x - booknameInfo.getWidth()) / 2 + 70,
//							(y - booknameInfo.getHeight()) / 2 + 70);
					booknameInfo.setLayout(new FlowLayout());
					JLabel msg = new JLabel("도서명을 입력해주세요");
					JButton ok = new JButton("확인");
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							booknameInfo.setVisible(false);
							booknameInfo.dispose();
						}
					});
					booknameInfo.add(msg);
					booknameInfo.add(ok);
					booknameInfo.setVisible(true);
					textField_2.requestFocus();
					textField_2.selectAll();
				} else if (author.equals("")) {
					final Dialog authorInfo = new Dialog(frame, "Information", true);
					authorInfo.setSize(200, 100);
					authorInfo.setLocationRelativeTo(null);
//					authorInfo.setLocation((x - authorInfo.getWidth()) / 2 + 70, (y - authorInfo.getHeight()) / 2 + 70);
					authorInfo.setLayout(new FlowLayout());
					JLabel msg = new JLabel("저자를 입력해주세요");
					JButton ok = new JButton("확인");
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							authorInfo.setVisible(false);
							authorInfo.dispose();
						}
					});
					authorInfo.add(msg);
					authorInfo.add(ok);
					authorInfo.setVisible(true);
					textField_3.requestFocus();
					textField_3.selectAll();
				} else if (publisher.equals("")) {
					final Dialog publisherInfo = new Dialog(frame, "Information", true);
					publisherInfo.setSize(200, 100);
					publisherInfo.setLocationRelativeTo(null);
//					publisherInfo.setLocation((x - publisherInfo.getWidth()) / 2 + 70,
//							(y - publisherInfo.getHeight()) / 2 + 70);
					publisherInfo.setLayout(new FlowLayout());
					JLabel msg = new JLabel("출판사를 입력해주세요");
					JButton ok = new JButton("확인");
					ok.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							publisherInfo.setVisible(false);
							publisherInfo.dispose();
						}
					});
					publisherInfo.add(msg);
					publisherInfo.add(ok);
					publisherInfo.setVisible(true);
					textField_4.requestFocus();
					textField_4.selectAll();
				} else {
					bookDTO dto = new bookDTO();
					bookDAO dao = new bookDAO();
					dto.setBOOK_CODE(code);
					dto.setBOOK_NAME(bookname);
					dto.setBOOK_AUTHOR(author);
					dto.setBOOK_CATEGORY(category);
					dto.setBOOK_COMPANY(publisher);
					System.out.println(code+", "+bookname+""+author+", "+category+", "+publisher);
					int data = dao.updateBook(dto);
					System.out.println("입력자료:" + data);
					if(data != 0) {
						final Dialog finishInfo = new Dialog(frame, "Information", true);
						finishInfo.setSize(200, 100);
						finishInfo.setLocationRelativeTo(null);
						
						finishInfo.setLayout(new FlowLayout());
						JLabel msg = new JLabel("책이 수정되었습니다.");
						JButton ok = new JButton("확인");
						ok.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								finishInfo.setVisible(false);
								finishInfo.dispose();
							}
						});
						finishInfo.add(msg);
						finishInfo.add(ok);
						finishInfo.setVisible(true);
						dispose();
						ManagerMain.panel.remove(ManagerMain.bm);
						ManagerMain.bm = new BookManager();
						ManagerMain.panel.add(ManagerMain.bm, "3");
						ManagerMain.panel.setVisible(true);
						ManagerMain.card.show(ManagerMain.panel,"3");
					}
				}
			}
		}
	}
}