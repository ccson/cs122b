package MyClasses; 

import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.*; 

public class MovieList extends HttpServlet
{
    
    private static boolean isValidNumber(String input){
        
        for (int i = 0; i < input.length(); i++){
            
            if (!Character.isDigit(input.charAt(i)))
                return false; 
            
        }
        
        return true; 
        
    }
    
    private ArrayList<Movie> developQuerySearch(HttpServletRequest request, HttpServletResponse response, String sortMethod, int amountPerPage, int pageNumber) throws ServletException, IOException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        
        ArrayList<Movie> movieList = new ArrayList<Movie>(); 
		HttpSession session = request.getSession();

        String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        Statement statement = dbcon.createStatement(); 
        
        String title; 
        String year; 
        String director; 
        String firstName; 
        String lastName; 
        String sort = ""; 
        String limit = " LIMIT " + amountPerPage + " OFFSET " + ((pageNumber - 1) * amountPerPage); 
		String first_part = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m";
		String second_part = " WHERE";
		boolean and = false;
		boolean empty = true;

        if (sortMethod.equals("TitleAscending"))
            sort = " ORDER BY m.title ASC"; 
        else if (sortMethod.equals("TitleDescending"))
            sort = " ORDER BY m.title DESC"; 
        else if (sortMethod.equals("YearAscending"))
            sort = " ORDER BY m.year ASC"; 
        else if (sortMethod.equals("YearDescending"))
            sort = " ORDER BY m.year DESC"; 
        
        if (request.getParameter("title") != null && !request.getParameter("title").equals("")){
            title = "\"%" + request.getParameter("title") + "%\"";
			and = true;
			second_part += " m.title LIKE " + title;
			empty = false;
		}
        else
            title = "\"%\"";        
        if (request.getParameter("year") != null && !request.getParameter("year").equals("")){
            year = "\"" + request.getParameter("year") + "\""; 
			if(and)
				second_part += " AND";
			else
				and = true;
			second_part += " CAST(m.year AS CHAR(20)) LIKE " + year;
			empty = false;
		}
        else
            year = "\"%\"";         
        if (request.getParameter("director") != null && !request.getParameter("director").equals("")){
            director = "\"%" + request.getParameter("director") + "%\"";
			if(and)
				second_part += " AND";
			else
				and = true;
			second_part += " m.director LIKE " + director;
			empty = false;
		}
        else
            director = "\"%\"";            
        if (request.getParameter("firstName") != null && !request.getParameter("firstName").equals("")){
			first_part = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m, stars s, stars_in_movies sim";
            firstName = "\"%" + request.getParameter("firstName") + "%\"";
			if(and)
				second_part += " AND";
			else
				and = true;
			second_part += " m.id = sim.movie_id AND s.id = sim.star_id AND s.first_name LIKE " + firstName;
			empty = false;
		}
        else
            firstName = "\"%\""; 
        if (request.getParameter("lastName") != null && !request.getParameter("lastName").equals("")){
			first_part = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m, stars s, stars_in_movies sim";
            lastName = "\"%" + request.getParameter("lastName") + "%\"";
			if(and)
				second_part += " AND";
			else
				and = true;
			second_part += " m.id = sim.movie_id AND s.id = sim.star_id AND s.last_name LIKE " + lastName;
			empty = false;
		}
        else
            lastName = "\"%\"";
            
		String query = first_part + second_part + sort + limit;
		//String query = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m, stars s, stars_in_movies sim WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.title " + " LIKE " + title + " AND CAST(m.year AS CHAR(20)) LIKE " + year + " AND " + " m.director LIKE " + director + " AND s.first_name LIKE " + firstName + " AND s.last_name LIKE " + lastName + sort + limit; 	
		if(empty){
  	        session.setAttribute("empty_search", true); 
			RequestDispatcher RequestDispatcherObj = request.getRequestDispatcher("/WEB-INF/JSP/Search.jsp");
    	    RequestDispatcherObj.forward(request, response);	
		}
		
        ResultSet result = statement.executeQuery(query); 
        
        while (result.next())
            movieList.add(new Movie(result.getInt("id"), result.getString("title"), result.getInt("year"), result.getString("director"), Movie.getListOfGenres(result.getInt("id")), Movie.getListOfStars(result.getInt("id")))); 
        
        result.close(); 
        statement.close(); 
        dbcon.close(); 
        
