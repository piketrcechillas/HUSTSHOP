package test;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

@Path("/connect")
public class Main {
	
	@Context
	UriInfo uri;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkLogin(@FormParam("username") String account, @FormParam("password") String password) throws SQLException, ClassNotFoundException, JSONException {
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
		URI myUri = uri.getBaseUri();
		URI path = uri.getAbsolutePath();
		NewCookie cookie = new NewCookie("currentUser", account, path.toString(), myUri.toString(), null, 7200, false);
	
		return Response.ok(json.toString()).cookie(cookie).build();
	}
	
	
	@POST
	@Path("/createaccount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAccount(@FormParam("username") String account, @FormParam("password") String password) throws SQLException, ClassNotFoundException, JSONException {
		boolean result = DataSetter.accountCreate(account, password);
		JSONObject json = new JSONObject();
		if(result) {
			json.put("username", account);
			json.put("status", "Success");
		}
		else {
			json.put("status", "Fail");
		}
	
		return Response.ok(json.toString()).build();
	}
	
	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@CookieParam ("currentUser") Cookie cookie) throws SQLException, ClassNotFoundException, JSONException {
		 if (cookie != null) {
			    NewCookie newCookie = new NewCookie(cookie, null, 0, false);
			    return Response.ok("Logged out").cookie(newCookie).build();
		 }
		 return Response.ok("No session").build();
	}
	
	@GET
	@Path("/getuserinfo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserInfo(@CookieParam("currentUser") Cookie cookie) throws SQLException, ClassNotFoundException, JSONException {
		if(cookie != null)
			{JSONObject obj = UserController.getUserInfo(cookie.getValue());
			return Response.ok(obj.toString()).build();}
		return Response.ok("Invalid session").build();
	}
	
	@POST
	@Path("/updateuserinfo")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUserInfo(@CookieParam("currentUser") Cookie cookie, @FormParam("name") String name, @FormParam("gender") String gender,
									@FormParam("date") Date date, @FormParam("address") String address, @FormParam("district") String district,
									@FormParam("province") String province, @FormParam("city") String city, @FormParam("country") String country,
									@FormParam("telephone") String telephone) throws SQLException, ClassNotFoundException, JSONException, ParseException {
		if(cookie != null) {
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
			boolean res = UserController.updateUserInfo(cookie.getValue(), hash);
			JSONObject obj = new JSONObject();
			if(res) {
				 obj = UserController.getUserInfo(cookie.getValue());
				 obj.put("status", "Success");
				
			}
			else {
				 obj.put("status", "Failed");
			}
			
			return Response.ok(obj.toString()).build();
		}
		else {
			return Response.ok("Invalid session").build();
		}
	}
	
}


