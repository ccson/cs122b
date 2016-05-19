package MyClasses; 

/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginDashBoard extends HttpServlet
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
			//Class.forName("org.gjt.mm.mysql.Driver");
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			// Declare our statement
			Statement statement = dbcon.createStatement();

			String email = request.getParameter("employeeemail");
			String password = request.getParameter("employeepw");
			
			if (email == null){
				email = "";
			}
			if (password == null){
				password = "";
			}
			String query = "SELECT * from employees where email = '" + email + "' AND password = '" + password + "'";

			// Perform the query
			ResultSet rs = statement.executeQuery(query);
			
			Boolean main_page = false;
			  
			if (rs.next()){
				String bool = "true"; 
				session.setAttribute("employeefull_name", rs.getString("fullname"));
				session.setAttribute("employeeLoggedIn", bool); 
				session.setAttribute("employeeloginInfoCorrect", "true");
				session.setAttribute("employeeEmail", email); 
				session.setAttribute("employeePassword", password); 
				main_page = true;
			}
			  

			rs.close();
			statement.close();
			dbcon.close();
			
			if (main_page){
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/MainDashBoard.jsp");
				dispatcher.forward(request, response);
			}
			else{
				session.setAttribute("employeeloggedIn", "false"); 
				session.setAttribute("employeeloginInfoCorrect", "false"); 
				response.sendRedirect(request.getContextPath() + "/_dashboard");
			}
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