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

public class BookEnroll extends JFrame {

	private JPanel contentPane;
	private JPanel panel;

	private JLabel genre;
	private JLabel bookname;
	private JLabel author;
	private JLabel publisher;
	private JLabel lblNewLabel_2;

	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	private static BookEnroll frame;
	private static int x, y;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Toolkit
	 * tk=Toolkit.getDefaultToolkit(); Dimension screenSize=tk.getScreenSize();
	 * frame = new BookEnroll(); frame.setSize(267,345);
	 * frame.setLocation((screenSize.width-frame.getWidth())/2,(screenSize.height-
	 * frame.getHeight())/2); x=screenSize.width-frame.getWidth()/2;
	 * y=screenSize.height-frame.getHeight()/2; frame.setVisible(true); } catch
	 * (Exception e) { e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the frame.
	 */
	public BookEnroll() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(241, 230, 208));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		genre = new JLabel("분류");
		genre.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		genre.setBounds(64, 86, 57, 45);
		contentPane.add(genre);

		bookname = new JLabel("도서명");
		bookname.setBounds(64, 154, 94, 45);
		bookname.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		contentPane.add(bookname);

		textField_2 = new JTextField();
		textField_2.setBounds(201, 160, 151, 33);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		author = new JLabel("저자명");
		author.setBounds(64, 223, 90, 45);
		author.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		contentPane.add(author);

		textField_3 = new JTextField();
		textField_3.setBounds(201, 229, 151, 33);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		publisher = new JLabel("출판사명");
		publisher.setBounds(64, 292, 106, 45);
		publisher.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		contentPane.add(publisher);

		textField_4 = new JTextField();
		textField_4.setBounds(201, 298, 151, 33);
		contentPane.add(textField_4);
		textField_4.setColumns(10);

		JButton btnNewButton = new JButton("등록하기");
		btnNewButton.addActionListener(new EventHandler());
		btnNewButton.setBackground(new Color(172, 136, 102));
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnNewButton.setForeground(new Color(241, 230, 208));
		btnNewButton.setBounds(39, 386, 119, 45);
		contentPane.add(btnNewButton);

		JButton button = new JButton("취소하기");
		button.setBackground(new Color(172, 136, 102));
		button.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		button.setForeground(new Color(241, 230, 208));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setBounds(233, 386, 119, 45);
		contentPane.add(button);

		panel = new JPanel();
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panel.setBounds(0, 0, 400
				, 60);
		
		panel.setBackground(new Color(120, 81, 48));
		
		contentPane.add(panel);
		panel.setLayout(null);

		lblNewLabel_2 = new JLabel();
		lblNewLabel_2.setIcon(new ImageIcon(BookEnroll.class.getResource("/img/img52.png")));
		lblNewLabel_2.setForeground(new Color(241, 230, 208));
		lblNewLabel_2.setFont(new Font("맑은 고딕",Font.BOLD,20));
		lblNewLabel_2.setBounds(120, 0, 163, 60);
		panel.add(lblNewLabel_2);

		comboBox = new JComboBox();
		comboBox.setBackground(new Color(172, 136, 102));
		comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		comboBox.setForeground(Color.white);
		comboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "컴퓨터", "철 학", "종 교", "과 학", "예 술", "언 어", "문 학", "역 사" }));

		comboBox.setBounds(201, 93, 151, 33);
		contentPane.add(comboBox);
		
		
	}

	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String bookname = textField_2.getText();
			String author = textField_3.getText();
			String publisher = textField_4.getText();
			if (bookname.equals("")) {
				final Dialog booknameInfo = new Dialog(frame, "Information", true);
				booknameInfo.setSize(200, 100);
				booknameInfo.setLocationRelativeTo(null);
				// booknameInfo.setLocation((x - booknameInfo.getWidth()) / 2 + 70,
				// 		(y - booknameInfo.getHeight()) / 2 + 70);
				booknameInfo.setLayout(new FlowLayout());
				JLabel msg = new JLabel("도서명을 입력해주세요");
				JButton ok = new JButton("확인");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
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
			//	authorInfo.setLocation((x - authorInfo.getWidth()) / 2 + 70, (y - authorInfo.getHeight()) / 2 + 70);
				authorInfo.setLayout(new FlowLayout());
				JLabel msg = new JLabel("저자를 입력해주세요");
				JButton ok = new JButton("확인");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
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
//				publisherInfo.setLocation((x - publisherInfo.getWidth()) / 2 + 70,
//						(y - publisherInfo.getHeight()) / 2 + 70);
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
				String category = comboBox.getSelectedItem().toString();
				int index = 0;
				int length = 9;
				char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < length; i++) {
					index = (int) (charSet.length * Math.random());
					sb.append(charSet[index]);
				}
				System.out.println(sb.toString());
				dto.setBOOK_CODE(Integer.parseInt(sb.toString()));
				dto.setBOOK_CATEGORY(category);
				dto.setBOOK_NAME(bookname);
				dto.setBOOK_AUTHOR(author);
				dto.setBOOK_COMPANY(publisher);

				bookDAO dao = new bookDAO();
				int data = dao.insertBook(dto);
				System.out.println("입력자료:" + data);

				final Dialog finishInfo = new Dialog(frame, "Information", true);
				finishInfo.setSize(200, 100);
				finishInfo.setLocationRelativeTo(null);
				finishInfo.setLayout(new FlowLayout());
				JLabel msg = new JLabel("책이 등록되었습니다.");
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
				new BookManager();
			}
		}
	}
}