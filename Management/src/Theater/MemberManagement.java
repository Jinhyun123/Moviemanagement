package Theater;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import jdbcDao.JdbcDao;

public class MemberManagement {

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
	String age;

	public MemberManagement() {
		memberMap = new HashMap<>();
		memberMapList = new ArrayList<>();
	}

	public void newMember() {
		try {
			jdbcDao = new JdbcDao();
			sql = null;

			input = new Scanner(System.in);
			//System.out.print("ID: ");
			id = input.nextLine();

			//System.out.print("Pawword: ");
			password = input.nextLine();

			//System.out.print("Name: ");
			name = input.nextLine();

			//System.out.print("Phone: ");
			phone = input.nextLine();

			//System.out.print("Age: ");
			age = input.nextLine();

			Member member = new Member();

			member.setId(id);
			member.setPassword(password);
			member.setAge(age);
			member.setPhone(phone);
			member.setName(name);

			sql = String.format(
					"insert into member.member_table (id,age,name,password,phone) values ('%s', %s, '%s', '%s', '%s')",
					member.getId(), member.getAge(), member.getName(), member.getPassword(), member.getPhone());

			jdbcDao.insertDao(sql);

			memberMap.put(id, member);
			memberMapList.add(memberMap);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*public void login() {
		while (true) {
			Scanner input = new Scanner(System.in);
			System.out.print("ID: ");
			String id = input.nextLine();

			if (memberMap.containsKey(id) == true) {

				System.out.print("Password: ");
				String password = input.nextLine();

				if ((memberMap.get(id).getPassword().equals(password))) {
					System.out.println("로그인성공");
					break;
				} else {
					System.out.println("로그인실패");
					continue;
				}
			} else {
				System.out.println("로그인실패");
				continue;
			}
		}
	}*/

	public void modify() {
		try {
			jdbcDao = new JdbcDao();
			sql = null;

				input = new Scanner(System.in);
				//System.out.print("ID : ");
				id = input.nextLine();

				if (memberMap.containsKey(id) == true) {

					//System.out.print("Password: ");
					password = input.nextLine();

					if ((memberMap.get(id).getPassword().equals(password))) {
						System.out.print("변경하실 비빌번호  : ");
						newPassword = input.nextLine();

						sql = String.format("update member.member_table set password='" + newPassword + "' where id='"
								+ memberMap.get(id).getId() + "'");
						
						jdbcDao.updateDao(sql);
						memberMap.get(id).setPassword(newPassword);
						
						System.out.println("비밀번호 변경 완료 ");
					} else {
						System.out.println("로그인실패");
					}
				} else {
					System.out.println("로그인실패");
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*public void delete() {
		try {
			JdbcDao jdbcDao = new JdbcDao();
			String sql = null;
			
			while (true) {
				Scanner input = new Scanner(System.in);
				String id = input.nextLine();

				if (memberMap.containsKey(id) == true) {

					System.out.print("Password: ");
					String password = input.nextLine();

					if ((memberMap.get(id).getPassword().equals(password))) {						
						sql = String.format("delete from member.member_table where id = '"+id+"'");
						jdbcDao.deletetDao(sql);
						
						memberMap.remove(id);
						System.out.println("탈퇴되었습니다");
						
						break;
					} else {
						System.out.println("로그인실패");
						continue;
					}
				} else {
					System.out.println("로그인실패");
					continue;
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/

}
