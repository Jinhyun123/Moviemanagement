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
		String dbUser = "root";
		String dbPass = "1234";
		String query = "select * from movie_table ";

		conn = DriverManager.getConnection(jdbcDriver, dbUser, dbPass);

		stmt = conn.createStatement();

		rs = stmt.executeQuery(query);
		
		JSONObject jsonmain = new JSONObject(); // 객체
		JSONArray jArray = new JSONArray();
		
		//디비에서 가져오기
		while (rs.next()) {
			String num = rs.getString("num");
			String title = rs.getString("title");
			String poster = rs.getString("poster");
			String content = rs.getString("content");
			int grade = rs.getInt("grade");
			int count = rs.getInt("count");
			String average = rs.getString("average");
		
		//제이슨 파일로 변환해서 띄어줌
		JSONObject jobject = new JSONObject(); // JSON내용을 담을 객체
		
		jobject.put("num", num);
		jobject.put("title",title);
		jobject.put("poster",poster);
		jobject.put("content", content);
		jobject.put("grade", grade);
		jobject.put("count", count);
		jobject.put("average", average);
		
		
		
		jArray.add(0, jobject);
		
		}
		
		//out.println(jArray);
		
		jsonmain.put("MovieJason", jArray);
		
		out.println(jsonmain);
		out.flush();
	
	%>
		
