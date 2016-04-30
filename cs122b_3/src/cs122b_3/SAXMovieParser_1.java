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

public class SAXMovieParser_1 extends DefaultHandler{
	
//	boolean bFilm = false; 
//	boolean bTitle = false; 
//	boolean bYear = false; 
//	boolean bDirector = false; 
//	boolean bGenre = false; 
//	
//	private GenreInMovie tempGenreInMovie; 
//	
//	Connection dbcon; 
//	
//	private void insertIntoTable(GenreInMovie tempGenreInMovie) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
//
//        if (tempGenreInMovie.getTitle() == null || tempGenreInMovie.getYear() == -1 || tempGenreInMovie.getDirector() == null || tempGenreInMovie.getTitle().equals("") || 
//        		tempGenreInMovie.getDirector().equals("")){
//        	
//        	return; 
//        	
//        }
//        
//        
//        String insertMovie = "INSERT INTO movies (title, year, director) SELECT ?, ?, ? FROM dual WHERE NOT EXISTS (SELECT * FROM movies WHERE title = ? AND year = ? AND director = ?); "; 
//        PreparedStatement insertMovieStatement = dbcon.prepareStatement(insertMovie); 
//        insertMovieStatement.setString(1, tempGenreInMovie.getTitle());
//        insertMovieStatement.setInt(2, tempGenreInMovie.getYear());
//        insertMovieStatement.setString(3, tempGenreInMovie.getDirector());
//        insertMovieStatement.setString(4, tempGenreInMovie.getTitle());
//        insertMovieStatement.setInt(5, tempGenreInMovie.getYear());
//        insertMovieStatement.setString(6, tempGenreInMovie.getDirector());
//        insertMovieStatement.executeUpdate(); 
//        insertMovieStatement.close(); 
//
//        if (tempGenreInMovie.getGenre() == null || tempGenreInMovie.getGenre().equals("")){
//        	
//        	return; 
//        	
//        }
//        
//        
//        String insertGenre = "INSERT INTO genres (name) SELECT ? FROM dual WHERE NOT EXISTS (SELECT * FROM genres WHERE name = ?); "; 
//        PreparedStatement insertGenreStatement = dbcon.prepareStatement(insertGenre); 
//        insertGenreStatement.setString(1, tempGenreInMovie.getGenre());
//        insertGenreStatement.setString(2, tempGenreInMovie.getGenre());
//        insertGenreStatement.executeUpdate(); 
//        insertGenreStatement.close(); 
//        
//        
//        String movieIDQuery = "SELECT id FROM movies WHERE title = ? AND year = ? AND director = ?; "; 
//        PreparedStatement movieIDQueryStatement = dbcon.prepareStatement(movieIDQuery); 
//        movieIDQueryStatement.setString(1, tempGenreInMovie.getTitle());
//        movieIDQueryStatement.setInt(2, tempGenreInMovie.getYear());
//        movieIDQueryStatement.setString(3, tempGenreInMovie.getDirector());
//        ResultSet movieIDResult = movieIDQueryStatement.executeQuery(); 
//        movieIDResult.next(); 
//        int movieID = movieIDResult.getInt("id"); 
//        movieIDQueryStatement.close(); 
//        
//        String genreIDQuery = "SELECT id FROM genres WHERE name = ?; "; 
//        PreparedStatement genreIDQueryStatement = dbcon.prepareStatement(genreIDQuery); 
//        genreIDQueryStatement.setString(1, tempGenreInMovie.getGenre());
//        ResultSet genreIDResult = genreIDQueryStatement.executeQuery(); 
//        genreIDResult.next(); 
//        int genreID = genreIDResult.getInt("id"); 
//        genreIDQueryStatement.close(); 
//        
//        String insertGenreInMovie = "INSERT INTO genres_in_movies (genre_id, movie_id) SELECT ?, ? FROM dual WHERE NOT EXISTS (SELECT * FROM genres_in_movies WHERE genre_id = ? AND movie_id = ?); "; 
//        PreparedStatement insertGenreInMovieStatement = dbcon.prepareStatement(insertGenreInMovie); 
//        insertGenreInMovieStatement.setInt(1, genreID);
//        insertGenreInMovieStatement.setInt(2, movieID);
//        insertGenreInMovieStatement.setInt(3, genreID);
//        insertGenreInMovieStatement.setInt(4, movieID);
//        insertGenreInMovieStatement.executeUpdate(); 
//        insertGenreInMovieStatement.close(); 
//		
//	}
//	
//	private boolean isYear(String year){
//		
//		for (int i = 0; i < year.length(); i++){
//			
//			if (!Character.isDigit(year.charAt(i)))
//				return false; 
//			
//		}
//		
//		return true; 
//		
//	}
//
//	public SAXMovieParser_1() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
//		
//		String loginUser = "root";
//        String loginPasswd = "calmdude6994";
//        String loginUrl = "jdbc:mysql:///moviedb";
//        
//        Class.forName("com.mysql.jdbc.Driver").newInstance();
//        dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
//        dbcon.setAutoCommit(false);
//		
//	}
//	
//	public void runExample() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
//
//		parseDocument();
//		dbcon.commit();
//		dbcon.close(); 
//		
//	}
//
//	private void parseDocument() {
//		
//		
//		SAXParserFactory spf = SAXParserFactory.newInstance();
//		try {
//		
//			SAXParser sp = spf.newSAXParser();
//			sp.parse("/Users/Colin_Son/Downloads/stanford-movies/mains243.xml", this);  
//			
//		}catch(SAXException se) {
//			se.printStackTrace();
//		}catch(ParserConfigurationException pce) {
//			pce.printStackTrace();
//		}catch (IOException ie) {
//			ie.printStackTrace();
//		}
//	}
//	
//	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//		
//		if (qName.equalsIgnoreCase("film")){
//			
//			tempGenreInMovie = new GenreInMovie(); 
//			bFilm = true; 
//			
//		}
//		else if (qName.equalsIgnoreCase("t"))
//			bTitle = true; 
//		else if (qName.equalsIgnoreCase("year"))
//			bYear = true; 
//		else if (qName.equalsIgnoreCase("dirn") && bFilm == true)
//			bDirector = true; 
//		else if (qName.equalsIgnoreCase("cat"))
//			bGenre = true; 
//		
//	}
//	
//
//	public void characters(char[] ch, int start, int length) throws SAXException {
//		
//		if (bTitle){
//			
//			tempGenreInMovie.setTitle(new String(ch, start, length).trim());
//			bTitle = false; 
//			
//		}
//			
//		else if (bYear){
//			
//			if (isYear(new String(ch, start, length)))
//				tempGenreInMovie.setYear(Integer.parseInt(new String(ch, start, length).trim()));
//			else
//				tempGenreInMovie.setYear(-1); 
//					
//			bYear = false; 
//			
//		}
//			
//		else if (bDirector){
//			
//			tempGenreInMovie.setDirector(new String(ch, start, length).trim());
//			bDirector = false; 
//			
//		}
//		
//		else if (bGenre){
//			
//			tempGenreInMovie.setGenre(new String(ch, start, length).trim());
//			bGenre = false; 
//			
//		}
//			
//		
//	}
//	
//	public void endElement(String uri, String localName, String qName) throws SAXException {
//		
//		if (qName.equalsIgnoreCase("film")){
//			
//			try {
//				insertIntoTable(tempGenreInMovie);
//			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//				e.printStackTrace();
//			} 
//			tempGenreInMovie = null; 
//			bFilm = false; 
//			
//		}
//		
//	}
//	
//	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
//		
//		Runtime runtime = Runtime.getRuntime(); 
//		
//		long start = System.currentTimeMillis(); 
//		SAXMovieParser_1 spe = new SAXMovieParser_1();
//		try {
//			spe.runExample();
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		long end = System.currentTimeMillis(); 
//		System.out.println("Time in Seconds: " + ((end - start) / 1000.0));
//		System.out.println("Total Memory (in MB) Used: " + ((runtime.totalMemory() - runtime.freeMemory()) / 1048576.0));
//		
//	}
	
}




