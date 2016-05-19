package MyClasses; 

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import com.google.gson.*;

public class AutoComplete extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {


		String loginUser = "root";
		String loginPasswd = "calmdude6994";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";

		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession();
		
		Connection dbcon = null; 
		PreparedStatement preparedStatement = null; 
		ResultSet rs = null; 

		try{
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			
			String input = req.getParameter("input");
			
			String query = "SELECT * FROM movies WHERE MATCH (title) AGAINST (? IN BOOLEAN MODE); ";
			String[] inputParsed = input.split(" "); 
			String titleQuery = ""; 
			
			for (int i = 0; i < inputParsed.length; i++){
				
				titleQuery = titleQuery.concat("+" + inputParsed[i]); 
				
				if (i == inputParsed.length - 1)
					titleQuery = titleQuery.concat("*"); 
				
			}
			
			preparedStatement = dbcon.prepareStatement(query);
			preparedStatement.setString(1, titleQuery); 
			
			rs = preparedStatement.executeQuery();
			
			ArrayList<String> result = new ArrayList<String>(); 
			  
			while(rs.next())
				result.add(rs.getString("title").trim()); 
			
			String json = new Gson().toJson(result);
			
			out.println(json);
			
			out.close(); 
			rs.close(); 
			preparedStatement.close(); 
			dbcon.close(); 
			
		}
		
	

		catch(java.lang.Exception ex){
			out.println("<HTML>" +
						"<HEAD><TITLE>" +
						"MovieDB: Error" +
						"</TITLE></HEAD>\n<BODY>" +
						"<P>SQL error in doGet: " +
						ex.getMessage() + "</P></BODY></HTML>");
			return;
		}
		
		finally {
			
			if (out != null)
				out.close(); 
				
			try { rs.close(); } catch (Exception e) {}
			try { preparedStatement.close(); } catch (Exception e) {}
			try { dbcon.close(); } catch (Exception e) {}
			
		}
		
			
	}
}
