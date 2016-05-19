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

public class AndroidSingleMovie extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		Connection dbcon = null; 
		ResultSet result = null; 
		Statement movieIDQueryStatement = null; 

		try {
		        	
			String loginUser = "root";
			String loginPasswd = "calmdude6994";
			String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			
			int movieID = Integer.parseInt(request.getParameter("movieID")); 

			String movieIDQuery = "SELECT * FROM movies WHERE id=" + movieID + "; "; 
			movieIDQueryStatement = dbcon.createStatement();
			result = movieIDQueryStatement.executeQuery(movieIDQuery);  
			
			result.next(); 
			
			Movie movie = new Movie(result.getInt("id"), result.getString("title"), result.getInt("year"), result.getString("director"), Movie.getListOfGenres(result.getInt("id")), Movie.getListOfStars(result.getInt("id")), result.getString("banner_url"), result.getString("trailer_url")); 
			
			String movieJson = new Gson().toJson(movie); 
			out.println(movieJson); 
				
			result.close(); 
			movieIDQueryStatement.close(); 
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
			try { movieIDQueryStatement.close(); } catch (Exception e) {}
			try { dbcon.close(); } catch (Exception e) {}
			
		}
			
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
	 
		doGet(request, response); 
	   
	}


}