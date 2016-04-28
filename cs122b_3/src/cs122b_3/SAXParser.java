package cs122b_3;

import java.sql.SQLException;

public class SAXParser {
	
	public static void main(String[] args){
		
		long MovieParserStart;
		long MovieParserEnd;
		long StarParserStart; 
		long StarParserEnd; 
		
		SAXMovieParser movieParser = new SAXMovieParser();
		SAXStarParser starParser = new SAXStarParser();
		try {
			
			MovieParserStart = System.currentTimeMillis(); 
			movieParser.runExample();
			MovieParserEnd = System.currentTimeMillis(); 
			System.out.println("Time in Seconds for Movie Parser: " + ((MovieParserEnd - MovieParserStart) / 1000.0));
			
			StarParserStart = System.currentTimeMillis(); 
			starParser.runExample(movieParser.getMovieMap());
			StarParserEnd = System.currentTimeMillis(); 
			System.out.println("Time in Seconds for Star Parser: " + ((StarParserEnd - StarParserStart) / 1000.0));
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
