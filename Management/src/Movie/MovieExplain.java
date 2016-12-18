package Movie;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import KeepInformation.KeepInformation;
import jdbcDao.JdbcDao;
import jsonReader.MovieReader;
import jsonReader.ReviewReader;
import swing.MoviePoster;

public class MovieExplain extends JFrame {

	private BufferedImage image;
	JPanel jPanel_Poster_content;
	JPanel jPanel_Button;
	JPanel jPanel_Review;

	JPanel jPanel_all;

	JTextArea jTextArea_content;
	JTextArea jTextArea_Review;
	JTextField jTextfield_Review;
	JLabel jLabel_Conment;
	JLabel jLabel_Poster;

	JLabel jLabel_grade;
	JLabel jLabel_Nowgrade;

	JTextField jTextField_grade;

	JButton jButton_Back;
	JButton jButton_recommend;

	KeepInformation information;
	JdbcDao jdbcDao;
	ReviewReader reviewReader;
	MovieReader movieReader;

	public MovieExplain() {
		information = KeepInformation.getInstance();

		setSize(500, 600);
		setTitle(information.getMovie().get("title"));
		setLocation(150, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jPanel_all = new JPanel();
		jPanel_all.setSize(500, 600);
		jPanel_all.setLayout(new FlowLayout());

		jPanel_Poster_content = new JPanel();
		jPanel_Button = new JPanel();
		jPanel_Review = new JPanel();

		jPanel_Review.setLayout(new BorderLayout());

		jTextArea_Review = new JTextArea(5, 40);
		jTextArea_Review.setLineWrap(true);
		jTextArea_Review.setEditable(false);

		jTextfield_Review = new JTextField(10);

		jLabel_Nowgrade = new JLabel("현재평점 : " + information.getMovie().get("average")); //평점을 넣어줁다
		jLabel_grade = new JLabel("          평점 : ", Label.RIGHT);
		jTextField_grade = new JTextField(5); // 평점
		jTextField_grade.addActionListener(new EventHandler());

		jButton_Back = new JButton("Back");
		jButton_recommend = new JButton("추천 영화");

		jLabel_Conment = new JLabel(information.getMovie().get("title") + " 줄거리");

		try {
			image = ImageIO.read(new File(information.getMovie().get("poster")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		jLabel_Poster = new JLabel(new ImageIcon(image));

		jTextArea_content = new JTextArea(10, 40);
		jTextArea_content.setText(information.getMovie().get("content"));
		jTextArea_content.setLineWrap(true);
		jTextArea_content.setEditable(false); //수정못하도록함

		jPanel_Poster_content.setLayout(new BorderLayout());
		jPanel_Poster_content.add(new JScrollPane(jTextArea_content), "South");
		jPanel_Poster_content.add(jLabel_Conment, "Center");
		jPanel_Poster_content.add(jLabel_Poster, "North");

		jPanel_Button.add(jLabel_Nowgrade);
		jPanel_Button.add(jLabel_grade);
		jPanel_Button.add(jTextField_grade);
		jPanel_Button.add(jButton_recommend);
		jPanel_Button.add(jButton_Back);

		jPanel_Review.add(new JScrollPane(jTextArea_Review), "Center");
		jPanel_Review.add(jTextfield_Review, "South");

		jPanel_all.add(jPanel_Poster_content);
		jPanel_all.add(jPanel_Button);
		jPanel_all.add(jPanel_Review); // 댓글 라벨 + 별점

		add(jPanel_all);
		jTextArea_Review.addFocusListener(new EventHandler());
		jTextfield_Review.addFocusListener(new EventHandler());
		jTextfield_Review.addActionListener(new EventHandler());
		jButton_Back.addActionListener(new EventHandler());
		jButton_recommend.addActionListener(new EventHandler());

		reviewReader = new ReviewReader();

		ArrayList<HashMap<String, String>> aryhash = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> data = new HashMap<String, String>();

		aryhash = reviewReader.data();// aryhash에 reviewReder의 data메소드의 리턴값을 넣어준다
		String review = "";

		for (int i = aryhash.size() - 1; i >= 0; i--) { // 위에서 아래로 보이도록 (aryhash의 사이즈 만큼) 
			data = aryhash.get(i);// hashMap에  arraylist의 인텍스만큼 계속 넣어준다
			review += data.get("member_id") + " : "; //review 변수에 hashMap에 있는  키값 member_id의 해당하는 값을 넣고  :도 추가해서 넣는다
			review += data.get("review") + "\n"; //review 변수에 추가한다 댓글과 줄바꿈
			jTextArea_Review.append(review); // jTextArea_Review에   review를 보여준다
			review = "";// review를 초기화 시켜준다
		}

		setVisible(true);
	}

	class EventHandler extends FocusAdapter implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jTextfield_Review) {

				try {
					information = KeepInformation.getInstance();
					jdbcDao = new JdbcDao();

					String sql = null;

					//insert 쿼리문 review테이블에 각각 넣어준다
					sql = String.format(
							"insert into member.review_table (movie_title, member_id, review) values ('%s', '%s', '%s')",
							information.getMovie().get("title"), information.getId(), jTextfield_Review.getText());

					jdbcDao.insertDao(sql);

					ArrayList<HashMap<String, String>> aryhash = new ArrayList<HashMap<String, String>>();
					HashMap<String, String> data = new HashMap<String, String>();
					//System.out.println(reviewReader.data().toString());
					String review = "";

					aryhash = reviewReader.data(); // aryhash에 reviewReder의 data메소드의 리턴값을 넣어준다
					review += information.getId() + " : "; //review변수에 현재 아이디값넣어주고 : 넣어준다
					review += jTextfield_Review.getText() + "\n"; // review변수에 jTextfield_Review에 적힌 것을 넣어주고 줄바꿈해준다
					jTextArea_Review.append(review); // jTextArea_Review에 review에 담긴것을 보여준다
					review = "";
					jTextfield_Review.setText("");
				} catch (SQLException e1) {
					e1.printStackTrace();

				}
			} else if (e.getSource() == jTextField_grade) { 
				try {
					information = KeepInformation.getInstance();
					jdbcDao = new JdbcDao();
					movieReader = new MovieReader();

					int grade = 0;
					int grade_now = 0;
					int count = 0;
					double average_fake = 0;
					double average_real = 0;
					
					for (int i = 0; i < movieReader.data().size(); i++) {
						if (movieReader.data().get(i).get("title").equals(information.getMovie().get("title"))) {
							//movieReader의 인덱스에해당하는 키값이"title"과 같은것이 현재 선택된 영화의 제목일때
							
							count = Integer.parseInt(movieReader.data().get(i).get("count")) + 1; //현재 해당 영화의 평점을 입력한 횟수를 1증가시켜서 count 변수에  담는다
							grade = Integer.parseInt(movieReader.data().get(i).get("grade")); //현재 해당 영화의 평점을  grade 변수에 담는다
							grade_now = Integer.parseInt(jTextField_grade.getText());//입력한 평점을 grade_now 변수에 담는다

							average_fake = ((double) grade + (double) grade_now) / (double) count; // (기존의 평점+현재평점 )/평점을 매긴 횟수 를  average_fake변수에 담는다
							average_real = Double.parseDouble(String.format("%.1f", average_fake)); // 현재 해당영화의 전체평점을 average_real변수에 담는다

							String sql = null;

							//쿼리 수정부분 현재 해당되는 영화의 전체평점의 합 , 평점매긴횟수, 전체적으로 다 계산된 평점 
							sql = String.format("update member.movie_table set movie_table.grade = '"
									+ (grade + grade_now) + "', movie_table.count = '" + count
									+ "', movie_table.average = '" + average_real + "' where movie_table.title='"
									+ information.getMovie().get("title") + "'");
							jdbcDao.updateDao(sql);
						}
					}

					jTextField_grade.setText("");

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else if (e.getSource() == jButton_recommend) { //recommend 버튼을 눌렀을때
				//추천영화
				
				movieReader = new MovieReader();
				
				double zero =0;
				double tmp=0;
				String average="";
				String bestMovie="";
				
				for(int i =0; i< movieReader.data().size(); i++){
					 average = movieReader.data().get(i).get("average");//인덱스에 해당되는 영화의 평점을 담는다 (현재String형이다)
					 tmp = Double.parseDouble(average); //String ㅡ> double
					 
					 if(zero < tmp){
						 zero = tmp; //나중에는 zero에는 최고평점을가진 값이 들어가있다.
						 bestMovie = movieReader.data().get(i).get("title");//나중에 bestMovie변수에는 최고 높은 평점에 해당되는 영화제목이 올라가있다 
					 }
					 else if(i < movieReader.data().size()-1){ //영화의 갯수만큼 계속된다
						 continue;
					 }
					 else{
					 }
 				}
				JOptionPane.showMessageDialog(null, "추천영화의 영화는  "+ bestMovie); //최고높은 평점의 영화의 제목을 메세지로 띄어준다
			}

			else if (e.getSource() == jButton_Back) { //Back버튼일때
				new MoviePoster(); // MoviePoster 생성자 실행
				dispose(); // 기존의 창을 닫아준다
			}
		}
	}
}
