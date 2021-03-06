
/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Login extends HttpServlet
{
    public String getServletInfo()
    {
       return "Servlet connects to MySQL database and displays result of a SELECT";
    }

    // Use http GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        String loginUser = "root";
        String loginPasswd = "CHANGE";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        //out.println("<HTML><HEAD><TITLE>Movies</TITLE></HEAD>");
        //out.println("<BODY><H1>Results</H1>");


        try
        {
            //Class.forName("org.gjt.mm.mysql.Driver");
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
            // Declare our statement
            Statement statement = dbcon.createStatement();

			String email = request.getParameter("email");
			String password = request.getParameter("pw");
			
			if (email == null){
				email = "";
			}
			if (password == null){
				password = "";
			}
            String query = "SELECT * from customers where email = '" + email + "' AND password = '" + password + "'";

            // Perform the query
            ResultSet rs = statement.executeQuery(query);
			
			Boolean main_page = false;
			HttpSession session = request.getSession();
			  
			if (rs.next()){
				session.setAttribute("first_name", rs.getString("first_name"));
				session.setAttribute("last_name", rs.getString("last_name"));
				main_page = true;
			}
			  
			  
              // Iterate through each row of rs


            rs.close();
            statement.close();
            dbcon.close();
			
			if (main_page){
				RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
				dispatcher.forward(request, response);
			}
			else{
				response.sendRedirect("http://localhost:8080/Login/");
			}
		}
        catch (SQLException ex) {
            while (ex != null) {
                    System.out.println ("SQL Exception:  " + ex.getMessage ());
                    ex = ex.getNextException ();
                }  // end while
            }  // end catch SQLException

        catch(java.lang.Exception ex)
            {
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