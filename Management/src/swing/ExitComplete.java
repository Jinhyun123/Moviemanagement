package swing;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

//탈퇴가 되었을 때
public class ExitComplete extends JFrame {

	private JLabel jLabel;
	private JButton jButton;

	public ExitComplete() {
		setTitle("Exit Complete");
		setSize(320, 100);
		setLayout(new FlowLayout());
		setLocation(150, 50);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jLabel = new JLabel("      Exit Complete      ");
		jButton = new JButton("  OK  ");

		jButton.addActionListener(new EventHandler());
		
		add(jLabel);
		add(jButton);
		
	}

	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new Login();//로그인창으로 돌아감
			dispose();
		}
	}
}