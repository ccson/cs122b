package cs122b_3;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class SAXStarParser extends DefaultHandler{
	
	private StarInMovie tempStarInMovie; 
	
	private Connection dbcon; 
	
	private boolean bTitle = false; 
	private boolean bActor = false; 
	private boolean bFilmID = false; 
	
	HashMap<StarInMovie, Integer> actorMap;  
	HashMap<StarInMovie, Integer> movieMap; 
	HashSet<StarInMovie> starsInMoviesSet; 
	
	private void initializeHashMaps(HashMap<GenreInMovie, Integer> movieMapFromMovieParser) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        
        Statement statement = dbcon.createStatement(); 
        String getAllStars = "SELECT * FROM stars; "; 
        ResultSet allStars = statement.executeQuery(getAllStars); 
        while (allStars.next())
        	actorMap.put(new StarInMovie(allStars.getString("first_name").toLowerCase().trim(), allStars.getString("last_name").toLowerCase().trim()), allStars.getInt("id"));
        allStars.close();
        
        String getStarsInMovies = "SELECT * FROM stars_in_movies; "; 
        ResultSet allStarsInMovies = statement.executeQuery(getStarsInMovies); 
        while (allStarsInMovies.next())
        	starsInMoviesSet.add(new StarInMovie(allStarsInMovies.getInt("star_id"), allStarsInMovies.getInt("movie_id"))); 
        allStarsInMovies.close();
        
        statement.close(); 
        
        for (GenreInMovie g : movieMapFromMovieParser.keySet()){
        	
        	if (g.getFilmID() != null)
        		movieMap.put(new StarInMovie(g.getTitle().toLowerCase().trim(), g.getFilmID().toLowerCase().trim(), 0), movieMapFromMovieParser.get(g)); 
        	else
        		movieMap.put(new StarInMovie(g.getTitle().toLowerCase().trim(), null, 0), movieMapFromMovieParser.get(g)); 
        	
        }
		
	}
	
	private void insertIntoTable(StarInMovie tempStarInMovie) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		//System.out.println(tempStarInMovie.getActorFirstName() + " --- " + tempStarInMovie.getActorLastName() + " --- " + tempStarInMovie.getMovieTitle());
		
		if (tempStarInMovie.getActorFirstName() == null || tempStarInMovie.getActorLastName() == null || tempStarInMovie.getActorFirstName().equals("") || tempStarInMovie.getActorLastName().equals(""))
			return; 
        
        int insertStarStatus = 0; 
        if (!actorMap.containsKey(new StarInMovie(tempStarInMovie.getActorFirstName().toLowerCase().trim(), tempStarInMovie.getActorLastName().toLowerCase().trim()))){
        	
	        String insertStar = "INSERT INTO stars (first_name, last_name) VALUES (?, ?); "; 
	        PreparedStatement insertStarStatement = dbcon.prepareStatement(insertStar); 
	        insertStarStatement.setString(1, tempStarInMovie.getActorFirstName());
	        insertStarStatement.setString(2, tempStarInMovie.getActorLastName());
	        insertStarStatus = insertStarStatement.executeUpdate(); 
	        insertStarStatement.close(); 
        	
        }
        
        if (insertStarStatus > 0){
        	
        	String getLastKey = "SELECT LAST_INSERT_ID(); "; 
        	PreparedStatement getLastKeyStatement = dbcon.prepareStatement(getLastKey); 
        	ResultSet primaryKey = getLastKeyStatement.executeQuery(); 
        	primaryKey.next(); 
        	actorMap.put(new StarInMovie(tempStarInMovie.getActorFirstName().toLowerCase().trim(), tempStarInMovie.getActorLastName().toLowerCase().trim()), primaryKey.getInt(1)); 
        	primaryKey.close(); 
        	getLastKeyStatement.close(); 
        	
        }
		
        if (tempStarInMovie.getMovieTitle() == null || tempStarInMovie.getMovieTitle().equals("") || tempStarInMovie.getFilmID() == null || tempStarInMovie.getFilmID().equals("") || !movieMap.containsKey(new StarInMovie(tempStarInMovie.getMovieTitle().toLowerCase().trim(), tempStarInMovie.getFilmID().toLowerCase().trim(), 0))){
        	
        	return; 
        	
        }
        
        int starID = actorMap.get(new StarInMovie(tempStarInMovie.getActorFirstName().toLowerCase().trim(), tempStarInMovie.getActorLastName().toLowerCase().trim())); 
        int movieID = movieMap.get(new StarInMovie(tempStarInMovie.getMovieTitle().toLowerCase().trim(), tempStarInMovie.getFilmID().toLowerCase().trim(), 0)); 
        
        int insertStarInMovieStatus = 0; 
        if (!starsInMoviesSet.contains(new StarInMovie(starID, movieID))){
        	
	        String insertStarsInMovies = "INSERT INTO stars_in_movies (star_id, movie_id) VALUES (?, ?); "; 
	        PreparedStatement insertStarsInMoviesStatement = dbcon.prepareStatement(insertStarsInMovies); 
	        insertStarsInMoviesStatement.setInt(1, starID);
	        insertStarsInMoviesStatement.setInt(2, movieID);
	        insertStarInMovieStatus = insertStarsInMoviesStatement.executeUpdate(); 
	        insertStarsInMoviesStatement.close(); 
        	
        }
        
        if (insertStarInMovieStatus > 0){
        	
        	starsInMoviesSet.add(new StarInMovie(starID, movieID)); 
        	
        }
        
	}
	
	public SAXStarParser() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		actorMap = new HashMap<StarInMovie, Integer>(); 
		movieMap = new HashMap<StarInMovie, Integer>(); 
		starsInMoviesSet = new HashSet<StarInMovie>(); 
		
		String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql:///moviedb";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        dbcon.setAutoCommit(false);
		
	}
	
	public void runExample(HashMap<GenreInMovie, Integer> movieMapFromMovieParser) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		initializeHashMaps(movieMapFromMovieParser); 
		parseDocument();
		dbcon.commit();
		
	}

	private void parseDocument() {
		
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			SAXParser sp = spf.newSAXParser();
			sp.parse("./casts124.xml", this);  
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if (qName.equalsIgnoreCase("m"))
			tempStarInMovie = new StarInMovie(); 
		else if (qName.equalsIgnoreCase("t"))
			bTitle = true; 
		else if (qName.equalsIgnoreCase("a"))
			bActor = true; 
		else if (qName.equalsIgnoreCase("f"))
			bFilmID = true;
			
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if (bTitle){
			
			tempStarInMovie.setMovieTitle(new String(ch, start, length).trim());
			bTitle = false; 
			
		}
		
		else if (bActor){
			
			String name[] = new String(ch, start, length).trim().split(" "); 
			if (name.length == 2){
				
				tempStarInMovie.setActorFirstName(name[0]);
				tempStarInMovie.setActorLastName(name[1]);
				
			}
			
			bActor = false; 
			
		}
		
		else if (bFilmID){
			
			tempStarInMovie.setFilmID(new String(ch, start, length).trim());
			bFilmID = false; 
			
		}
		
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase("m")){
			
			try {
				insertIntoTable(tempStarInMovie);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			tempStarInMovie = null; 
			
		}
		
	}

}
