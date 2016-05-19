package MyClasses; 

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AutoPopup extends HttpServlet
{
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		try {

	        String loginUser = "root";
	        String loginPasswd = "calmdude6994";
	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			
			int movieID = Integer.parseInt(request.getParameter("movieID")); 
			String contextPath = request.getParameter("contextPath"); 
			
			String query = "SELECT * FROM movies WHERE id = ?; "; 
			
			PreparedStatement movieStatement = dbcon.prepareStatement(query); 
			movieStatement.setInt(1, movieID); 
			ResultSet movieResult = movieStatement.executeQuery(); 
			movieResult.next(); 
			
			out.println("<table>"); 
			
			out.println("<tr>\n\t<td style=\"font-weight:bold\">Movie Description Link: </td>\n<td><a href=\"" + contextPath + "/success/singlemovie?id=" + movieID + "\">" + movieResult.getString("title").trim() + "</a></td></tr>"); 
			out.println("<tr>\n\t<td style=\"font-weight:bold\">Release Date: </td>\n<td>" + Integer.toString(movieResult.getInt("year")) + "</td>\n</tr>\n"); 
			ArrayList<Star> listOfStars = Movie.getListOfStars(movieID); 
			if (listOfStars.size() > 0)
				out.println("<tr>\n\t<td style=\"font-weight:bold\">Stars: </td>\n\t<td>\n\t<ul>\n"); 
			for (Star s : listOfStars)
				out.println("<li>" + s.getFirstName() + " " + s.getLastName() + "</li>");
			if (listOfStars.size() > 0)
				out.println("</ul></td>\n</tr>\n"); 
				
			String bannerURL = movieResult.getString("banner_url"); 
			if (bannerURL == null || bannerURL.equals(""))
				bannerURL = ""; 
			out.println("<tr>\n\t<td style=\"font-weight:bold\">Banner: </td>\n<td><img src=\"" + bannerURL +  "\" alt=\"Image Not Available\"></td></tr>"); 
			
			movieResult.close(); 
			movieStatement.close(); 
			dbcon.close(); 
			
			
		}
		
		catch (SQLException ex) {
			
			out.println("<HTML>" +
			"<HEAD><TITLE>" +
			"MovieDB: Error" +
			"</TITLE></HEAD>\n<BODY>" +
			"<P>SQL error in doGet: " +
			ex.getMessage() + "</P></BODY></HTML>");

		}

		catch(java.lang.Exception ex){
			
			out.println("<HTML>" +
			"<HEAD><TITLE>" +
			"MovieDB: Error" +
			"</TITLE></HEAD>\n<BODY>" +
			"<P>SQL error in doGet: " +
			ex.getMessage() + "</P></BODY></HTML>");
			
		}
			
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
		doGet(request, response);
    }

}