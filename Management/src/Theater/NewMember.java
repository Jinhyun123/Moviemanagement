package Theater;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import jdbcDao.JdbcDao;

public class NewMember {

	HashMap<String, Member> memberMap;
	ArrayList<HashMap<String, Member>> memberMapList;
	JdbcDao jdbcDao;
	String sql;
	Scanner input;
	String id;
	String password;
	String newPassword;
	String name;
	String phone;
	int age;

	public NewMember() {
		memberMap = new HashMap<>();
		memberMapList = new ArrayList<>();
	}

	public void newMember(String id, String password, String age, String name, String phone) {
		try {
			jdbcDao = new JdbcDao();
			sql = null;
			
			Member member = new Member();

			member.setId(id);
			member.setPassword(password);
			member.setAge(age);
			member.setPhone(phone);
			member.setName(name);

			sql = String.format(
					"insert into member.member_table (id,age,name,password,phone) values ('%s', '%s', '%s', '%s', '%s')",
					member.getId(), member.getAge(), member.getName(), member.getPassword(), member.getPhone());

			jdbcDao.insertDao(sql);

			memberMap.put(id, member);
			memberMapList.add(memberMap);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}