        return movieList; 
        
    }
    
    private ArrayList<Movie> developQueryBrowse(HttpServletRequest request, String sortMethod, int amountPerPage, int pageNumber) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        
        ArrayList<Movie> movieList = new ArrayList<Movie>(); 
        
        String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        Statement statement = dbcon.createStatement(); 
        
        String title; 
        String genre; 
        String sort = ""; 
        String limit = " LIMIT " + amountPerPage + " OFFSET " + ((pageNumber - 1) * amountPerPage); 
		String first_part = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m";
		String second_part = " WHERE";
		boolean and = false;
		
        
        if (request.getParameter("title") != null && !request.getParameter("title").equals("")){
            title = "\"" + request.getParameter("title") + "%\"";
			and = true;
			second_part += " m.title LIKE " + title;
		}
        
        if (request.getParameter("genre") != null && !request.getParameter("genre").equals("")){
			first_part = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m, genres g, genres_in_movies gim";
            genre = "\"" + request.getParameter("genre") + "\"";
			if(and){
				second_part += " AND";
			}
			else
				and = true;
			second_part += " gim.movie_id AND g.id = gim.genre_id AND CAST(g.id AS CHAR(20)) LIKE " + genre;
		}
            
        if (sortMethod.equals("TitleAscending"))
            sort = " ORDER BY m.title ASC"; 
        else if (sortMethod.equals("TitleDescending"))
            sort = " ORDER BY m.title DESC"; 
        else if (sortMethod.equals("YearAscending"))
            sort = " ORDER BY m.year ASC"; 
        else if (sortMethod.equals("YearDescending"))
            sort = " ORDER BY m.year DESC"; 
            
		String query = first_part + second_part + sort + limit;
			
        ResultSet result = statement.executeQuery(query); 
        
        while (result.next())
            movieList.add(new Movie(result.getInt("id"), result.getString("title"), result.getInt("year"), result.getString("director"), Movie.getListOfGenres(result.getInt("id")), Movie.getListOfStars(result.getInt("id")))); 
        
        result.close(); 
        statement.close(); 
        dbcon.close(); 
        
        return movieList; 
        
    }
    
    private int getCorpusSizeBrowse(HttpServletRequest request) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        
        String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        Statement statement = dbcon.createStatement(); 
        
        String title; 
        String genre; 
		String first_part = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m";
		String second_part = " WHERE";
		boolean and = false;
		
        
        if (request.getParameter("title") != null && !request.getParameter("title").equals("")){
            title = "\"" + request.getParameter("title") + "%\"";
			and = true;
			second_part += " m.title LIKE " + title;
		}
        
        if (request.getParameter("genre") != null && !request.getParameter("genre").equals("")){
			first_part = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m, genres g, genres_in_movies gim";
            genre = "\"" + request.getParameter("genre") + "\"";
			if(and){
				second_part += " AND";
			}
			else
				and = true;
			second_part += "  gim.movie_id AND g.id = gim.genre_id AND CAST(g.id AS CHAR(20)) LIKE " + genre;
		}
            
		String query = first_part + second_part;
		
        ResultSet result = statement.executeQuery(query); 
        
        int size = 0; 
        
        if (result != null){
            
            result.last(); 
            size = result.getRow(); 
            
        }
        
        result.close(); 
        statement.close(); 
        dbcon.close(); 
        
        return size; 
        
    }
    
    private int getCorpusSizeSearch(HttpServletRequest request) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{

        String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        Statement statement = dbcon.createStatement(); 
        
        String title; 
        String year; 
        String director; 
        String firstName; 
        String lastName; 
		String first_part = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m";
		String second_part = " WHERE";
		boolean and = false;
        
        if (request.getParameter("title") != null && !request.getParameter("title").equals("")){
            title = "\"%" + request.getParameter("title") + "%\"";
			and = true;
			second_part += " m.title LIKE " + title;
		}
        else
            title = "\"%\"";        
        if (request.getParameter("year") != null && !request.getParameter("year").equals("")){
            year = "\"" + request.getParameter("year") + "\""; 
			if(and)
				second_part += " AND";
			else
				and = true;
			second_part += " CAST(m.year AS CHAR(20)) LIKE " + year;
		}
        else
            year = "\"%\"";         
        if (request.getParameter("director") != null && !request.getParameter("director").equals("")){
            director = "\"%" + request.getParameter("director") + "%\"";
			if(and)
				second_part += " AND";
			else
				and = true;
			second_part += " m.director LIKE " + director;
		}
        else
            director = "\"%\"";            
        if (request.getParameter("firstName") != null && !request.getParameter("firstName").equals("")){
			first_part = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m, stars s, stars_in_movies sim";
            firstName = "\"%" + request.getParameter("firstName") + "%\"";
			if(and)
				second_part += " AND";
			else
				and = true;
			second_part += " m.id = sim.movie_id AND s.id = sim.star_id AND s.first_name LIKE " + firstName;
		}
        else
            firstName = "\"%\""; 
        if (request.getParameter("lastName") != null && !request.getParameter("lastName").equals("")){
			first_part = "SELECT DISTINCT m.id, m.title, m.year, m.director FROM movies m, stars s, stars_in_movies sim";
            lastName = "\"%" + request.getParameter("lastName") + "%\"";
			if(and)
				second_part += " AND";
			else
				and = true;
			second_part += " m.id = sim.movie_id AND s.id = sim.star_id AND s.last_name LIKE " + lastName;
		}
        else
            lastName = "\"%\"";
            
		String query = first_part + second_part;
				
        ResultSet result = statement.executeQuery(query); 
        
        int size = 0; 
        
        if (result != null){
            
            result.last(); 
            size = result.getRow(); 
            
        }
        
        result.close(); 
        statement.close(); 
        dbcon.close(); 
        
        return size; 
        
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        String sortMethod = "None"; 
        int amountPerPage = 5; 
        int pageNumber = 1; 
        int corpusSize = 0; 

        	try {
    	        	
    	        	ArrayList<Movie> movieList; 
    
            if (request.getParameter("year") != null && request.getParameter("year") != "" && !isValidNumber(request.getParameter("year"))){
                
                String error = "yearNumberError"; 
                session.setAttribute("errorPage", error); 
                RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/WEB-INF/JSP/ErrorPage.jsp");
                RequetsDispatcherObj.forward(request, response);
                
            }
            
            if (request.getParameter("amountPerPage") != null && request.getParameter("amountPerPage") != "" && !isValidNumber(request.getParameter("amountPerPage"))){
                
                String error = "amountPerPageNumberError"; 
                session.setAttribute("errorPage", error); 
                RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/WEB-INF/JSP/ErrorPage.jsp");
                RequetsDispatcherObj.forward(request, response);
                
            }
            
            if (request.getParameter("pageNumber") != null && request.getParameter("pageNumber") != "" && !isValidNumber(request.getParameter("pageNumber"))){
                
                String error = "pageNumberError"; 
                session.setAttribute("errorPage", error); 
                RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/WEB-INF/JSP/ErrorPage.jsp");
                RequetsDispatcherObj.forward(request, response);
                
            }
    
            if (request.getParameter("sort") != null){
                	         
    	            if (request.getParameter("sort").equals("TitleAscending"))
    	                sortMethod = "TitleAscending"; 
    	            else if (request.getParameter("sort").equals("TitleDescending"))
    	                sortMethod = "TitleDescending"; 
    	            else if (request.getParameter("sort").equals("YearAscending"))
    	                sortMethod = "YearAscending";
    	            else if (request.getParameter("sort").equals("YearDescending"))
    	                sortMethod = "YearDescending";
    	                
    	        }
    
                
            if (request.getParameter("amountPerPage") != null && request.getParameter("pageNumber") != null){
                
                if (request.getParameter("amountPerPage").equals("10"))
                    amountPerPage = 10;    
                else if (request.getParameter("amountPerPage").equals("25"))
                    amountPerPage = 25;  
                else if (request.getParameter("amountPerPage").equals("50"))
                    amountPerPage = 50;  
                else if (request.getParameter("amountPerPage").equals("100"))
                    amountPerPage = 100;  
                    
                pageNumber = Integer.parseInt(request.getParameter("pageNumber")); 
            
            }
            
    
            if (request.getParameter("type") != null){
    	        
        	        if (request.getParameter("type").equals("search")){
        	            movieList = developQuerySearch(request, response, sortMethod, amountPerPage, pageNumber); 
						corpusSize = getCorpusSizeSearch(request); 
                }
                
        	        else {
        	            movieList = developQueryBrowse(request, sortMethod, amountPerPage, pageNumber); 
						corpusSize = getCorpusSizeBrowse(request); 
                }
                
            }
            
            else
                movieList = new ArrayList<Movie>(); 
    	
    	        session.setAttribute("movieList", movieList); 
				session.setAttribute("corpusSize", corpusSize); 
    	
    	        RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/WEB-INF/JSP/movieList.jsp");
    	        RequetsDispatcherObj.forward(request, response);
    
    	         
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
        
        out.close(); 
            
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
     
        doGet(request, response); 
       
    }
	
}