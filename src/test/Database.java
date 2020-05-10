package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	 
	public Connection conn = null;
 
	public Database() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://hustshop.database.windows.net:1433;database=hustshop;encrypt=true;trustServerCertificate=true;";
			conn = DriverManager.getConnection(url, "hustshop", "Hust1234!");
			System.out.println("Database Connected");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
 
	public ResultSet runSql(String sql) throws SQLException {
		Statement stm = conn.createStatement();
		return stm.executeQuery(sql);
	}
 
	public boolean runSql2(String sql) throws SQLException {
		Statement stm = conn.createStatement();
		return stm.execute(sql);
	}
 
	@Override
	protected void finalize() throws Throwable {
		if (conn != null || !conn.isClosed()) {
			conn.close();
		}
	}
}