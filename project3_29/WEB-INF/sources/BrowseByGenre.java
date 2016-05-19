package MyClasses; 

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class BrowseByGenre extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		try {
			
			ArrayList<Genre> genreBrowseList = new ArrayList<Genre>(); 
				
			String loginUser = "root";
			String loginPasswd = "calmdude6994";
			String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Statement statement = dbcon.createStatement(); 
			String query = "SELECT * FROM genres ORDER BY name";
			
			ResultSet result = statement.executeQuery(query); 
			while(result.next())
				genreBrowseList.add(new Genre(result.getInt("id"), result.getString("name"))); 
				
			session.setAttribute("genreBrowseList", genreBrowseList); 

			RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/WEB-INF/JSP/BrowseByGenre.jsp");
			RequetsDispatcherObj.forward(request, response);
			
			result.close();
			statement.close(); 
			dbcon.close(); 
			
		}
		
		catch (SQLException ex) {
			
			while (ex != null) {
				System.out.println ("SQL Exception:  " + ex.getMessage ());
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