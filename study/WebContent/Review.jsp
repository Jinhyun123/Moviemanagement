<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@	page import="org.json.simple.JSONObject"%>
<%@	page import="org.json.simple.JSONArray"%>

<%
response.setCharacterEncoding("UTF-8");
		Class.forName("com.mysql.jdbc.Driver");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		String jdbcDriver = "jdbc:mysql://localhost:3306/member";
		String dbUser = "root";//계정이름
		String dbPass = "1234";//비밀번호
		String title = request.getParameter("movie_title");//ReviewReader에서 get방식으로 보낸 해당 키값에 해당되는 value값을 title변수에 넣어준다
		String query = "select member_id, review from review_table where review_table.movie_title = '"+title+"';"; //현재 영화제목에 해당되는 member_id 와 review를 전부 나타낸다

		conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);

		stmt = conn.createStatement();

		rs = stmt.executeQuery(query);
		
		JSONObject jsonmain = new JSONObject(); // 객체
		JSONArray jArray = new JSONArray();
		
		//디비에서 가져오기
		while (rs.next()) {
			String member_id = rs.getString("member_id");
			String review = rs.getString("review");
		
		//제이슨 파일로 변환해서 띄어줌
		JSONObject jobject = new JSONObject(); // JSON내용을 담을 객체
		
		jobject.put("member_id", member_id);
		jobject.put("review",review);
		
		jArray.add(0, jobject);
		
		}
		
		//out.println(jArray);
		
		jsonmain.put("Review", jArray);
		
		out.println(jsonmain);
		out.flush();
	
	%>
		
