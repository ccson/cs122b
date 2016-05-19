package MyClasses; 

/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MetaData extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
		HttpSession session = request.getSession();

        String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

			
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);			
			ResultSet rs = dbcon.getMetaData().getTables(null, null, "%", null); 
			String result_string = "";
			
			while (rs.next()){
				
				System.out.println();
				result_string += ("Table: " + rs.getString(3) + "<br>");
				Statement select = dbcon.createStatement();
				ResultSet result = select.executeQuery("Select * from " + rs.getString(3));
				ResultSetMetaData metadata = result.getMetaData();
				for (int i = 1; i <= metadata.getColumnCount(); i++)
					result_string += ("Type of column "+ metadata.getColumnName(i) + ": " + metadata.getColumnTypeName(i) + "<br>");
				result_string += "<br>";
				select.close();
				result.close(); 
				
			}
			       
			session.setAttribute("dashboard", "metadata");
			session.setAttribute("metadata", result_string); 

			rs.close();
			dbcon.close();
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/ResultDashBoard.jsp");
			dispatcher.forward(request, response);

		}
		catch (SQLException ex) {
			while (ex != null) {
					System.out.println ("SQL Exception:  " + ex.getMessage ());
					ex = ex.getNextException ();
			}  // end while
		}  // end catch SQLException

		catch(java.lang.Exception ex){
			out.println("<HTML>" +
						"<HEAD><TITLE>" +
						"MovieDB: Error" +
						"</TITLE></HEAD>\n<BODY>" +
						"<P>SQL error in doGet: " +
						ex.getMessage() + "</P></BODY></HTML>");
			return;
		}
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
    }
}