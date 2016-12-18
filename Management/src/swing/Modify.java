package swing;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import KeepInformation.KeepInformation;
import jdbcDao.JdbcDao;
import jsonReader.MemberReader;

public class Modify extends JFrame {

	private JPanel jp1;
	private JPanel jp2;
	private JPanel jp3;
	private JPanel jp4;
	private JPanel jp5;
	private JPanel jp6;

	private Label jLabel_id;
	private Label jLabel_id1;
	private Label jLabel_password;
	private Label jLabel_age;
	private Label jLabel_name;
	private Label jLabel_phone;

	private TextField TextField_password;
	private TextField TextField_age;
	private TextField TextField_name;
	private TextField TextField_phone;

	private Button ok;
	private Button back;

	KeepInformation test;

	public Modify() {

		test = KeepInformation.getInstance(); // 로그인될때의 값을 가지고있는 객체 생성
		String id = test.getId(); // id의 변수에 로그인된 id의 값을 넣어줌

		setTitle("My information Modify");
		setSize(350, 210);
		setLocation(150, 50);
		setVisible(true);
		setLayout(new GridLayout(6, 1)); // GridLayout을 사용했다. 6행 1열
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		jp5 = new JPanel();
		jp6 = new JPanel();

		jLabel_id = new Label("        ID       :", Label.RIGHT);
		jLabel_id1 = new Label("     " + id + "     ", Label.RIGHT);
		jp1.setLayout(new FlowLayout());
		jp1.add(jLabel_id);
		jp1.add(jLabel_id1);

		jLabel_password = new Label("Password :", Label.RIGHT);
		TextField_password = new TextField(10);
		TextField_password.setEchoChar('*'); // 입력한 값을 *로 보이게 한다
		jp2.add(jLabel_password);
		jp2.add(TextField_password);

		jLabel_age = new Label("       Age      :", Label.RIGHT);
		TextField_age = new TextField(10);
		jp3.add(jLabel_age);
		jp3.add(TextField_age);

		jLabel_name = new Label("      Name   :", Label.RIGHT);
		TextField_name = new TextField(10);
		jp4.add(jLabel_name);
		jp4.add(TextField_name);

		jLabel_phone = new Label("      Phone  :", Label.RIGHT);
		TextField_phone = new TextField(10);
		jp5.add(jLabel_phone);
		jp5.add(TextField_phone);

		ok = new Button(" OK ");
		back = new Button(" Back ");
		jp6.add(ok);
		jp6.add(back);

		add(jp1);
		add(jp2);
		add(jp3);
		add(jp4);
		add(jp5);
		add(jp6);

		TextField_password.addActionListener(new EventHandler());
		TextField_age.addActionListener(new EventHandler());
		TextField_name.addActionListener(new EventHandler());
		TextField_phone.addActionListener(new EventHandler());
		ok.addActionListener(new EventHandler());
		back.addActionListener(new EventHandler());

	}

	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == TextField_password || e.getSource() == TextField_name
					|| e.getSource() == TextField_phone || e.getSource() == TextField_age || e.getSource() == ok) {

				String password = TextField_password.getText();
				String name = TextField_name.getText();
				String phone = TextField_phone.getText();
				String age = TextField_age.getText();

				JdbcDao jdbcDao;
				String sql = null;

				MemberReader data = new MemberReader();
				// 기존에 아이디가 있을경우 체크해서 안되게 해야됨.

				if (!password.isEmpty() && !age.isEmpty() && !name.isEmpty() && !phone.isEmpty()) { //모든 텍스트필드가 비어있지 않을 경우
					for (int i = 0; i < data.data().size(); i++) {

						if (data.data().get(i).get("id").equals(test.getId())) { // 무조건 실행됨												

							try {
								jdbcDao = new JdbcDao();
								sql = String.format("update member.member_table set member_table.age = '" + age
										+ "', member_table.phone ='" + phone + "', member_table.name = '" + name
										+ "', member_table.password = '" + password + "' where member_table.id='"
										+ test.getId() + "'");
								jdbcDao.updateDao(sql); // 수정쿼리문을 db에 보내준다

								test.setPassword(password);// 기존에 로그인 되어있는 비밀번호변경

								new ChoiceModifyOrMoviePoster(); //화면  전환
								dispose();//기존의 창을 닫아준다
								break;//break;
							} catch (SQLException e1) {
								e1.printStackTrace();
							}

						} else if (i < data.data().size() - 1) { // 마지막인덱스 까지 체크
							//System.out.println("continue");
							continue;
						}
					}
				} else {//텍스트필드가 하나라도 비어있는경우
					JOptionPane.showMessageDialog(null, "비어있는 칸이있습니다."); //메세지 창띄어줌 
					TextField_password.setText(""); //초기화
					TextField_name.setText("");
					TextField_phone.setText("");
					TextField_age.setText("");
					TextField_password.requestFocus(); // 커서를 TextField_password로 가져간다

				}
			}

			else if (e.getSource() == back) {
				new ChoiceModifyOrMoviePoster();
				dispose();
			}

		}

	}

}
