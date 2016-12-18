package jdbcDao; //Data Access Objects의  연결을 담당하는 일을 가진 객체

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.mysql.jdbc.Statement;

import Theater.Member;

public class JdbcDao {
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public JdbcDao() throws SQLException{
		con = DriverManager.getConnection("jdbc:mysql://localhost","root", "1234");

		stmt = (Statement) con.createStatement();
		if(stmt != null) {
			System.out.println("good");
		}
	}
	
	/*public int selectDao(String sql) throws SQLException{
		System.out.println(sql);
		return stmt.executeUpdate(sql);
	}*/
	
	public int deletetDao(String sql) throws SQLException{
		System.out.println(sql);
		return stmt.executeUpdate(sql);
	}
	
	public int insertDao(String sql) throws SQLException{
		System.out.println(sql);
		return stmt.executeUpdate(sql);
	}
	
	public int updateDao(String sql) throws SQLException{
		System.out.println(sql);
		stmt.execute("SET SQL_SAFE_UPDATES =0");
		return stmt.executeUpdate(sql);
	}
}
