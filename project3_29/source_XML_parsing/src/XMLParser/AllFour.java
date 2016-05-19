package XMLParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*; 

public class AllFour {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		String loginUser = "classta";
        String loginPasswd = "classta";
        String loginUrl = "jdbc:mysql:///moviedb_project3_grading";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        dbcon.setAutoCommit(false);
		
		long MovieParserStart;
		long MovieParserEnd;
		long StarParserStart; 
		long StarParserEnd; 
		long ActorParserStart; 
		long ActorParserEnd; 
		
		AllFourMovie movieParser = new AllFourMovie();
		AllFourStar starParser = new AllFourStar();
		AllFourActor actorParser = new AllFourActor(); 
		
		try {
			
			MovieParserStart = System.currentTimeMillis(); 
			movieParser.runExample(dbcon);
			MovieParserEnd = System.currentTimeMillis(); 
			
			StarParserStart = System.currentTimeMillis(); 
			starParser.runExample(movieParser.getMovieMapWithID(), dbcon);
			StarParserEnd = System.currentTimeMillis(); 
			
			ActorParserStart = System.currentTimeMillis(); 
			actorParser.runExample(dbcon);
			ActorParserEnd = System.currentTimeMillis(); 
			
			dbcon.commit(); 
			dbcon.close(); 
			
			System.out.println("\nSummary: ");
			System.out.println("-------------------------------------");
			System.out.println("Time in Seconds for Movie Parser: " + ((MovieParserEnd - MovieParserStart) / 1000.0) + " Seconds");
			System.out.println("Time in Seconds for Star Parser: " + ((StarParserEnd - StarParserStart) / 1000.0) + " Seconds");
			System.out.println("Time in Seconds for Actor Parser: " + ((ActorParserEnd - ActorParserStart) / 1000.0) + " Seconds");
			System.out.println("Number of Inserts into \"movie\" Table: " + movieParser.getNumberOfMovieInserts());
			System.out.println("Number of Inserts into \"genre\" Table: " + movieParser.getNumberOfGenreInserts());
			System.out.println("Number of Inserts into \"genres_in_movies\" Table: " + movieParser.getNumberOfGenreInMovieInserts());
			System.out.println("Number of Inserts into \"stars\" Table: " + (starParser.getNumberOfStarInserts() + actorParser.getNumberOfStarInserts()));
			System.out.println("Number of Inserts into \"stars_in_movies\" Table: " + starParser.getNumberOfStarInMovieInserts());
			
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
