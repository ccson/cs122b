package MyClasses; 

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddMovie extends HttpServlet
{
	
	private static boolean isValidNumber(String input){
		
		for (int i = 0; i < input.length(); i++){
			
			if (!Character.isDigit(input.charAt(i)))
				return false; 
			
		}
		
		return true; 
		
	}
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
	
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		
		try {

	        String loginUser = "root";
	        String loginPasswd = "calmdude6994";
	        String loginUrl = "jdbc:mysql://localhost:3306/moviedb?allowMultiQueries=true";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);

			String title = request.getParameter("title").trim(); 
			String year = request.getParameter("year").trim(); 
			String director = request.getParameter("director").trim(); 
			String genre = request.getParameter("genre"); 
			String newTitle = ""; 
			String newYear = ""; 
			String newDirector = ""; 
			String starFirstName = ""; 
			if (request.getParameter("starFirstName") == null || request.getParameter("starFirstName").equals(""))
				starFirstName = ""; 
			else
				starFirstName = request.getParameter("starFirstName").trim(); 
			String starLastName = ""; 
			if (request.getParameter("starLastName") == null || request.getParameter("starLastName").equals(""))
				starLastName = ""; 
			else
				starLastName = request.getParameter("starLastName").trim(); 
			
			if (genre != null)
				genre = genre.trim(); 
			
			if (!isValidNumber(year)){
				
				session.setAttribute("yearInvalid", "true"); 
				response.sendRedirect(request.getContextPath() + "/_dashboard/employee/main");
				
			}
			
			if (!starFirstName.equals("") && starLastName.equals("")){
				
				session.setAttribute("starLastNameInvalid", "true"); 
				response.sendRedirect(request.getContextPath() + "/_dashboard/employee/main");
				
			}
			
			Statement statement = dbcon.createStatement(); 
			statement.execute("SET @insert_movie_status := 0;"); 
			statement.execute("SET @insert_star_status := 0;"); 
			statement.execute("SET @insert_genre_status := 0;"); 
			statement.execute("SET @insert_star_in_movie_status := 0;"); 
			statement.execute("SET @insert_genre_in_movie_status := 0;"); 
			
			String addMovie = "{call add_movie(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}"; 
			CallableStatement callStatement = dbcon.prepareCall(addMovie); 
			callStatement.setString(1, title); 
			callStatement.setInt(2, Integer.parseInt(year)); 
			callStatement.setString(3, director); 
			
			if (genre == null || genre.equals(""))
				callStatement.setString(4, ""); 
			else
				callStatement.setString(4, genre); 
			
			if (starFirstName == null || starFirstName.equals(""))
				callStatement.setString(5, ""); 
			else
				callStatement.setString(5, starFirstName); 
			
			if (starLastName == null || starLastName.equals(""))
				callStatement.setString(6, ""); 
			else
				callStatement.setString(6, starLastName); 
			
			if (newTitle == null || newTitle.equals(""))
				callStatement.setString(7, ""); 
			else
				callStatement.setString(7, newTitle); 
				
			if (newYear == null || newYear.equals(""))
				callStatement.setInt(8, 0); 
			else
				callStatement.setInt(8, Integer.parseInt(newYear)); 
				
			if (newDirector == null || newDirector.equals(""))
				callStatement.setString(9, ""); 
			else
				callStatement.setString(9, newDirector); 
			
			callStatement.registerOutParameter("insert_movie_status", java.sql.Types.INTEGER);
			callStatement.registerOutParameter("insert_star_status", java.sql.Types.INTEGER);
			callStatement.registerOutParameter("insert_genre_status", java.sql.Types.INTEGER);
			callStatement.registerOutParameter("insert_star_in_movie_status", java.sql.Types.INTEGER);
			callStatement.registerOutParameter("insert_genre_in_movie_status", java.sql.Types.INTEGER);
			callStatement.registerOutParameter("update_movie_status", java.sql.Types.INTEGER);
			
			callStatement.setInt(10, 0); 
			callStatement.setInt(11, 0); 
			callStatement.setInt(12, 0); 
			callStatement.setInt(13, 0); 
			callStatement.setInt(14, 0); 
			callStatement.setInt(15, 0); 
		
			callStatement.execute(); 
			
			int insertMovieStatusInt = callStatement.getInt(10); 
			int insertStarStatusInt = callStatement.getInt(11); 
			int insertGenreStatusInt = callStatement.getInt(12); 
			int insertStarInMovieStatusInt = callStatement.getInt(13); 
			int insertGenreInMovieStatusInt = callStatement.getInt(14); 
			int updateMovieStatusInt = callStatement.getInt(15); 
			
			callStatement.close(); 
			statement.close(); 
			dbcon.close(); 
			
			session.setAttribute("procedureInsertMovie", Integer.toString(insertMovieStatusInt)); 
			session.setAttribute("procedureInsertStar", Integer.toString(insertStarStatusInt)); 
			session.setAttribute("procedureInsertGenre", Integer.toString(insertGenreStatusInt)); 
			session.setAttribute("procedureInsertStarInMovie", Integer.toString(insertStarInMovieStatusInt)); 
			session.setAttribute("procedureInsertGenreInMovie", Integer.toString(insertGenreInMovieStatusInt)); 
			session.setAttribute("procedureUpdateMovie", Integer.toString(updateMovieStatusInt)); 
			
			session.setAttribute("procedureTitle", title); 
			session.setAttribute("procedureYear", year); 
			session.setAttribute("procedureDirector", director); 
			session.setAttribute("procedureGenre", genre); 
			session.setAttribute("procedureStarFirstName", starFirstName);
			session.setAttribute("procedureStarLastName", starLastName); 
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/JSP/AddMovieDashboard.jsp");
			dispatcher.forward(request, response);
			
		}
		
		catch (SQLException ex) {
			
			session.setAttribute("yearInvalid", "true"); 
			response.sendRedirect(request.getContextPath() + "/_dashboard/employee/main");

		}

		catch(java.lang.Exception ex){
			
			session.setAttribute("yearInvalid", "true"); 
			response.sendRedirect(request.getContextPath() + "/_dashboard/employee/main");
			
		}
			
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
		doGet(request, response);
    }
}