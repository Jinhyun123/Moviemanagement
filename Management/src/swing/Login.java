package swing;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import KeepInformation.KeepInformation;
import jsonReader.MemberReader;

public class Login extends JFrame {
	
	private Label lid;
	private Label lpswd;
	private TextField tid;
	private TextField tpswd;
	private Button newmember;
	private Button ok;
	
	public Login() {

		setTitle("Login");
		setSize(380, 100);
		setLocation(150, 50);
		setVisible(true);
		setLayout(new FlowLayout()); // frame의 layout을 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		lid = new Label("ID    :", Label.RIGHT); 
		tid = new TextField(10);

		lpswd = new Label("Password :", Label.RIGHT);
		tpswd = new TextField(10);
		tpswd.setEchoChar('*'); // 입력한 값을 *로 보이게 한다

		newmember = new Button("New member");
		ok = new Button(" OK ");
		
		tid.addActionListener(new EventHandler());
		tpswd.addActionListener(new EventHandler());
		ok.addActionListener(new EventHandler());
		newmember.addActionListener(new EventHandler());

		add(lid); //frame에 넣어준다
		add(tid);
		add(lpswd); 
		add(tpswd);
		add(ok);
		add(newmember);
	}

	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == newmember){// newmember 버튼을 눌렀을때 
				new CreateNewMember();// CreateNewMember 클래스로 넘어간다.
				dispose(); // 기존의  창을 닫아준다.
			}
			else if(e.getSource() == ok || e.getSource() == tid  || e.getSource() == tpswd){ //ok,tid,tpswd 중 하나라도 발생한다면
				String id = tid.getText(); //tid의 내용을 id변수에 담아준다
				String password = tpswd.getText(); //tpswd의 내용을 password변수에 담아준다

				MemberReader data = new MemberReader(); //db -> jsp -> java 로 넘어온 member_table의 값을 가져오기위해서 객체생성
				
				if (!id.isEmpty() && !password.isEmpty() && !data.data().isEmpty()){  //id 가 비어있지않거나 password 가 비어있지않거나   data list에 값이 비어있지않을때 

					for (int i = 0; i < data.data().size(); i++) {
						if (data.data().get(i).get("id").equals(id)
								&& data.data().get(i).get("password").equals(password)){ //id가  data에 있는 것과 같고   password가 data에 있는 것과 같을때 실행 
							
							KeepInformation information = KeepInformation.getInstance();
							information.setId((data.data().get(i).get("id"))); // singleton pattern
							information.setPassword((data.data().get(i).get("password")));// singleton pattern
							//System.out.println("macth");
							new ChoiceModifyOrMoviePoster();
							dispose();
							break;
						} else if (i < data.data().size() - 1) { // 마지막인덱스 까지 체크
							//System.out.println("continue");
							continue;
						} else {
							//System.out.println("dismatch");
							JOptionPane.showMessageDialog(null, "존재하지 않는 회원입니다."); //메세지창을 띄어준다
							tid.setText(""); // 초기화
							tpswd.setText("");
							tid.requestFocus();// 커서를 tid에 가져간다
						}
					}
				} else {
					System.out.println("dismatch");
					JOptionPane.showMessageDialog(null, "다시입력해주세요.");
					tid.setText("");
					tpswd.setText("");
					tid.requestFocus();// 커서를 tid에 가져간다
				}
			}

		}
	}
}
