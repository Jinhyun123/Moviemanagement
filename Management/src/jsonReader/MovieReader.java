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

public class MovieReader {
	
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
		String jsonText = readAll(rd);
		
		return jsonText;
	}
	
	public ArrayList<HashMap<String, String>> data(){
		
		
		MemberReader jsonReader = new MemberReader();
		ArrayList<HashMap<String, String>> db_data_movie_list = new ArrayList<HashMap<String, String>>();	
		
		try {
			String json = readJsonString("http://localhost:8080/study/Movie.jsp");
			
			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(json.toString());
			
			JSONArray jsonArray = (JSONArray) jsonObject.get("MovieJason"); //jsonObject 의 key가 "MovieJason" 의 value 값을 넣어줌
			
			for(int i=jsonArray.size()-1; i>=0; i--) {
				
				HashMap<String, String> db_data = new HashMap<String,String>();
				
				JSONObject jhObject = (JSONObject) jsonArray.get(i); // jsonArray의 index에 해당되는 값을 JSONObject 형식인 jhObject화 시킨다.
				
				db_data.put("num", jhObject.get("num").toString()); //db_data key값에 "num" value 값에  jhObject의 key값 "num"에 해당되는 value값을 넣는다
				db_data.put("title", jhObject.get("title").toString());
				db_data.put("poster", jhObject.get("poster").toString());
				db_data.put("content", jhObject.get("content").toString());//db_data key값에 "content" value 값에  jhObject의 key값 "content"에 해당되는 value값을 넣는다
				db_data.put("grade",jhObject.get("grade").toString());
				db_data.put("count",jhObject.get("count").toString());
				db_data.put("average",jhObject.get("average").toString());
				
				
				db_data_movie_list.add(db_data);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return db_data_movie_list;
	}
	public static void main(String []args){
		MovieReader r = new MovieReader();
		System.out.println(r.data().get(0).toString());
	}
	
}
