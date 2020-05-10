package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataSetter {
	public static Database db= new Database();
	public static boolean accountCheck(String account, String password) throws SQLException, ClassNotFoundException {
		boolean result = false;
		String sql = "SELECT * FROM Account WHERE username='" + account + "' AND password = '" + password + "'";		
		PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			result = true;}

		
		return result;
	}
	
	public static String accountCreate(String account, String password) throws SQLException, ClassNotFoundException {
		String result = null;
		String sql = "SELECT * FROM Account WHERE username='" + account + "'";		
		PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			result = "Account already existed";
		}
		else {
			db.runSql2("INSERT INTO Account (username, password) VALUES ('" + account + "', '" + password + "')");
			result = "Account created";
		}
		
		return result;
	}
}

