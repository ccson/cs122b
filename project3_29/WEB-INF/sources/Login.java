package MyClasses; 

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
		HttpSession session = request.getSession();

        String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

        response.setContentType("text/html");    // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");


		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
		if (!valid) {
			                
			session.setAttribute("captchaCorrect", "false"); 
			response.sendRedirect(request.getContextPath());
			
		}
		else{
			session.setAttribute("captchaCorrect", "true"); 
			
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
				  
				if (rs.next()){
					String bool = "true"; 
					session.setAttribute("first_name", rs.getString("first_name"));
					session.setAttribute("last_name", rs.getString("last_name"));
					session.setAttribute("userLoggedIn", bool); 
					session.setAttribute("loginInfoCorrect", "true");
					session.setAttribute("customerEmail", email); 
					session.setAttribute("customerPassword", password); 
					main_page = true;
				}
				  
				  
				  // Iterate through each row of rs


				rs.close();
				statement.close();
				dbcon.close();
				
				if (main_page){
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/Main.jsp");
					dispatcher.forward(request, response);
				}
				else{
					
					session.setAttribute("userLoggedIn", "false"); 
					session.setAttribute("loginInfoCorrect", "false"); 
					response.sendRedirect(request.getContextPath());
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
		}
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	doGet(request, response);
    }
}