package swing;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jdbcDao.JdbcDao;
import jsonReader.MemberReader;

public class CreateNewMember extends JFrame {
	private JPanel jp1;
	private JPanel jp2;
	private JPanel jp3;
	private JPanel jp4;
	private JPanel jp5;
	private JPanel jp6;

	private Label lid;
	private Label lpswd;
	private Label lage;
	private Label lname;
	private Label lphone;

	private TextField tid;
	private TextField tpswd;
	private TextField tage;
	private TextField tname;
	private TextField tphone;

	private Button ok;

	public CreateNewMember() {

		setTitle("CreateNewMember");
		setSize(250, 300);
		setLocation(150, 50);
		setVisible(true);
		setLayout(new GridLayout(6, 1));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		jp5 = new JPanel();
		jp6 = new JPanel();

		lid = new Label("        ID       :", Label.RIGHT);
		tid = new TextField(10);
		jp1.add(lid);
		jp1.add(tid);

		lpswd = new Label("Password :", Label.RIGHT);
		tpswd = new TextField(10);
		jp2.add(lpswd);
		jp2.add(tpswd);
		tpswd.setEchoChar('*'); // 입력한 값을 *로 보이게 한다

		lage = new Label("       Age      :", Label.RIGHT);
		tage = new TextField(10);
		jp3.add(lage);
		jp3.add(tage);

		lname = new Label("      Name   :", Label.RIGHT);
		tname = new TextField(10);
		jp4.add(lname);
		jp4.add(tname);

		lphone = new Label("      Phone  :", Label.RIGHT);
		tphone = new TextField(10);
		jp5.add(lphone);
		jp5.add(tphone);

		ok = new Button("OK");
		jp6.add(ok);

		add(jp1);
		add(jp2);
		add(jp3);
		add(jp4);
		add(jp5);
		add(jp6);

		tid.addActionListener(new EventHandler());
		tpswd.addActionListener(new EventHandler());
		tage.addActionListener(new EventHandler());
		tname.addActionListener(new EventHandler());
		tphone.addActionListener(new EventHandler());

		ok.addActionListener(new EventHandler());

	}

	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String id = tid.getText();
			String password = tpswd.getText();
			String age = tid.getText();
			String name = tname.getText();
			String phone = tphone.getText();

			JdbcDao jdbcDao;
			MemberReader memberReader;

			if (!id.isEmpty() && !password.isEmpty() && !age.isEmpty() && !name.isEmpty() && !phone.isEmpty()) {
				try {

					jdbcDao = new JdbcDao();
					memberReader = new MemberReader();

					String sql = null; //쿼리문을 담을 변수

					int count = 0;

					for (int i = 0; i < memberReader.data().size(); i++) { 
						//아이디중복체크 기존에 아이디가 있을 경우 count1에 1을 넣어준다
						if ((memberReader.data().get(i).get("id")).equals(id)) { 
							count++;
							if(count>=1)
							{
								break;
							}
						}
					}

					if (count == 1) {//count가 1일경우 
						JOptionPane.showMessageDialog(null, "기존에 등록된 아이디가 있습니다");
						tid.setText(""); // tid 초기화
						tid.requestFocus();

					} else {//count가 1이 아닐경우
						sql = String.format( // 쿼리문
								"insert into member.member_table (id, age, name, password, phone) values ('%s', '%s', '%s', '%s', '%s')",
								id, age, name, password, phone);

						jdbcDao.insertDao(sql); //jdbcDao의 메소드의 매개변수로 sql문을 보내준다. database에 쿼리문이 실행된다.
						new Login(); // Login 화면으로 넘어간다
						dispose(); // 기존의 창은 사라진다
					}

				} catch (SQLException e1) {
					e1.printStackTrace();

				}
			}

			else {
				JOptionPane.showMessageDialog(null, "비어있는 칸이있습니다");
			}

		}
	}
}