package test;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

@Path("/connect")
public class Main {
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String checkLogin(@FormParam("username") String account, @FormParam("password") String password) throws SQLException, ClassNotFoundException {
		String result;
		if(DataSetter.accountCheck(account, password))
			result = "Success";
		else
			result = "Fail";
		return result;	
	}
	
	
	@POST
	@Path("/createaccount")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String createAccount(@FormParam("username") String account, @FormParam("password") String password) throws SQLException, ClassNotFoundException {
		String result = DataSetter.accountCreate(account, password);
		return result;	
	}

}


