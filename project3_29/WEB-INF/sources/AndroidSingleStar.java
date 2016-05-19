package MyClasses; 

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.*; 
import com.google.gson.*;

public class AndroidSingleStar extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		Connection dbcon = null; 
		ResultSet result = null; 
		Statement starIDQueryStatement = null; 

		try {
		        	
			String loginUser = "root";
			String loginPasswd = "calmdude6994";
			String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			
			int starID = Integer.parseInt(request.getParameter("starID")); 

			String starIDQuery = "SELECT * FROM stars WHERE id=" + starID + "; "; 
			starIDQueryStatement = dbcon.createStatement();
			result = starIDQueryStatement.executeQuery(starIDQuery);  
			
			result.next(); 
			
			Star star = new Star(result.getInt("id"), result.getString("first_name"), result.getString("last_name"), result.getString("dob"), result.getString("photo_url"), Star.getListOfMovies(result.getInt("id"))); 
			
			String starJson = new Gson().toJson(star); 
			out.println(starJson); 
				
			result.close(); 
			starIDQueryStatement.close(); 
			dbcon.close(); 
			out.close(); 
		         
		}
			
		catch (SQLException ex) {
			
			while (ex != null) {
				out.println("SQL Exception: " + ex.getMessage());
				ex = ex.getNextException();
			}
				
		}

		catch(java.lang.Exception ex){
			
			out.println("<HTML>" +
						"<HEAD><TITLE>" +
						"MovieDB: Error" +
						"</TITLE></HEAD>\n<BODY>" +
						"<P>SQL error in doGet: " +
						ex.getMessage() + "</P></BODY></HTML>");
			
		}
		
		finally {
			
			if (out != null)
				out.close(); 
				
			try { result.close(); } catch (Exception e) {}
			try { starIDQueryStatement.close(); } catch (Exception e) {}
			try { dbcon.close(); } catch (Exception e) {}
			
		}
			
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
	 
		doGet(request, response); 
	   
	}


}