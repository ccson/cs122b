import java.util.*;

public class Genre {
	
	private int id; 
	private String name; 
	
	public Genre(int id, String name){
		
		this.id = id; 
		this.name = name; 
		
	}
	
	public int getID() { return id; }
	public String getName() { return name; }
	
	public static class sortGenreAlphabetically implements Comparator<Genre>{
		
		public int compare(Genre g1, Genre g2){
			
			return g1.getName().compareTo(g2.getName());  
			
		}
		
	}
	
}
