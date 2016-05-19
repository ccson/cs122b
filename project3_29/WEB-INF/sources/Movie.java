package MyClasses; 

import java.sql.*;
import java.util.*; 

public class Movie {
	
	private int id; 
	private String title; 
	private int year; 
	private String director;
	private String bannerURL; 
	private String trailerURL; 
	private ArrayList<Genre> genres; 
	private ArrayList<Star> stars; 
	
	public Movie(int id, String title){
		
		this.id = id; 
		this.title = title; 
		
	}
	
	public Movie(int id, String title, int year, String director, ArrayList<Genre> genres, ArrayList<Star> stars){
		
		this.id = id; 
		this.title = title; 
		this.year = year; 
		this.director = director; 
		this.genres = new ArrayList<Genre>(genres); 
		this.stars = new ArrayList<Star>(stars); 
		
	}
	
	public Movie(int id, String title, int year, String director, ArrayList<Genre> genres, ArrayList<Star> stars, String bannerURL, String trailerURL){
		
		this.id = id; 
		this.title = title; 
		this.year = year; 
		this.director = director; 
		this.genres = new ArrayList<Genre>(genres); 
		this.stars = new ArrayList<Star>(stars); 
		this.bannerURL = bannerURL; 
		this.trailerURL = trailerURL; 
		
	}
	
	public int getID() { return id; } 
	public String getTitle() { return title; }
	public int getYear() { return year; }
	public String getDirector() { return director; }
	public ArrayList<Genre> getGenres() { return genres; }
	public ArrayList<Star> getStars() { return stars; }
	public String getBannerURL() { return bannerURL; }
	public String getTrailerURL() { return trailerURL; }
	
	
	
	public static ArrayList<Star> getListOfStars(int movieID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		ArrayList<Star> listOfStars = new ArrayList<Star>(); 
		
		String loginUser = "root";
		String loginPasswd = "calmdude6994";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		String query = "SELECT DISTINCT s.id, s.first_name, s.last_name FROM stars s, stars_in_movies sim, movies m WHERE m.id = " + movieID + " AND s.id = sim.star_id AND m.id = sim.movie_id"; 
		Statement select = dbcon.createStatement(); 
		ResultSet result = select.executeQuery(query); 
		
		while(result.next())
			listOfStars.add(new Star(result.getInt(1), result.getString(2), result.getString(3))); 
		
		result.close(); 
		select.close(); 
		dbcon.close(); 
		
		return listOfStars; 
		
	}
	
	public static ArrayList<Genre> getListOfGenres(int movieID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		ArrayList<Genre> listOfGenres = new ArrayList<Genre>();
		
		String loginUser = "root";
		String loginPasswd = "calmdude6994";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		String query = "SELECT DISTINCT g.id, g.name FROM movies m, genres g, genres_in_movies gim WHERE m.id = " + movieID + " AND g.id = gim.genre_id AND m.id = gim.movie_id"; 
		Statement statement = dbcon.createStatement(); 
		ResultSet result = statement.executeQuery(query); 
		
		while(result.next())
			listOfGenres.add(new Genre(result.getInt(1), result.getString(2))); 
			
		result.close();
		statement.close();
		dbcon.close();
		
		return listOfGenres; 
		
	}
	
	public static String getTitleFromID(int movieID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
			
		String loginUser = "root";
		String loginPasswd = "calmdude6994";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		String query = "SELECT title FROM movies WHERE id = " + movieID; 
		Statement statement = dbcon.createStatement(); 
		ResultSet result = statement.executeQuery(query); 
		
		result.next(); 
		String movieTitle = result.getString("title"); 
			
		result.close();
		statement.close();
		dbcon.close();
		
		return movieTitle; 
		
	}
	
	public static int getCustomerIDFromEmailPassword(String email, String password) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		String loginUser = "root";
		String loginPasswd = "calmdude6994";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		String query = "SELECT id FROM customers WHERE email = '" + email + "' AND password = '" + password + "'"; 
		Statement statement = dbcon.createStatement(); 
		ResultSet result = statement.executeQuery(query); 
		
		result.next(); 
		int customerID = result.getInt("id"); 
			
		result.close();
		statement.close();
		dbcon.close();
		
		return customerID; 
		
	}
	
}

