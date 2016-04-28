package cs122b_3;

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

import org.xml.sax.helpers.DefaultHandler;

import java.sql.*; 

public class SAXMovieParser extends DefaultHandler{
	
	boolean bFilm = false; 
	boolean bTitle = false; 
	boolean bYear = false; 
	boolean bDirector = false; 
	boolean bGenre = false; 
	
	private GenreInMovie tempGenreInMovie; 
	
    HashMap<GenreInMovie, Integer> movieMap;
    HashMap<GenreInMovie, Integer> genreMap;
    HashSet<GenreInMovie> genresInMoviesSet; 
    
    private void initializeHashMaps() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	
		String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql:///moviedb";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        
        Statement statement = dbcon.createStatement(); 
        String getAllMovies = "SELECT * FROM movies; "; 
        ResultSet allMovies = statement.executeQuery(getAllMovies); 
        while (allMovies.next())
        	movieMap.put(new GenreInMovie(allMovies.getString("title").toLowerCase().trim(), allMovies.getInt("year"), allMovies.getString("director").toLowerCase().trim()), allMovies.getInt("id"));
        allMovies.close(); 
        
        String getAllGenres = "SELECT * FROM genres; "; 
        ResultSet allGenres = statement.executeQuery(getAllGenres); 
        while (allGenres.next())
        	genreMap.put(new GenreInMovie(allGenres.getString("name").toLowerCase().trim()), allGenres.getInt("id"));
        allGenres.close();
        
        String getAllGenreInMovies = "SELECT * FROM genres_in_movies; "; 
        ResultSet allGenresInMovies = statement.executeQuery(getAllGenreInMovies); 
        while (allGenresInMovies.next())
        	genresInMoviesSet.add(new GenreInMovie(allGenresInMovies.getInt("genre_id"), allGenresInMovies.getInt("movie_id"))); 
        	
