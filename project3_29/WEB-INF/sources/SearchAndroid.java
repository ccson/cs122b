package MyClasses; 

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.*; 
import com.google.gson.*;

public class SearchAndroid extends HttpServlet
{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		Connection dbcon = null; 
		ResultSet result = null; 
		ResultSet resultSize = null; 
		PreparedStatement titleQueryStatement = null; 
		PreparedStatement sizeTitleQueryStatement = null; 

		try {
		        	
			ArrayList<Movie> movieList = new ArrayList<Movie>(); 
		
			String loginUser = "root";
			String loginPasswd = "calmdude6994";
			String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			
			String title = request.getParameter("title"); 
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber")); 
			
			if (title != null && !title.equals("")){
				
				String[] queryParsed = title.trim().split(" "); 
				String fullTextQuery = ""; 
				
				for (int i = 0; i < queryParsed.length; i++){
					
					if (i == queryParsed.length - 1){
						
						fullTextQuery = fullTextQuery.concat("+" + queryParsed[i]); 
						fullTextQuery = fullTextQuery.concat("*"); 
						
					}
					
					else 
						fullTextQuery = fullTextQuery.concat("+" + queryParsed[i] + " "); 
					
				}
			
				String titleQuery = "SELECT * FROM movies WHERE MATCH (title) AGAINST (? IN BOOLEAN MODE) LIMIT 5 OFFSET ?; "; 
				titleQueryStatement = dbcon.prepareStatement(titleQuery);
				titleQueryStatement.setString(1, fullTextQuery); 
				titleQueryStatement.setInt(2, (pageNumber - 1) * 5); 
				result = titleQueryStatement.executeQuery();
				
				while (result.next())
					movieList.add(new Movie(result.getInt("id"), result.getString("title"), result.getInt("year"), result.getString("director"), Movie.getListOfGenres(result.getInt("id")), Movie.getListOfStars(result.getInt("id")), result.getString("banner_url"), result.getString("trailer_url"))); 
				
				result.close(); 
				titleQueryStatement.close(); 
				
				int size = 0; 
				String sizeTitleQuery = "SELECT * FROM movies WHERE MATCH (title) AGAINST (? IN BOOLEAN MODE); ";
				sizeTitleQueryStatement = dbcon.prepareStatement(sizeTitleQuery); 
				sizeTitleQueryStatement.setString(1, fullTextQuery); 
				resultSize = sizeTitleQueryStatement.executeQuery();  
				
				if (resultSize != null){
					
					resultSize.last(); 
					size = resultSize.getRow(); 
					
				}
				
				AndroidClass androidMovieList = new AndroidClass(movieList, size, title, pageNumber); 
				
				String listOfMoviesJson = new Gson().toJson(androidMovieList); 
				out.println(listOfMoviesJson); 
					
				resultSize.close(); 
				sizeTitleQueryStatement.close(); 
				dbcon.close(); 
				out.close(); 
				
			}
			
			else{
				
				AndroidClass emptyAndroidMovieList = new AndroidClass(new ArrayList<Movie>(), 0, request.getParameter("title"), 1); 
				String listOfMoviesJson = new Gson().toJson(emptyAndroidMovieList); 
				out.println(listOfMoviesJson); 
				
			}
			
			dbcon.close(); 
			out.close(); 
		         
		}
			
		catch (SQLException ex) {
			
			while (ex != null) {
				out.println("SQL Exception: " + ex.getMessage());
				ex = ex.getNextException();
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
		
		finally {
			
			if (out != null)
				out.close(); 
				
			try { result.close(); } catch (Exception e) {}
			try { resultSize.close(); } catch (Exception e) {}
			try { titleQueryStatement.close(); } catch (Exception e) {}
			try { dbcon.close(); } catch (Exception e) {}
			
		}
			
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException
	{
	 
		doGet(request, response); 
	   
	}
	
}