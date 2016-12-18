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
import javax.swing.JPanel;

import KeepInformation.KeepInformation;
import Movie.MovieExplain;
import jsonReader.MovieReader;
import jsonReader.ReviewReader;


public class MoviePoster extends JFrame {
	
	
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage image3;
	private BufferedImage image4;
	private BufferedImage image5;
	private BufferedImage image6;
	
	JButton jButton0;
	JButton jButton1;
	JButton jButton2;
	JButton jButton3;
	JButton jButton4;
	JButton jButton5;
	JButton jButton6;
	
	JPanel j1;
	JPanel j2;
	JPanel j3;
	JPanel j4;
	JPanel j5;
	JPanel j6;
	JPanel j7;

	public MoviePoster() {
		setTitle("Movie Poster");
		setSize(600, 500);
		setLayout(new FlowLayout());
		setLocation(150, 50);
		setVisible(true);
		try {
			image1 = ImageIO.read(new File("C:\\Users\\mic_custom\\Desktop\\image1.PNG"));
			image2 = ImageIO.read(new File("C:\\Users\\mic_custom\\Desktop\\image2.PNG"));
			image3 = ImageIO.read(new File("C:\\Users\\mic_custom\\Desktop\\image3.PNG"));
			image4 = ImageIO.read(new File("C:\\Users\\mic_custom\\Desktop\\image4.PNG"));
			image5 = ImageIO.read(new File("C:\\Users\\mic_custom\\Desktop\\image5.PNG"));
			image6 = ImageIO.read(new File("C:\\Users\\mic_custom\\Desktop\\image6.PNG"));
		} catch (IOException e) {

			e.printStackTrace();
		}

		jButton0 = new JButton(new ImageIcon(image1));
		jButton0.addActionListener(new EventHandler());
		
		jButton1 = new JButton(new ImageIcon(image2));
		jButton1.addActionListener(new EventHandler());
		
		jButton2 = new JButton(new ImageIcon(image3));
		jButton2.addActionListener(new EventHandler());
		
		jButton3 = new JButton(new ImageIcon(image4));
		jButton3.addActionListener(new EventHandler());
		
		jButton4 = new JButton(new ImageIcon(image5));
		jButton4.addActionListener(new EventHandler());
		
		jButton5 = new JButton(new ImageIcon(image6));
		jButton5.addActionListener(new EventHandler());
		
		jButton6 = new JButton("  Back  ");
		jButton6.addActionListener(new EventHandler());
		
		j1 = new JPanel();
		j2 = new JPanel();
		j3 = new JPanel();
		j4 = new JPanel();
		j5 = new JPanel();
		j6 = new JPanel();
		j7 = new JPanel();

		j1.add(jButton0);
		j2.add(jButton1);
		j3.add(jButton2);
		j4.add(jButton3);
		j5.add(jButton4);
		j6.add(jButton5);
		j7.add(jButton6);

		add(j1);
		add(j2);
		add(j3);
		add(j4);
		add(j5);
		add(j6);
		add(j7);
	}
	
	
	class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MovieReader movieReader = new MovieReader();
			KeepInformation movieInformation = KeepInformation.getInstance(); //singleton pattern 사용을 위해
			
			if(e.getSource() == jButton0) {
				movieInformation.setMovie(movieReader.data().get(0)); //hashmap button0 에 해당되는 영화 정보가 들어감  singleton pattern
				new MovieExplain();
				dispose();
			}
			else if(e.getSource() == jButton1){
				movieInformation.setMovie(movieReader.data().get(1));
				new MovieExplain();
				dispose();
			}
			else if(e.getSource() == jButton2){
				movieInformation.setMovie(movieReader.data().get(2));
				new MovieExplain();
				dispose();
			}
			else if(e.getSource() == jButton3){
				movieInformation.setMovie(movieReader.data().get(3));
				new MovieExplain();
				dispose();
			}
			else if(e.getSource() == jButton4){
				movieInformation.setMovie(movieReader.data().get(4));
				new MovieExplain();
				dispose();
			}
			else if(e.getSource() == jButton5){
				movieInformation.setMovie(movieReader.data().get(5));
				new MovieExplain();
				dispose();
			}
			else if(e.getSource() == jButton6){
				new ChoiceModifyOrMoviePoster();
				dispose();
			}
		}
	}
}
