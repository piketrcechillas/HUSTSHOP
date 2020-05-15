package test;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class UserController {
	public static Database db= new Database();
	public static JSONObject getUserInfo(String account) throws SQLException, ClassNotFoundException, JSONException {
		String sql = "SELECT * FROM UserInfo WHERE username='" + account + "'";		
		PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.executeQuery();
		JSONObject userInfo = new JSONObject();
		if(rs.next()) {
			userInfo.put("username", rs.getString("username"));	
			userInfo.put("name", rs.getString("name"));
			userInfo.put("type", rs.getInt("type"));
			userInfo.put("gender",  rs.getString("gender"));
			userInfo.put("birthday", rs.getDate("birthday"));
			userInfo.put("address", rs.getString("address"));
			userInfo.put("district", rs.getString("district"));
			userInfo.put("province", rs.getString("province"));
			userInfo.put("city", rs.getString("city"));
			userInfo.put("country", rs.getString("country"));
			userInfo.put("telephone", rs.getString("telephone"));
			
			}
		else {
			userInfo.put("error", "Data not found");
		}
		return userInfo;
	}
	
	public static boolean updateUserInfo(String account, HashMap<String, Object> map) throws SQLException {
		boolean result = false;
		String sql = "SELECT * FROM Account WHERE username='" + account + "'";	
		PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			result = true;
			if(map.containsKey("name")) {
				db.runSql2("UPDATE UserInfo SET name = '" + (String) map.get("name") + "' WHERE username = '" + account + "'");
			}
			if(map.containsKey("date")) {
				db.runSql2("UPDATE UserInfo SET birthday = '" + (Date) map.get("date") + "' WHERE username = '" + account + "'");
			}
			if(map.containsKey("gender")) {
				db.runSql2("UPDATE UserInfo SET gender = '" + (String) map.get("gender") + "' WHERE username = '" + account + "'");
			}
			if(map.containsKey("address")) {
				db.runSql2("UPDATE UserInfo SET address = '" + (String) map.get("address") + "' WHERE username = '" + account + "'");
			}
			if(map.containsKey("district")) {
				db.runSql2("UPDATE UserInfo SET district = '" + (String) map.get("district") + "' WHERE username = '" + account + "'");
			}
			if(map.containsKey("province")) {
				db.runSql2("UPDATE UserInfo SET province = '" + (String) map.get("province") + "' WHERE username = '" + account + "'");
			}
			if(map.containsKey("city")) {
				db.runSql2("UPDATE UserInfo SET city = '" + (String) map.get("city") + "' WHERE username = '" + account + "'");
			}
			if(map.containsKey("country")) {
				db.runSql2("UPDATE UserInfo SET country = '" + (String) map.get("country") + "' WHERE username = '" + account + "'");
			}
			if(map.containsKey("telephone")) {
				db.runSql2("UPDATE UserInfo SET telephone = '" + (String) map.get("telephone") + "' WHERE username = '" + account + "'");
			}
		}
		
		return result;
		
	}
}
