import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MovieList extends HttpServlet
{
    
    private static class compareMoviesTitleAscending implements Comparator<Movie>{
        
        public int compare(Movie m1, Movie m2){
            
            return m2.getTitle().compareTo(m1.getTitle()); 
            
        }
        
    }
    
    private static class compareMoviesTitleDescending implements Comparator<Movie>{
        
        public int compare(Movie m1, Movie m2){
            
            return m1.getTitle().compareTo(m2.getTitle());
            
        }
        
    }
    
    private static class compareMoviesYearDescending implements Comparator<Movie>{
        
        public int compare(Movie m1, Movie m2){
            
            return m1.getYear() - m2.getYear(); 
            
        }
        
    }
    
    private static class compareMoviesYearAscending implements Comparator<Movie>{
        
        public int compare(Movie m1, Movie m2){
            
            return m2.getYear() - m1.getYear(); 
            
        }
        
    }

    private ArrayList<Movie> developQuerySearch(HttpServletRequest request) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        
        ArrayList<Movie> movieList = new ArrayList<Movie>(); 
        
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
        
        if (request.getParameter("title") != null)
            title = "\"%" + request.getAttribute("title") + "%\""; 
        else
            title = "\"%\"";
        
        if (request.getParameter("year") != null)
            year = "\"" + request.getAttribute("year") + "\""; 
        else
            year = "\"%\""; 
        
        if (request.getParameter("director") != null)
            director = "\"%" + request.getAttribute("director") + "%\"";
        else
            director = "\"%\""; 
            
        if (request.getParameter("firstName") != null)
            firstName = "\"%" + request.getAttribute("firstName") + "%\"";
        else
            firstName = "\"%\""; 
            
        if (request.getParameter("lastName") != null)
            lastName = "\"%" + request.getAttribute("lastName") + "%\"";
        else
            lastName = "\"%\"";
            
        String query = "SELECT m.id, m.title, m.year, m.director FROM movies m, stars s, stars_in_movies sim WHERE m.id = sim.movie_id AND s.id = sim.star_id AND m.title "
        + "LIKE" + title + " AND CAST(m.year AS VARCHAR(20)) LIKE" + year + "AND "
        + "m.director LIKE" + director + "AND s.first_name LIKE" + firstName + "AND s.last_name LIKE" + lastName ; 
        
        ResultSet result = statement.executeQuery(query); 
        
        while (result.next())
            movieList.add(new Movie(result.getInt("id"), result.getString("title"), result.getInt("year"), result.getString("director"), Movie.getListOfGenres(result.getInt("id")), Movie.getListOfStars(result.getInt("id")))); 
        
        result.close(); 
        statement.close(); 
        dbcon.close(); 
        
        return movieList; 
        
    }
    
    private ArrayList<Movie> developQueryBrowse(HttpServletRequest request) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
        
        ArrayList<Movie> movieList = new ArrayList<Movie>(); 
        
        String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        Statement statement = dbcon.createStatement(); 
        
        String title; 
        String genre; 
        
        if (request.getParameter("title") != null)
            title = "\"" + request.getAttribute("title") + "%\"";
        else
            title = "\"%\""; 
        
        if (request.getParameter("genre") != null)
            genre = "\"" + request.getAttribute("genre") + "\"";
        else
            genre = "\"%\""; 
            
        String query = "SELECT * FROM movies m, genres g, genres_in_movies gim WHERE m.id = gim.movie_id AND g.id = gim.genre_id AND m.title LIKE" + title + "AND g.id LIKE " + genre; 
            
        ResultSet result = statement.executeQuery(query); 
        
        while (result.next())
            movieList.add(new Movie(result.getInt("id"), result.getString("title"), result.getInt("year"), result.getString("director"), Movie.getListOfGenres(result.getInt("id")), Movie.getListOfStars(result.getInt("id")))); 
        
        result.close(); 
        statement.close(); 
        dbcon.close(); 
        
        return movieList; 
        
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        	HttpSession session = request.getSession(true);

        	try {
    	        	
    	        	ArrayList<Movie> movieList; 
    
            if (request.getAttribute("type") != null){
    	        
        	        if (request.getAttribute("type").equals("search"))
        	            movieList = developQuerySearch(request); 
        	        else
        	            movieList = developQueryBrowse(request); 
            }
            
            else
                movieList = new ArrayList<Movie>(); 
    	            
    	        if (request.getAttribute("sort") != null){
    	         
    	            if (request.getAttribute("sort").equals("TitleAscending"))
    	                Collections.sort(movieList, new compareMoviesTitleAscending()); 
    	            else if (request.getAttribute("sort").equals("TitleDescending"))
    	                Collections.sort(movieList, new compareMoviesTitleDescending()); 
    	            else if (request.getAttribute("sort").equals("YearAscending"))
    	                Collections.sort(movieList, new compareMoviesYearAscending()); 
    	            else if (request.getAttribute("sort").equals("YearDescending"))
    	                Collections.sort(movieList, new compareMoviesYearDescending()); 
    	                
    	         }
    	
    	         session.setAttribute("movieList", movieList); 
    	
    	         RequestDispatcher RequetsDispatcherObj = request.getRequestDispatcher("/movielist/result");
    	         RequetsDispatcherObj.forward(request, response);
    
    	         
        	}
        	
        catch (SQLException ex) {
            
            while (ex != null) {
                System.out.println ("SQL Exception:  " + ex.getMessage ());
                ex = ex.getNextException ();
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
}