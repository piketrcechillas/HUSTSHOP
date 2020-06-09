package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductController {
	public static Database db= new Database();
	public static JSONArray getProduct(String name, String category) throws SQLException, JSONException {
		String sql = null;
		if(name != "" && category != "")
			sql = "SELECT * FROM Products WHERE name LIKE '%" + name + "%' AND category = '" + category + "'";
		else if(name != "")
			sql = "SELECT * FROM Products WHERE name LIKE '%" + name + "%'";
		else if(category != "")
			sql = "SELECT * FROM Products WHERE category = '" + category + "'";
		else
			sql = "SELECT * FROM Products";
		PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.executeQuery();
		JSONArray productArray = new JSONArray();
		while(rs.next()) {
			JSONObject productInfo = new JSONObject();
			productInfo.put("id", rs.getInt("id"));	
			productInfo.put("name", rs.getString("name"));
			productInfo.put("category", rs.getString("category"));
			productInfo.put("price",  rs.getInt("price"));
			productInfo.put("description", rs.getString("description"));
			productArray.put(productInfo);
		}
		return productArray;	
	}
	
	public static boolean createProduct(String name, String category, String description, int price, String imageURL) throws SQLException, JSONException {
		boolean result = false;
		String sql = "SELECT * FROM Products WHERE name='" + name + "'";		
		PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			result = false;
		}
		else {
			db.runSql2("INSERT INTO Products (name, category, price, description, imageURL) VALUES ('" + name + "', '" + category + "', " + price + ", '" + description + "', '" + imageURL + "')");
			result = true;
		}
		
		return result;
	}
}
