import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SingleStar extends HttpServlet
{	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		try {
				
			String loginUser = "root";
			String loginPasswd = "calmdude6994";
			String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		    Statement statement = dbcon.createStatement();
		    int starID = Integer.parseInt(request.getParameter("id"));  
		    ResultSet result = statement.executeQuery("Select * from stars where id = " + starID);
	 
		    Star singleStar = new Star(result.getInt("id"), result.getString("first_name"), result.getString("last_name"), result.getString("dob"), result.getString("photo_url"), Star.getListOfMovies(result.getInt("id"))); 
	
		    session.setAttribute("singleStar", singleStar); 
	
		    result.close();
		    statement.close();
		    dbcon.close();
			
		 	RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/SingleStar.jsp");
		 	RequetsDispatcherObj.forward(request, response);
		 	
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
