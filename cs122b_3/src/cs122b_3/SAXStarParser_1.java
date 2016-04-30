package cs122b_3;

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

public class SAXStarParser_1 extends DefaultHandler{
	
	private StarInMovie tempStarInMovie; 
	
	private Connection dbcon; 
	
	private boolean bTitle = false; 
	private boolean bActor = false; 
	private boolean bFilmID = false; 
	
	private void insertIntoTable(StarInMovie tempStarInMovie) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
        if (tempStarInMovie.getActorFirstName() == null || tempStarInMovie.getActorLastName() == null || tempStarInMovie.getFilmID() == null &&
        		tempStarInMovie.getActorFirstName().equals("") || tempStarInMovie.getActorLastName().equals("") || tempStarInMovie.getFilmID().equals("")){
        	
        	return; 
        	
        }
        
        
        String insertStar = "INSERT INTO stars (first_name, last_name) SELECT ?, ? FROM dual WHERE NOT EXISTS (SELECT * FROM stars WHERE first_name = ? AND last_name = ?); "; 
        PreparedStatement insertStarStatement = dbcon.prepareStatement(insertStar); 
        insertStarStatement.setString(1, tempStarInMovie.getActorFirstName());
        insertStarStatement.setString(2, tempStarInMovie.getActorLastName());
        insertStarStatement.setString(3, tempStarInMovie.getActorFirstName());
        insertStarStatement.setString(4, tempStarInMovie.getActorLastName());
        insertStarStatement.executeUpdate(); 
        insertStarStatement.close(); 

        if (tempStarInMovie.getMovieTitle() == null || tempStarInMovie.getMovieTitle().equals("")){
        	
        	return; 
        	
        }
        
        String getStarID = "SELECT id FROM stars WHERE first_name = ? AND last_name = ?; "; 
        PreparedStatement getStarIDStatement = dbcon.prepareStatement(getStarID); 
        getStarIDStatement.setString(1, tempStarInMovie.getActorFirstName());
        getStarIDStatement.setString(2, tempStarInMovie.getActorLastName());
        ResultSet starInMovieResult = getStarIDStatement.executeQuery(); 
        starInMovieResult.next(); 
        int starID = starInMovieResult.getInt("id"); 
        starInMovieResult.close(); 
        
        String insertStarInMovieQuery = "INSERT INTO stars_in_movies (star_id, movie_id) SELECT ?, ? FROM dual WHERE NOT EXISTS (SELECT * FROM stars_in_movies WHERE star_id = ? AND movie_id = ?); "; 
        PreparedStatement insertStarInMovieStatement = dbcon.prepareStatement(insertStarInMovieQuery); 
        insertStarInMovieStatement.setInt(1, starID);
        insertStarInMovieStatement.setInt(2, movieID);
        insertStarInMovieStatement.setInt(3, starID);
        insertStarInMovieStatement.setInt(4, movieID);
        insertStarInMovieStatement.executeUpdate(); 
        insertStarInMovieStatement.close(); 
        
	}
	
	public SAXStarParser_1() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		String loginUser = "root";
        String loginPasswd = "calmdude6994";
        String loginUrl = "jdbc:mysql:///moviedb";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        dbcon.setAutoCommit(false);
		
	}
	
	public void runExample() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
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
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		Runtime runtime = Runtime.getRuntime(); 
		
		long start = System.currentTimeMillis(); 
		SAXStarParser_1 spe = new SAXStarParser_1();
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

