package MyClasses; 

import java.util.*; 
import java.sql.*;


public class Star {
	
	private int id; 
	private String firstName;
	private String lastName;  
	private String dob; 
	private String photoURL; 
	private ArrayList<Movie> movies;
	private String name; 
	
	public Star(int id, String firstName, String lastName){
		
		this.id = id; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.name = firstName + " " + lastName; 
		
	}
	
	public Star(int id, String firstName, String lastName, String dob, String photoURL, ArrayList<Movie> movies){
		
		this.id = id; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.dob = dob; 
		this.photoURL = photoURL; 
		this.movies = new ArrayList<Movie>(movies); 
		this.name = firstName + " " + lastName; 
		
	}
	
	public int getID() { return id; }
	public String getFirstName() { return firstName; }
	public String getLastName() { return lastName; }
	public String getDOB() { return dob; }
	public String getPhotoURL() { return photoURL; }
	public ArrayList<Movie> getMovies() { return movies; }
	public String getName() { return name; }
	
	
	public static ArrayList<Movie> getListOfMovies(int starID) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		ArrayList<Movie> listOfMovies = new ArrayList<Movie>(); 
		
		String loginUser = "root";
		String loginPasswd = "calmdude6994";
		String loginUrl = "jdbc:mysql://localhost:3306/moviedb";
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	    Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		String query = "SELECT DISTINCT m.id, m.title FROM stars s, movies m, stars_in_movies sim WHERE s.id = " + starID + " AND s.id = sim.star_id AND m.id = sim.movie_id"; 
		Statement statement = dbcon.createStatement(); 
		ResultSet result = statement.executeQuery(query); 
		
		while(result.next())
			listOfMovies.add(new Movie(result.getInt(1), result.getString(2))); 
		
		result.close();
		statement.close();
		dbcon.close();
		
		return listOfMovies; 
		
	}
	
}
