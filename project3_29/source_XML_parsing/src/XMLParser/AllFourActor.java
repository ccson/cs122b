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

public class AllFourActor extends DefaultHandler{
	
	private StarInMovie tempStar; 
	
	private Connection dbcon; 
	
	private boolean bStageName = false; 
	
	private HashSet<StarInMovie> actorSet; 
	private HashSet<StarInMovie> newActorSet; 
	
	private String batchInsertStar; 
	
	private int numberOfStarInserts = 0; 
	
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
    
    private void intializeHashSet() throws SQLException{
    	
        Statement statement = dbcon.createStatement(); 
        
        String getAllStars = "SELECT * FROM stars; "; 
        ResultSet allStars = statement.executeQuery(getAllStars); 
        while (allStars.next()){
        	if (allStars.wasNull())
        		continue; 
        	actorSet.add(new StarInMovie(allStars.getString("first_name").toLowerCase().trim(), allStars.getString("last_name").toLowerCase().trim()));
        }
        allStars.close(); 
        
        statement.close(); 
    	
    }
	
	private void insertIntoBatch(StarInMovie tempStar){
		
		if (tempStar == null || tempStar.getActorFirstName() == null)
			return; 
		
		if (!actorSet.contains(new StarInMovie(tempStar.getActorFirstName().toLowerCase().trim(), tempStar.getActorLastName().toLowerCase().trim())) 
				&& !newActorSet.contains(new StarInMovie(tempStar.getActorFirstName().toLowerCase().trim(), tempStar.getActorLastName().toLowerCase().trim()))){
			
			newActorSet.add(new StarInMovie(tempStar.getActorFirstName().toLowerCase().trim(), tempStar.getActorLastName().toLowerCase().trim())); 
			batchInsertStar = batchInsertStar.concat("(?, ?), ");  
			
		}
		
	}
	
	private void insertIntoTable() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		
		batchInsertStar = batchInsertStar.substring(0, batchInsertStar.length() - 2); 
		
		PreparedStatement insertStarStatement = dbcon.prepareStatement(batchInsertStar); 
		
		int batchInsertStarIndex = 1; 
		
		for (StarInMovie actor : newActorSet){
			
			insertStarStatement.setString(batchInsertStarIndex, capitalize(actor.getActorFirstName()));
			batchInsertStarIndex++; 
			insertStarStatement.setString(batchInsertStarIndex, capitalize(actor.getActorLastName()));
			batchInsertStarIndex++; 
			
		}
		
		if (newActorSet.size() > 0){
			
			numberOfStarInserts = insertStarStatement.executeUpdate(); 
			
		}
		
		insertStarStatement.close(); 
        
	}
	
	public AllFourActor() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		actorSet = new HashSet<StarInMovie>(); 
		newActorSet = new HashSet<StarInMovie>(); 
		
		batchInsertStar = "INSERT INTO stars (first_name, last_name) VALUES "; 
		
	}
	
	public void runExample(Connection dbcon) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		this.dbcon = dbcon; 
		
		intializeHashSet(); 
		parseDocument();
		insertIntoTable(); 
		
	}

	private void parseDocument() {
		
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			SAXParser sp = spf.newSAXParser();
			sp.parse("../actors63.xml", this);  
			
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
		
		if (qName.equalsIgnoreCase("actor"))
			tempStar = new StarInMovie(); 
		else if (qName.equalsIgnoreCase("stagename"))
			bStageName = true; 
			
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if (bStageName){
			
			String stageName = new String(ch, start, length).trim();
			String[] nameArray = stageName.split(" "); 
			
			if (nameArray.length == 2){
				
				tempStar.setActorFirstName(nameArray[0]);
				tempStar.setActorLastName(nameArray[1]);
				
			}
			
			else if (nameArray.length == 1){
				
				tempStar.setActorFirstName("");
				tempStar.setActorLastName(nameArray[0]);
				
			}
			
			else if (nameArray.length > 2){
				
				String firstName = ""; 
				
				tempStar.setActorLastName(nameArray[nameArray.length - 1]); 
				for (int i = 0; i < nameArray.length - 1; i++)
					firstName = firstName.concat(nameArray[i] + " "); 
				tempStar.setActorFirstName(firstName.trim());
				
			}
			
			bStageName = false; 
			
		}
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase("actor")){
			
			insertIntoBatch(tempStar);
			tempStar = null; 
			
		}
		
	}
	
	public int getNumberOfStarInserts() { return numberOfStarInserts; }

}

