package XMLParser; 

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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class AllFourStar extends DefaultHandler{
	
	private StarInMovie tempStarInMovie; 
	
	private Connection dbcon; 
	
	private boolean bTitle = false; 
	private boolean bActor = false; 
	private boolean bFilmID = false; 
	
	private HashSet<StarInMovie> actorSet; 
	private HashSet<StarInMovie> newActorSet; 
	
	private HashSet<StarInMovie> starsInMoviesSet; 
	private HashSet<StarInMovie> newStarsInMoviesSet; 
	
	private HashMap<StarInMovie, Integer> actorMap;  
	private HashMap<StarInMovie, Integer> movieMap; 
	
	private String batchInsertStar; 
	private String batchInsertStarsInMovies; 
	
	private HashSet<StarInMovie> StarIDMovieID; 
	
	private int numberOfStarInserts = 0; 
	private int numberOfStarInMovieInserts = 0; 
	
    private String capitalize(String input){
    	
    	if (input.length() == 0){
    		
    		return input; 
    		
    	}
    	
    	String output = ""; 
    	String temp = ""; 
    	
    	String[] array = input.split(" "); 
    	
    	for (int i = 0; i < array.length; i++){
    		
    		temp = array[i].substring(0, 1).toUpperCase() + array[i].substring(1); 
    		output = output.concat(temp + " "); 
    		
    	}
    	
    	return output; 
    	
    }
    
	private void initializeHashSet() throws SQLException{
		
        Statement statement = dbcon.createStatement(); 
        
        String getAllStars = "SELECT * FROM stars; "; 
        ResultSet allStars = statement.executeQuery(getAllStars); 
        while (allStars.next()){
        	if (allStars.wasNull())
        		continue; 
        	actorSet.add(new StarInMovie(allStars.getString("first_name").toLowerCase().trim(), allStars.getString("last_name").toLowerCase().trim()));
        }
        allStars.close(); 
        
        String getAllStarsInMovies = "SELECT * FROM stars_in_movies; "; 
        ResultSet allStarsInMovies = statement.executeQuery(getAllStarsInMovies); 
        while (allStarsInMovies.next()){
        	if (allStarsInMovies.wasNull())
        		continue; 
        	StarIDMovieID.add(new StarInMovie(allStarsInMovies.getInt("star_id"), allStarsInMovies.getInt("movie_id")));
        }
        allStarsInMovies.close(); 
        
        statement.close(); 
		
	}
	
	private void initializeHashMaps(HashMap<GenreInMovie, Integer> movieMapFromMovieParser) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        
        for (GenreInMovie g : movieMapFromMovieParser.keySet()){
        	
        	if (g.getFilmID() != null)
        		movieMap.put(new StarInMovie(g.getTitle().toLowerCase().trim(), g.getFilmID().toLowerCase().trim(), 0), movieMapFromMovieParser.get(g)); 
        	else
        		movieMap.put(new StarInMovie(g.getTitle().toLowerCase().trim(), null, 0), movieMapFromMovieParser.get(g)); 
        	
        }
		
	}
	
	private void insertIntoBatch(StarInMovie tempStarInMovie){
		
		if (tempStarInMovie.getActorLastName() == null || tempStarInMovie.getActorLastName().equals(""))
			return; 
		
		if (!actorSet.contains(new StarInMovie(tempStarInMovie.getActorFirstName().toLowerCase().trim(), tempStarInMovie.getActorLastName().toLowerCase().trim()))
				&& !newActorSet.contains(new StarInMovie(tempStarInMovie.getActorFirstName().toLowerCase().trim(), tempStarInMovie.getActorLastName().toLowerCase().trim()))){
			
			System.out.println("Repeat Actor: First Name => " + tempStarInMovie.getActorFirstName() + " ----- Last Name => " + tempStarInMovie.getActorLastName());
			newActorSet.add(new StarInMovie(tempStarInMovie.getActorFirstName().toLowerCase().trim(), tempStarInMovie.getActorLastName().toLowerCase().trim())); 
			batchInsertStar = batchInsertStar.concat("(?, ?), "); 
			
		}
		
		if (tempStarInMovie.getMovieTitle() == null || tempStarInMovie.getFilmID() == null || tempStarInMovie.getMovieTitle().equals("") || tempStarInMovie.getFilmID().equals(""))
			return; 
		
		if (!starsInMoviesSet.contains(new StarInMovie(tempStarInMovie.getActorFirstName().toLowerCase().trim(), tempStarInMovie.getActorLastName().toLowerCase().trim(), tempStarInMovie.getMovieTitle().toLowerCase().trim(), tempStarInMovie.getFilmID().toLowerCase().trim()))){
			
			System.out.println("Repeat Star In Movie: First Name => " + tempStarInMovie.getActorFirstName() + " ----- Last Name => " + tempStarInMovie.getActorLastName() + " ----- Movie Title => " + tempStarInMovie.getMovieTitle() + " ----- Film ID => " + tempStarInMovie.getFilmID());
			starsInMoviesSet.add(new StarInMovie(tempStarInMovie.getActorFirstName().toLowerCase().trim(), tempStarInMovie.getActorLastName().toLowerCase().trim(), tempStarInMovie.getMovieTitle().toLowerCase().trim(), tempStarInMovie.getFilmID().toLowerCase().trim())); 
			
		}
			
		
	}
	
	private void insertIntoTable() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		batchInsertStar = batchInsertStar.substring(0, batchInsertStar.length() - 2);
		
		PreparedStatement insertStarStatement = dbcon.prepareStatement(batchInsertStar); 
		
		int insertStarIndex = 1; 
		
		for (StarInMovie actor : newActorSet){
			
			insertStarStatement.setString(insertStarIndex, capitalize(actor.getActorFirstName()));
			insertStarIndex++; 
			insertStarStatement.setString(insertStarIndex, capitalize(actor.getActorLastName()));
			insertStarIndex++; 
			
		}
		
		if (newActorSet.size() != 0){
			
			numberOfStarInserts = insertStarStatement.executeUpdate(); 
			
		}
		
		insertStarStatement.close(); 
		
		Statement statement = dbcon.createStatement(); 
        String getAllStars = "SELECT * FROM stars; "; 
        ResultSet allStars = statement.executeQuery(getAllStars); 
        while (allStars.next())
        	actorMap.put(new StarInMovie(allStars.getString("first_name").toLowerCase().trim(), allStars.getString("last_name").toLowerCase().trim()), allStars.getInt("id")); 
        allStars.close(); 
        
        for (StarInMovie starInMovie : starsInMoviesSet){
        	
        	int starID = actorMap.get(new StarInMovie(starInMovie.getActorFirstName().toLowerCase().trim(), starInMovie.getActorLastName().toLowerCase().trim()));
        	if (!movieMap.containsKey(new StarInMovie(starInMovie.getMovieTitle().toLowerCase().trim(), starInMovie.getFilmID().toLowerCase().trim(), 0))){
        		
        		System.out.println("Repeat Movie: Title => " + starInMovie.getMovieTitle() + " ----- Film ID => " + starInMovie.getFilmID());
        		continue; 
        		
        	}
        	int movieID = movieMap.get(new StarInMovie(starInMovie.getMovieTitle().toLowerCase().trim(), starInMovie.getFilmID().toLowerCase().trim(), 0)); 
        	
        	if (!StarIDMovieID.contains(new StarInMovie(starID, movieID)) && !newStarsInMoviesSet.contains(new StarInMovie(starID, movieID))){
        	
        		batchInsertStarsInMovies = batchInsertStarsInMovies.concat("(?, ?), "); 
        		newStarsInMoviesSet.add(new StarInMovie(starID, movieID)); 
        		
        	}
        	
        }
        
        batchInsertStarsInMovies = batchInsertStarsInMovies.substring(0, batchInsertStarsInMovies.length() - 2); 
        
        PreparedStatement insertStarsInMoviesStatement = dbcon.prepareStatement(batchInsertStarsInMovies); 
        
        int insertStarsInMoviesIndex = 1; 
        
        for (StarInMovie starinmovie : newStarsInMoviesSet){
        	
        	insertStarsInMoviesStatement.setInt(insertStarsInMoviesIndex, starinmovie.getStarID());
        	insertStarsInMoviesIndex++; 
        	insertStarsInMoviesStatement.setInt(insertStarsInMoviesIndex, starinmovie.getMovieID());
        	insertStarsInMoviesIndex++; 
        	
        }
        
        if (newStarsInMoviesSet.size() > 0){
        	
        	numberOfStarInMovieInserts = insertStarsInMoviesStatement.executeUpdate(); 
        	
        }
        
        insertStarsInMoviesStatement.close(); 
        
        statement.close(); 
        
	}
	
	public AllFourStar() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		actorSet = new HashSet<StarInMovie>(); 
		newActorSet = new HashSet<StarInMovie>(); 
		actorMap = new HashMap<StarInMovie, Integer>(); 
		movieMap = new HashMap<StarInMovie, Integer>(); 
		starsInMoviesSet = new HashSet<StarInMovie>(); 
		StarIDMovieID = new HashSet<StarInMovie>();
		newStarsInMoviesSet = new HashSet<StarInMovie>(); 
		
		batchInsertStar = "INSERT INTO stars (first_name, last_name) VALUES "; 
		batchInsertStarsInMovies = "INSERT INTO stars_in_movies (star_id, movie_id) VALUES "; 
		
	}
	
	public void runExample(HashMap<GenreInMovie, Integer> movieMapWithID, Connection dbcon) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		this.dbcon = dbcon; 
		
		initializeHashSet(); 
		initializeHashMaps(movieMapWithID); 
		parseDocument();
		insertIntoTable(); 
		
	}

	private void parseDocument() {
		
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			SAXParser sp = spf.newSAXParser();
			sp.parse("../casts124.xml", this);  
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
	
	public void warning(SAXParseException e){
		System.out.println("WARNING : " + e.getMessage());
	}

	public void error(SAXParseException e){
		System.out.println("ERROR : " + e.getMessage());
	}

	public void fatalError(SAXParseException e){
		System.out.println("FATAL : " + e.getMessage());
	}
	
	@Override
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
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if (bTitle){
			
			tempStarInMovie.setMovieTitle(new String(ch, start, length).trim());
			bTitle = false; 
			
		}
		
		else if (bActor){
			
			String nameArray[] = new String(ch, start, length).trim().split(" "); 
			if (nameArray.length == 2){
				
				tempStarInMovie.setActorFirstName(nameArray[0]);
				tempStarInMovie.setActorLastName(nameArray[1]);
				
			}
			
			else if (nameArray.length == 1){
				
				tempStarInMovie.setActorFirstName("");
				tempStarInMovie.setActorLastName(nameArray[0]);
				
			}
			
			else if (nameArray.length > 2){
				
				String firstName = ""; 
				
				tempStarInMovie.setActorLastName(nameArray[nameArray.length - 1]); 
				for (int i = 0; i < nameArray.length - 1; i++)
					firstName = firstName.concat(nameArray[i] + " "); 
				tempStarInMovie.setActorFirstName(firstName.trim());
				
			}
			
			bActor = false; 
			
		}
		
		else if (bFilmID){
			
			tempStarInMovie.setFilmID(new String(ch, start, length).trim());
			bFilmID = false; 
			
		}
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase("m")){
			
			insertIntoBatch(tempStarInMovie);
			tempStarInMovie = null; 
			
		}
		
	}
	
	public int getNumberOfStarInserts() { return numberOfStarInserts; }
	public int getNumberOfStarInMovieInserts() { return numberOfStarInMovieInserts; }
	

}