        statement.close(); 
        dbcon.close(); 
    	
    }
	
	
	private void insertIntoTable(GenreInMovie tempGenreInMovie) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		//System.out.println(tempGenreInMovie.getTitle() + " --- " + tempGenreInMovie.getYear() + " --- " + tempGenreInMovie.getDirector() + " --- " + tempGenreInMovie.getGenre());

        if (tempGenreInMovie.getTitle() == null || tempGenreInMovie.getYear() == -1 || tempGenreInMovie.getDirector() == null || tempGenreInMovie.getTitle().equals("") || 
        		tempGenreInMovie.getDirector().equals("")){
        	
        	return; 
        	
        }
        
		
		String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql:///moviedb";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        
        int insertMovieStatus = 0; 
        if (!movieMap.containsKey(new GenreInMovie(tempGenreInMovie.getTitle().toLowerCase().trim(), tempGenreInMovie.getYear(), tempGenreInMovie.getDirector().toLowerCase().trim()))){
        
	        String insertMovie = "INSERT INTO movies (title, year, director) VALUES (?, ?, ?); "; 
	        PreparedStatement insertMovieStatement = dbcon.prepareStatement(insertMovie); 
	        insertMovieStatement.setString(1, tempGenreInMovie.getTitle());
	        insertMovieStatement.setInt(2, tempGenreInMovie.getYear());
	        insertMovieStatement.setString(3, tempGenreInMovie.getDirector());
	        insertMovieStatus = insertMovieStatement.executeUpdate(); 
	        insertMovieStatement.close(); 
        
        }

        if (insertMovieStatus > 0){
        	
        	String getLastKey = "SELECT LAST_INSERT_ID(); "; 
        	PreparedStatement getLastKeyStatement = dbcon.prepareStatement(getLastKey); 
        	ResultSet primaryKey = getLastKeyStatement.executeQuery(); 
        	primaryKey.next(); 
        	movieMap.put(new GenreInMovie(tempGenreInMovie.getTitle().toLowerCase().trim(), tempGenreInMovie.getYear(), tempGenreInMovie.getDirector().toLowerCase().trim()), primaryKey.getInt(1)); 
        	primaryKey.close(); 
        	getLastKeyStatement.close(); 
        	
        }

        if (tempGenreInMovie.getGenre() == null || tempGenreInMovie.getGenre().equals("")){
        	
            dbcon.close(); 
        	return; 
        	
        }
        
        int insertGenreStatus = 0; 
        if (!genreMap.containsKey(new GenreInMovie(tempGenreInMovie.getGenre().toLowerCase().trim()))){
        
	        String insertGenre = "INSERT INTO genres (name) VALUES (?); "; 
	        PreparedStatement insertGenreStatement = dbcon.prepareStatement(insertGenre); 
	        insertGenreStatement.setString(1, tempGenreInMovie.getGenre());
	        insertGenreStatus = insertGenreStatement.executeUpdate(); 
	        insertGenreStatement.close(); 
        
        }
        
        if (insertGenreStatus > 0){
        	
        	String getLastKey = "SELECT LAST_INSERT_ID(); "; 
        	PreparedStatement getLastKeyStatement = dbcon.prepareStatement(getLastKey); 
        	ResultSet primaryKey = getLastKeyStatement.executeQuery(); 
        	primaryKey.next(); 
        	genreMap.put(new GenreInMovie(tempGenreInMovie.getGenre().toLowerCase().trim()), primaryKey.getInt(1)); 
        	primaryKey.close(); 
        	getLastKeyStatement.close();
        	
        }
        

        int genreID = genreMap.get(new GenreInMovie(tempGenreInMovie.getGenre().toLowerCase())); 
        int movieID = movieMap.get(new GenreInMovie(tempGenreInMovie.getTitle().toLowerCase().trim(), tempGenreInMovie.getYear(), tempGenreInMovie.getDirector().toLowerCase().trim())); 
        
        int insertGenresInMoviesStatus = 0; 
        if (!genresInMoviesSet.contains(new GenreInMovie(genreID, movieID))){
        
	        String insertGenreInMovie = "INSERT INTO genres_in_movies (genre_id, movie_id) VALUES (?, ?); "; 
	        PreparedStatement insertGenreInMovieStatement = dbcon.prepareStatement(insertGenreInMovie); 
	        insertGenreInMovieStatement.setInt(1, genreID);
	        insertGenreInMovieStatement.setInt(2, movieID);
	        insertGenreInMovieStatement.setInt(3, genreID);
	        insertGenreInMovieStatement.setInt(4, movieID);
	        insertGenresInMoviesStatus = insertGenreInMovieStatement.executeUpdate(); 
	        insertGenreInMovieStatement.close(); 
	        
        }
        
        if (insertGenresInMoviesStatus > 0){
        	
        	genresInMoviesSet.add(new GenreInMovie(genreID, movieID)); 
        	
        }
        
        dbcon.close(); 
		
	}
	
	private boolean isYear(String year){
		
		for (int i = 0; i < year.length(); i++){
			
			if (!Character.isDigit(year.charAt(i)))
				return false; 
			
		}
		
		return true; 
		
	}

	public SAXMovieParser(){
		
	    movieMap = new HashMap<GenreInMovie, Integer>(); 
	    genreMap = new HashMap<GenreInMovie, Integer>(); 
	    genresInMoviesSet = new HashSet<GenreInMovie>(); 
		
	}
	
	public void runExample() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		initializeHashMaps();
		parseDocument();
		
	}

	private void parseDocument() {
		
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			SAXParser sp = spf.newSAXParser();
			sp.parse("/Users/Colin_Son/Downloads/stanford-movies/mains243.xml", this);  
			
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
	}
	
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
		
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if (bTitle){
			
			tempGenreInMovie.setTitle(new String(ch, start, length).trim());
			bTitle = false; 
			
		}
			
		else if (bYear){
			
			if (isYear(new String(ch, start, length)))
				tempGenreInMovie.setYear(Integer.parseInt(new String(ch, start, length).trim()));
			else
				tempGenreInMovie.setYear(-1); 
					
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
			
		
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if (qName.equalsIgnoreCase("film")){
			
			try {
				insertIntoTable(tempGenreInMovie);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} 
			tempGenreInMovie = null; 
			bFilm = false; 
			
		}
		
	}
	
	public static void main(String[] args){
		
		Runtime runtime = Runtime.getRuntime(); 
		
		long start = System.currentTimeMillis(); 
		SAXMovieParser spe = new SAXMovieParser();
		try {
			spe.runExample();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis(); 
		System.out.println("Time in Seconds: " + ((end - start) / 1000.0));
		System.out.println("Total Memory (in MB) Used: " + ((runtime.totalMemory() - runtime.freeMemory()) / 1048576.0));
		
	}
	
}




