package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DataSetter {
	public static Database db= new Database();
	public static int[] accountCheck(String account, String password) throws SQLException, ClassNotFoundException {
		int[] result = {0, -1};
		String sql = "SELECT * FROM Account WHERE username='" + account + "' AND password = '" + password + "'";		
		PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			result[0] = 1;
			int type = rs.getInt("type");
			result[1] = type;
			}

		
		return result;
	}
	
	public static boolean accountCreate(String account, String password) throws SQLException, ClassNotFoundException {
		boolean result = false;
		String sql = "SELECT * FROM Account WHERE username='" + account + "'";		
		PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			result = false;
		}
		else {
			db.runSql2("INSERT INTO Account (username, password, type) VALUES ('" + account + "', '" + password + "', 0)");
			db.runSql2("INSERT INTO UserInfo (username, type) VALUES ('" + account + "', 0)");
			result = true;
		}
		
		return result;
	}
}

