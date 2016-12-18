package KeepInformation;

import java.util.HashMap;

public class KeepInformation { //singleton pattern
	private static KeepInformation instance;
	
	static String id;
	static String password;
	
	static HashMap<String, String> movie;
	

	public HashMap<String, String> getMovie() {
		return movie;
	}

	public  void setMovie(HashMap<String, String> movie) {
		this.movie = movie;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private KeepInformation(){}
	
	public static synchronized KeepInformation getInstance(){ // 방해받지말고 끝날때까지..
		if(instance == null){
			instance = new KeepInformation();
		}
		return instance;
	}
}
