package swing;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import KeepInformation.KeepInformation;
import jdbcDao.JdbcDao;

public class ExitMember extends JFrame {

	private Label Label_id;
	private Label Label_password;
	private TextField textField_id;
	private TextField textField_password;
	private Button ok;
	private Button back;

	public ExitMember() {
		setTitle("ExitMember");
		setSize(380, 100);
		setLocation(150, 50);
		setVisible(true);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Label_id = new Label("ID    :", Label.RIGHT);
		textField_id = new TextField(10);

		Label_password = new Label("Password :", Label.RIGHT);
		textField_password = new TextField(10);
		textField_password.setEchoChar('*'); // 입력한 값을 *로 보이게 한다

		ok = new Button(" OK ");
		back = new Button(" Back ");

		textField_id.addActionListener(new EventHandler());
		textField_password.addActionListener(new EventHandler());
		ok.addActionListener(new EventHandler());
		back.addActionListener(new EventHandler());

		add(Label_id);
		add(textField_id);
		add(Label_password);
		add(textField_password);
		add(ok);
		add(back);
	}

	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == textField_id || e.getSource() == textField_password || e.getSource() == ok) {
				String id = textField_id.getText();
				String password = textField_password.getText();

				JdbcDao jdbcDao;
				String sql = null;

				KeepInformation information = KeepInformation.getInstance();
				if (!id.isEmpty() && !password.isEmpty()) {
					
					if (information.getId().equals(id) && information.getPassword().equals(password)) {
						// 로그인된 id와 textField_id와 같고 password와 textField_password 와 일치 할때
						try {
							jdbcDao = new JdbcDao();
							sql = String.format("delete from member.member_table where id = '" + id + "'");
							jdbcDao.deletetDao(sql);

							new ExitComplete(); // ExitComplete 클래스의 생성자 실행 
							dispose(); // 기존의 창은 닫는다
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						//System.out.println("macth");
					} else {
						// 로그인된 id와 textField_id와 같고 password와 textField_password 와 일치 하지않을때
						JOptionPane.showMessageDialog(null, "ID 또는 비밀번호가 일치하지 않습니다.");
						textField_id.setText("");
						textField_password.setText("");
						textField_id.requestFocus();
					}
				} else {
					//textField가 비어있을때
					//System.out.println("dismatch");
					JOptionPane.showMessageDialog(null, "비어있는 칸이있습니다.");
					textField_id.setText("");
					textField_password.setText("");
					textField_id.requestFocus();
				}
			} else if (e.getSource() == back) {//back 버튼을 눌렀을때
				new ChoiceModifyOrMoviePoster(); // 생성자실행
				dispose();

			}
		}
	}
}
