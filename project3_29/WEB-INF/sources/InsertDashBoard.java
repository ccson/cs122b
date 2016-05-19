package MyClasses; 

/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class InsertDashBoard extends HttpServlet
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
			String updateString;
			boolean valid = true;
	
		    String firstName = request.getParameter("star_firstname");
	        String lastName = request.getParameter("star_lastname");
	        String date = request.getParameter("star_dob");
	        String photoURL = request.getParameter("star_url");
			
			String first_part = "INSERT INTO stars(first_name, last_name";
			String second_part = " VALUES(\'" + firstName + "\', \'" + lastName + "\'";
			String full_name;
			
			if(firstName.equals(""))
				full_name = lastName;
			else
				full_name = firstName + " " + lastName;
			if(date.equals(""))
				date = "null";
			else{
				first_part += ", dob";
				second_part += ", \'" + date + "\'";
			}
			if(photoURL.equals(""))
				photoURL = "null";
			else{
				first_part += ", photo_url";
				second_part += ", \'" + photoURL + "\'";
			}
			
			first_part += ") ";
			second_part += ")";
			
			updateString = first_part + second_part;
					
			//out.println(updateString);
			
			PreparedStatement updateStatement = dbcon.prepareStatement(updateString);
			updateStatement.executeUpdate();
			updateStatement.close(); 
		
			session.setAttribute("dashboard", "insert");
			session.setAttribute("star_insert", full_name); 

			rs.close();
			dbcon.close();
			
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/ResultDashBoard.jsp");
			dispatcher.forward(request, response);
			
		}
		catch (SQLException ex) {
			session.setAttribute("sql_error", "Please enter date of birth with correct formatting");
			response.sendRedirect(request.getContextPath() + "/_dashboard/employee/main");
			
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