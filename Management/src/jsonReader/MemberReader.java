package jsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MemberReader {
	
	private static String readAll(Reader rd) throws IOException{
		StringBuilder sb = new StringBuilder();
		int cp;
		while((cp = rd.read()) != -1){
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static String readJsonString(String url) throws IOException{

		InputStream is = new URL(url).openStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
		String jsonText = readAll(rd); //jsonText에 readAll메소드의 파라미터를보낸 리턴값을 저장한다.
		return jsonText; 
	}
	
	public ArrayList<HashMap<String, String>> data(){ //ArrayList안에 해쉬맵이 들어가있는 자료형의 data() 메소드 (리턴값의 자료형은 동일하다)
		
		ArrayList<HashMap<String, String>> db_member_list = new ArrayList<HashMap<String, String>>(); //객체 생성
		
		try {
			
			String json = readJsonString("http://localhost:8080/study/Member.jsp"); //  readJsonString의 메소드의 리턴값을 String형 json에 담는다
			//System.out.println(json.toString()); //jason파일 string 형식

			JSONParser jsonParser = new JSONParser();//JSON 라이브러리안에 속해있는 JSONParser 객체생성
			
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json.toString()); //json데이터를 넣어  JSONObject로 만들어준다
			
			
			JSONArray jsonArray = (JSONArray) jsonObject.get("MemberJason"); //jsonObject 의 key가 "MemberJason"인 value값을 JSONArray로 만들어준다
			
			
			for(int i=0; i<jsonArray.size(); i++) {
				
				HashMap<String, String> db_data = new HashMap<String,String>();// HashMap 객체 생성
				
				JSONObject jhObject = (JSONObject) jsonArray.get(i); // jsonArray의 index에 해당되는 값을 JSONObject 형식인 jhObject화 시킨다.
				
				db_data.put("num", jhObject.get("num").toString()); //db_data key값에 "num" value 값에  jhObject의 key값 "num"에 해당되는 value값을 넣는다
				db_data.put("id", jhObject.get("id").toString()); //db_data key값에 "id" value 값에  jhObject의 key값 "id"에 해당되는 value값을 넣는다
				db_data.put("age", jhObject.get("age").toString()); //db_data key값에 "age" value 값에  jhObject의 key값 "age"에 해당되는 value값을 넣는다
				db_data.put("name", jhObject.get("name").toString());//db_data key값에 "name" value 값에  jhObject의 key값 "name"에 해당되는 value값을 넣는다
				db_data.put("password", jhObject.get("password").toString()); //db_data key값에 "password" value 값에  jhObject의 key값 "password"에 해당되는 value값을 넣는다
				db_data.put("phone", jhObject.get("phone").toString()); //db_data key값에 "phone" value 값에  jhObject의 key값 "phone"에 해당되는 value값을 넣는다
				db_member_list.add(db_data); // HashMap을 담고있는 ArrayList에 추가 시킨다.(for문이 돌아갈때 마다)
				
				//System.out.println("HashMap: "+db_data);
				
			//	System.out.println(i+" : "+jhObject.toString());
			}
		
			//System.out.println("jsonArray : "+jsonArray);
			//System.out.println("HashMapList : "+db_data_list.toString());
			//System.out.println("HashMap: "+db_data_list.get(0).get("id")); 
			//System.out.println("HashMap: "+db_data_list.get(1).get("id"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return db_member_list; // ArrayList<HashMap<String, String>>의 자료형인 db_data_list를 반환한다.
	}
}
