package XMLParser;

import java.io.File;
import java.io.IOException;
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
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.*; 

public class AllFourMovie extends DefaultHandler{
	
	private boolean bFilm = false; 
	private boolean bTitle = false; 
	private boolean bYear = false; 
	private boolean bDirector = false; 
	private boolean bGenre = false; 
	private boolean bFilmID = false; 
	
	private Connection dbcon; 
	
	private GenreInMovie tempGenreInMovie; 
	
	private HashSet<GenreInMovie> initialMovieSet; 
	private HashSet<GenreInMovie> initialGenreSet; 
    private HashSet<GenreInMovie> genresInMoviesSet;
    
    private HashSet<GenreInMovie> newMovieSet; 
    private HashSet<GenreInMovie> newGenreSet; 
    private HashSet<GenreInMovie> newGenresInMoviesSet; 
	
    private HashMap<GenreInMovie, Integer> movieMap;
    private HashMap<GenreInMovie, Integer> movieMapWithID; 
    private HashMap<GenreInMovie, Integer> genreMap;
    
    private HashMap<GenreInMovie, String> movieToFilmIDMap; 
    
    private String batchInsertMovie; 
    private String batchInsertGenre; 
    private String batchInsertMovieInGenre; 
    
    private HashSet<GenreInMovie> GenreIDMovieID; 
    
    private int numberOfMovieInserts = 0;
    private int numberOfGenreInserts = 0; 
    private int numberOfGenreInMovieInserts = 0; 
    
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
        
        String getAllMovies = "SELECT * FROM movies; "; 
        ResultSet allMovies = statement.executeQuery(getAllMovies); 
        while (allMovies.next()){
        	if (allMovies.wasNull())
        		continue; 
        	initialMovieSet.add(new GenreInMovie(allMovies.getString("title").toLowerCase().trim(), allMovies.getInt("year"), allMovies.getString("director").toLowerCase().trim(), null));
        }
        allMovies.close(); 
        
        String getAllGenres = "SELECT * FROM genres; "; 
        ResultSet allGenres = statement.executeQuery(getAllGenres); 
        while (allGenres.next()){
        	if (allGenres.wasNull())
        		continue; 
        	initialGenreSet.add(new GenreInMovie(allGenres.getString("name").toLowerCase().trim()));
        }
        allGenres.close(); 
        
        String getAllGenresInMovies = "SELECT * FROM genres_in_movies; "; 
        ResultSet allGenresInMovies = statement.executeQuery(getAllGenresInMovies); 
        while (allGenresInMovies.next()){
        	if (allGenresInMovies.wasNull())
        		continue; 
        	GenreIDMovieID.add(new GenreInMovie(allGenresInMovies.getInt("genre_id"), allGenresInMovies.getInt("movie_id")));
        }
        allGenresInMovies.close(); 
        
