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

import KeepInformation.KeepInformation;

public class ReviewReader {
	
	KeepInformation keepInformation = KeepInformation.getInstance();
	
	private static String readAll(Reader rd) throws IOException{
		StringBuilder sb = new StringBuilder();
		int cp;
		while((cp = rd.read()) != -1){
			sb.append((char) cp);
		}
		return sb.toString();
	}

	//string 
	public static String readJsonString(String url) throws IOException{

		InputStream is = new URL(url).openStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
		String jsonText = readAll(rd);
		
		return jsonText;
	}
	
	public ArrayList<HashMap<String, String>> data(){
		
		MemberReader jsonReader = new MemberReader();
		ArrayList<HashMap<String, String>> db_data_review_list = new ArrayList<HashMap<String, String>>();	
		
		try {
			String json = readJsonString("http://localhost:8080/study/Review.jsp?"+"movie_title="+keepInformation.getMovie().get("title")); //key value  get방식으로넘긴다
			
			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json.toString());
			
			JSONArray jsonArray = (JSONArray) jsonObject.get("Review"); //jsonObject 의 key가 "Review" 의 value 값을 넣어줌
			
			for(int i=0; i<jsonArray.size(); i++) {
				
				HashMap<String, String> db_data = new HashMap<String,String>();
				
				JSONObject jhObject = (JSONObject) jsonArray.get(i); 
				System.out.println(jhObject.get("review").toString());
				db_data.put("member_id", jhObject.get("member_id").toString());
				db_data.put("review", jhObject.get("review").toString());
				db_data_review_list.add(db_data);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return db_data_review_list;
	}
}
