package MyClasses; 

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SingleMovie extends HttpServlet
{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		try{
				
			String loginUser = "root";
			String loginPasswd = "calmdude6994";
			String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
			
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		    Statement statement = dbcon.createStatement();
		    int movieID = Integer.parseInt(request.getParameter("id"));  
			String query = "SELECT * FROM movies WHERE id = " + movieID; 
		    ResultSet result = statement.executeQuery(query); 
		
			result.next(); 
		    Movie singleMovie = new Movie(result.getInt("id"), result.getString("title"), result.getInt("year"), result.getString("director"), Movie.getListOfGenres(result.getInt("id")), Movie.getListOfStars(result.getInt("id")), result.getString("banner_url"), result.getString("trailer_url")); 
		
		    session.setAttribute("singleMovie", singleMovie); 
	
		    result.close();
		    statement.close();
		    dbcon.close();
	
			RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/WEB-INF/JSP/SingleMovie.jsp");
			RequetsDispatcherObj.forward(request, response);
			
		}
		
		catch (SQLException ex) {
			
			while (ex != null) {
				out.println("SQL Exception: " + ex.getMessage());
				ex = ex.getNextException ();
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
		
		out.close(); 
			
	}
}