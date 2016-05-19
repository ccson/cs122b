package MyClasses;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Confirmation extends HttpServlet
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

			String first_name = request.getParameter("first_name");
			String last_name = request.getParameter("last_name");
			String credit_card = request.getParameter("credit_card");
			String exp_date = request.getParameter("exp_date");
			
			String query = "SELECT * from creditcards WHERE";
			boolean first = false;
			boolean exists = true;

		
			if(!first_name.equals("")){
				first = false;
				query += " first_name = '" + first_name + "'";
			}	
			else
				exists = false;
			if(!last_name.equals("")){
				if(first == false)
					query += " AND";
				else
					first = false;
				query += " last_name = '" + last_name + "'";
			}	
			else
				exists = false;		
			if(!credit_card.equals("")){
				if(first == false)
					query += " AND";
				else
					first = false;
				query += " id = '" + credit_card + "'";
			}			
			else
				exists = false;
			if(!exp_date.equals("")){
				if(first == false)
					query += " AND";
				else
					first = false;
				query += " expiration = '" + exp_date + "'";
			}	
			else
				exists = false;
								
			if(exists == true){
            // Perform the query
				exists = false;
				ResultSet rs = statement.executeQuery(query);
				
				while (rs.next())
				{
					exists = true;
				}
				rs.close();
			}
			HttpSession session = request.getSession();
			  
            statement.close();
            dbcon.close();
			
			if (exists){
				
				
				try{
					
					@SuppressWarnings("unchecked")
					HashMap<Integer, Integer> shoppingCart = (HashMap<Integer, Integer>)session.getAttribute("ShoppingCart"); 
					
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					
					for (Integer id : shoppingCart.keySet()){
						Integer result = shoppingCart.get(id);
						for (Integer i = 0; i < result; i++){
							
							Connection dbcon1 = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
							Statement statement1 = dbcon1.createStatement(); 
							String customerEmail = (String)session.getAttribute("customerEmail"); 
							String customerPassword = (String)session.getAttribute("customerPassword"); 
							int customerID = Movie.getCustomerIDFromEmailPassword(customerEmail, customerPassword); 
							
							String insert = "INSERT INTO sales (customer_id, movie_id, sale_date) VALUES ("  + customerID + ", " + id + ", " + "CURDATE())";
							
							statement1.executeUpdate(insert); 
							statement1.close(); 
							dbcon1.close(); 
							
						}
						
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
					
				}
				
				
				session.setAttribute("confirm", "succeed");
				
				session.setAttribute("ShoppingCart", null); 
				
			}
			else{
				session.setAttribute("confirm", "fail");
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/ConfirmResult.jsp");
			dispatcher.forward(request, response);
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