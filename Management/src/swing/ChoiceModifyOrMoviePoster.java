package swing;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChoiceModifyOrMoviePoster extends JFrame {

	private JButton jButton_modify;
	private JButton jButton_moviePoster;
	private JButton jButton_exitMember;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	private JLabel jLabel_image;

	private BufferedImage image1;

	public ChoiceModifyOrMoviePoster() {

		setTitle("Choice Modify Or MoviePoster");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350, 400);
		setLayout(new FlowLayout());

		jButton_modify = new JButton("My information modify");
		jButton_modify.addActionListener(new EventHandler());
		jButton_moviePoster = new JButton("  Start Movie Poster ");
		jButton_moviePoster.addActionListener(new EventHandler());
		jButton_exitMember = new JButton("  Exit Member ");
		jButton_exitMember.addActionListener(new EventHandler());
		try {
			image1 = ImageIO.read(new File("C:\\Users\\mic_custom\\Desktop\\images.JPG"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		jLabel_image = new JLabel(new ImageIcon(image1));
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();

		p1.add(jButton_modify);
		p1.add(jButton_exitMember);
		p2.add(jLabel_image);
		p3.add(jButton_moviePoster);

		add(p1);
		add(p2);
		add(p3);
		setLocation(150, 50); // 위치지정
		setVisible(true);
	}

	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jButton_modify) {
				new Modify();
				dispose();
			} else if (e.getSource() == jButton_moviePoster) {
				new MoviePoster();
				dispose();
			} else if (e.getSource() == jButton_exitMember) {
				new ExitMember();
				dispose();
			}
		}
	}
}
