import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class BrowseByTitle extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		try {
			
			ArrayList<String> movieBrowseList = new ArrayList<String>(); 
				
			String loginUser = "root";
			String loginPasswd = "calmdude6994";
			String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Statement statement = dbcon.createStatement(); 
			String query = "SELECT * FROM movies ORDER BY title";
			
			ResultSet result = statement.executeQuery(query); 
			while (result.next()){
				
				if (movieBrowseList.size() == 0){
					
					movieBrowseList.add(result.getString("title").substring(0, 1)); 
					continue; 
					
				}
					
				
				if (!movieBrowseList.get(movieBrowseList.size() - 1).equals(result.getString("title").substring(0, 1)))
					movieBrowseList.add(result.getString("title").substring(0, 1)); 
				
			}
				
			session.setAttribute("movieBrowseList", movieBrowseList); 

			RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/browse/title/listing");
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