        statement.close();
    	
    }
	
    private void insertIntoBatch(GenreInMovie tempGenreInMovie){
    	
        if (tempGenreInMovie.getTitle() == null || tempGenreInMovie.getYear() == -1 || tempGenreInMovie.getDirector() == null || tempGenreInMovie.getFilmID() == null || tempGenreInMovie.getTitle().equals("") || 
        		tempGenreInMovie.getDirector().equals("") || tempGenreInMovie.getFilmID().equals("")){
        	
        	return; 
        	
        }
        
        if (!initialMovieSet.contains(new GenreInMovie(tempGenreInMovie.getTitle().toLowerCase().trim(), tempGenreInMovie.getYear(), tempGenreInMovie.getDirector().toLowerCase().trim(), null)) &&
        		!newMovieSet.contains(new GenreInMovie(tempGenreInMovie.getTitle().toLowerCase().trim(), tempGenreInMovie.getYear(), tempGenreInMovie.getDirector().toLowerCase().trim(), null))){
        	
        	System.out.println("Repeat Movie: Title => " + tempGenreInMovie.getTitle() + " ----- Year: " + tempGenreInMovie.getYear() + " ----- Director: " + tempGenreInMovie.getDirector());
        	batchInsertMovie = batchInsertMovie.concat("(?, ?, ?), "); 
        	newMovieSet.add(new GenreInMovie(tempGenreInMovie.getTitle().toLowerCase().trim(), tempGenreInMovie.getYear(), tempGenreInMovie.getDirector().toLowerCase().trim(), null)); 
        	movieToFilmIDMap.put(new GenreInMovie(tempGenreInMovie.getTitle().toLowerCase().trim(), tempGenreInMovie.getYear(), tempGenreInMovie.getDirector().toLowerCase().trim(), null), tempGenreInMovie.getFilmID().toLowerCase().trim()); 
        	
        }
        
        if (tempGenreInMovie.getGenre() == null || tempGenreInMovie.getGenre().equals(""))
        	return; 
    	
        if (!initialGenreSet.contains(new GenreInMovie(tempGenreInMovie.getGenre().toLowerCase().trim())) && !newGenreSet.contains(new GenreInMovie(tempGenreInMovie.getGenre().toLowerCase().trim()))){
        	
        	System.out.println("Repeat Genre: Name => " + tempGenreInMovie.getGenre());
        	batchInsertGenre = batchInsertGenre.concat("(?), "); 
        	newGenreSet.add(new GenreInMovie(tempGenreInMovie.getGenre().toLowerCase().trim())); 
        	
        }
        
        if (!genresInMoviesSet.contains(new GenreInMovie(tempGenreInMovie.getTitle().toLowerCase().trim(), tempGenreInMovie.getYear(), tempGenreInMovie.getDirector().toLowerCase().trim(), tempGenreInMovie.getFilmID().toLowerCase().trim()))){
        	
        	System.out.println("Repeat Genre In Movie: Title => " + tempGenreInMovie.getTitle() + " ----- Year: " + tempGenreInMovie.getYear() + " ----- Director: " + tempGenreInMovie.getDirector() + " ----- Film ID: " + tempGenreInMovie.getFilmID());
        	genresInMoviesSet.add(new GenreInMovie(tempGenreInMovie.getTitle().toLowerCase().trim(), tempGenreInMovie.getYear(), tempGenreInMovie.getDirector().toLowerCase().trim(), null, tempGenreInMovie.getGenre().toLowerCase().trim())); 
        	
        }
        
    }
    
	private void insertIntoTable() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		batchInsertMovie = batchInsertMovie.substring(0, batchInsertMovie.length() - 2); 
		
		int insertMovieIndex = 1; 
		PreparedStatement insertMovieStatement = dbcon.prepareStatement(batchInsertMovie); 

		for (GenreInMovie movie : newMovieSet){
			
			insertMovieStatement.setString(insertMovieIndex, capitalize(movie.getTitle()));
			insertMovieIndex++; 
			insertMovieStatement.setInt(insertMovieIndex, movie.getYear());
			insertMovieIndex++; 
			insertMovieStatement.setString(insertMovieIndex, capitalize(movie.getDirector()));
			insertMovieIndex++; 
			
		}
		
		if (newMovieSet.size() > 0){
			
			numberOfMovieInserts = insertMovieStatement.executeUpdate(); 
			
		}
		
		insertMovieStatement.close(); 
		
		
		
		
		batchInsertGenre = batchInsertGenre.substring(0, batchInsertGenre.length() - 2); 
		
		int insertGenreIndex = 1; 
		PreparedStatement insertGenreStatement = dbcon.prepareStatement(batchInsertGenre); 
		
		for (GenreInMovie genre : newGenreSet){
			
			insertGenreStatement.setString(insertGenreIndex, capitalize(genre.getGenre()));
			insertGenreIndex++; 
			
		}
		
		if (newGenreSet.size() > 0){
			
			
			numberOfGenreInserts = insertGenreStatement.executeUpdate(); 
			
		}
		
		insertGenreStatement.close(); 
		
		
		Statement statement = dbcon.createStatement(); 
        String getAllMovies = "SELECT * FROM movies; "; 
        ResultSet allMovies = statement.executeQuery(getAllMovies); 
        while (allMovies.next()){
        	movieMap.put(new GenreInMovie(allMovies.getString("title").toLowerCase().trim(), allMovies.getInt("year"), allMovies.getString("director").toLowerCase().trim(), null), allMovies.getInt("id"));
        	movieMapWithID.put(new GenreInMovie(allMovies.getString("title").toLowerCase().trim(), allMovies.getInt("year"), allMovies.getString("director").toLowerCase().trim(), movieToFilmIDMap.get(new GenreInMovie(allMovies.getString("title").toLowerCase().trim(), allMovies.getInt("year"), allMovies.getString("director").toLowerCase().trim(), null))), allMovies.getInt("id"));
        }
        allMovies.close(); 
        
        String getAllGenres = "SELECT * FROM genres; "; 
        ResultSet allGenres = statement.executeQuery(getAllGenres); 
        while (allGenres.next()){
        	genreMap.put(new GenreInMovie(allGenres.getString("name").toLowerCase().trim()), allGenres.getInt("id"));
        }
        allGenres.close();
        
        for (GenreInMovie genreInMovie : genresInMoviesSet){
        	
        	int genreID = genreMap.get(new GenreInMovie(genreInMovie.getGenre().toLowerCase().trim()));
        	if (!movieMap.containsKey(new GenreInMovie(genreInMovie.getTitle().toLowerCase().trim(), genreInMovie.getYear(), genreInMovie.getDirector().toLowerCase().trim(), null))){
        		
        		System.out.println("Inconsistent Data: Title => " + genreInMovie.getTitle() + " ----- Year: " + genreInMovie.getYear() + " ----- Director: " + genreInMovie.getDirector()); 
        		continue; 
        		
        	}
        	int movieID = movieMap.get(new GenreInMovie(genreInMovie.getTitle().toLowerCase().trim(), genreInMovie.getYear(), genreInMovie.getDirector().toLowerCase().trim(), null)); 
        	
        	if (!GenreIDMovieID.contains(new GenreInMovie(genreID, movieID)) && !newGenresInMoviesSet.contains(new GenreInMovie(genreID, movieID))){
        		
        		newGenresInMoviesSet.add(new GenreInMovie(genreID, movieID));
        		batchInsertMovieInGenre = batchInsertMovieInGenre.concat("(?, ?), "); 
        		
        	} 
        	
        }
        
        batchInsertMovieInGenre = batchInsertMovieInGenre.substring(0, batchInsertMovieInGenre.length() - 2); 
        
        int insertGenresInMoviesIndex = 1; 
        
		PreparedStatement insertGenresInMoviesStatement = dbcon.prepareStatement(batchInsertMovieInGenre); 
		
		for (GenreInMovie genreidmovieid : newGenresInMoviesSet){
			
			insertGenresInMoviesStatement.setInt(insertGenresInMoviesIndex, genreidmovieid.getGenreID());
			insertGenresInMoviesIndex++; 
			insertGenresInMoviesStatement.setInt(insertGenresInMoviesIndex, genreidmovieid.getMovieID());
			insertGenresInMoviesIndex++; 
			
		}
		
		if (newGenresInMoviesSet.size() > 0){
			
			
			numberOfGenreInMovieInserts = insertGenresInMoviesStatement.executeUpdate(); 
			
		}
		
		insertGenresInMoviesStatement.close(); 
        
        statement.close(); 
		
		
	}
	
	private boolean isYear(String year){
		
		for (int i = 0; i < year.length(); i++){
			
			if (!Character.isDigit(year.charAt(i)))
				return false; 
			
		}
		
		return true; 
		
	}

	public AllFourMovie() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		initialMovieSet = new HashSet<GenreInMovie>(); 
	    initialGenreSet = new HashSet<GenreInMovie>(); 
	    
	    newMovieSet = new HashSet<GenreInMovie>(); 
	    newGenreSet = new HashSet<GenreInMovie>(); 
	    newGenresInMoviesSet = new HashSet<GenreInMovie>(); 
	    
	    movieToFilmIDMap = new HashMap<GenreInMovie, String>(); 
		
	    movieMap = new HashMap<GenreInMovie, Integer>(); 
	    movieMapWithID = new HashMap<GenreInMovie, Integer>(); 
	    genreMap = new HashMap<GenreInMovie, Integer>(); 
	    genresInMoviesSet = new HashSet<GenreInMovie>(); 
	    
	    GenreIDMovieID = new HashSet<GenreInMovie>(); 
	    
	    batchInsertMovie = "INSERT INTO movies (title, year, director) VALUES "; 
	    batchInsertGenre = "INSERT INTO genres (name) VALUES "; 
	    batchInsertMovieInGenre = "INSERT INTO genres_in_movies (genre_id, movie_id) VALUES "; 
		
	}
	
	public void runExample(Connection dbcon) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		this.dbcon = dbcon; 
		
		initializeHashSet(); 
		parseDocument();
		insertIntoTable(); 
		
	}

	private void parseDocument() {
		
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			SAXParser sp = spf.newSAXParser();
			sp.parse("../mains243.xml", this);  
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		if (qName.equalsIgnoreCase("film")){
			
			tempGenreInMovie = new GenreInMovie(); 
			bFilm = true; 
			
		}
		else if (qName.equalsIgnoreCase("t"))
			bTitle = true; 
		else if (qName.equalsIgnoreCase("year"))
			bYear = true; 
		else if (qName.equalsIgnoreCase("dirn") && bFilm == true)
			bDirector = true; 
		else if (qName.equalsIgnoreCase("cat"))
			bGenre = true; 
		else if (qName.equalsIgnoreCase("fid"))
			bFilmID = true; 
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if (bTitle){
			
			tempGenreInMovie.setTitle(new String(ch, start, length).trim());
			bTitle = false; 
			
		}
			
		else if (bYear){
			
			if (isYear(new String(ch, start, length).trim()))
				tempGenreInMovie.setYear(Integer.parseInt(new String(ch, start, length).trim()));
			else {
				
				System.out.print("Inconsistent Data: Year => " + new String(ch, start, length).trim());
				if (tempGenreInMovie != null && tempGenreInMovie.getTitle() != null)
					System.out.print(" ----- Title => " + tempGenreInMovie.getTitle());
				System.out.println(); 
				tempGenreInMovie.setYear(-1); 
				
			}
					
			bYear = false; 
			
		}
			
		else if (bDirector){
			
			tempGenreInMovie.setDirector(new String(ch, start, length).trim());
			bDirector = false; 
			
		}
		
		else if (bGenre){
			
			tempGenreInMovie.setGenre(new String(ch, start, length).trim());
			bGenre = false; 
			
		}
		
		else if (bFilmID){
			
			tempGenreInMovie.setFilmID(new String(ch, start, length).trim());
			bFilmID = false; 
			
		}
			
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if (qName.equalsIgnoreCase("film")){

			insertIntoBatch(tempGenreInMovie);
			tempGenreInMovie = null; 
			bFilm = false; 
			
		}
		
	}
	
	public HashMap<GenreInMovie, Integer> getMovieMapWithID(){
		
		return movieMapWithID; 
		
	}
	
	public int getNumberOfMovieInserts() { return numberOfMovieInserts; }
	public int getNumberOfGenreInserts() { return numberOfGenreInserts; }
	public int getNumberOfGenreInMovieInserts() { return numberOfGenreInMovieInserts; }
	
	
}




