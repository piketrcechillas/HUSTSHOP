package test;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

@Path("/connect")
public class Main {
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String checkLogin(@FormParam("username") String account, @FormParam("password") String password) throws SQLException, ClassNotFoundException, JSONException {
		int[] resultArray = DataSetter.accountCheck(account, password);
		System.out.println("Account: " + account);
		System.out.println("Password: " + password);
		JSONObject json = new JSONObject();
		if(resultArray[0] == 1) {
			json.put("type", resultArray[1]);
			json.put("username", account);
			json.put("status", "Success");
		}
		else {
			json.put("status", "Fail");
		}
	
		return json.toString();
	}
	
	
	@POST
	@Path("/createaccount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String createAccount(@FormParam("username") String account, @FormParam("password") String password) throws SQLException, ClassNotFoundException, JSONException {
		boolean result = DataSetter.accountCreate(account, password);
		JSONObject json = new JSONObject();
		if(result) {
			json.put("username", account);
			json.put("status", "Success");
		}
		else {
			json.put("status", "Fail");
		}
	
		return json.toString();
	}
	
	@POST
	@Path("/getuserinfo")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String getUserInfo(@FormParam("username") String account) throws SQLException, ClassNotFoundException, JSONException {
		JSONObject obj = UserController.getUserInfo(account);
		return obj.toString();
	}
	
	@POST
	@Path("/updateuserinfo")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateUserInfo(@FormParam("username") String account, @FormParam("password") String password, @FormParam("name") String name, @FormParam("gender") String gender,
									@FormParam("date") Date date, @FormParam("address") String address, @FormParam("district") String district,
									@FormParam("province") String province, @FormParam("city") String city, @FormParam("country") String country,
									@FormParam("telephone") String telephone) throws SQLException, ClassNotFoundException, JSONException, ParseException {
		HashMap<String, Object> hash = new HashMap<>();
		if(name!=null)
			hash.put("name", name);
		if(gender!=null)
			hash.put("gender", gender);
		if(date!=null) {
			hash.put("date", date);
			System.out.println("Passed");
		}
		if(address!=null)
			hash.put("address", address);
		if(district!=null)
			hash.put("district", district);
		if(province!=null)
			hash.put("province", province);
		if(city!=null)
			hash.put("city", city);
		if(country!=null)
			hash.put("country", country);
		if(telephone!=null)
			hash.put("telephone", telephone);
		boolean res = UserController.updateUserInfo(account, password, hash);
		JSONObject obj = new JSONObject();
		if(res) {
			 obj = UserController.getUserInfo(account);
			 obj.put("status", "Success");
			
		}
		else {
			 obj.put("status", "Failed");
		}
		
		return obj.toString();
		
	}

}


