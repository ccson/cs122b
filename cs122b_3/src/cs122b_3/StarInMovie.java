package cs122b_3;

public class StarInMovie {
	
	private String actorFirstName;
	private String actorLastName; 
	private String movieTitle; 
	private int starID; 
	private int movieID; 
	
	public StarInMovie() { }
	
	public StarInMovie(String actorFirstName, String actorLastName, String movieTitle){
		
		this.actorFirstName = actorFirstName; 
		this.actorLastName = actorLastName; 
		this.movieTitle = movieTitle; 
		
		this.starID = -1; 
		this.movieID = -1; 
		
	}
	
	public StarInMovie(String movieTitle){
		
		this.movieTitle = movieTitle; 
		
		this.starID = -1; 
		this.movieID = -1; 
		
	}
	
	public StarInMovie(String actorFirstName, String actorLastName){
		
		this.actorFirstName = actorFirstName; 
		this.actorLastName = actorLastName; 
		
		this.starID = -1; 
		this.movieID = -1; 
		
	}
	
	public StarInMovie(int starID, int movieID){
		
		this.starID = starID; 
		this.movieID = movieID; 
		
	}
	
	public String getActorFirstName() { return actorFirstName; }
	public String getActorLastName() { return actorLastName; }
	public String getMovieTitle() { return movieTitle; }
	public int getStarID() { return starID; }
	public int getMovieID() { return movieID; }
	
	public void setActorFirstName(String actorFirstName) { this.actorFirstName = actorFirstName; }
	public void setActorLastName(String actorLastName) { this.actorLastName = actorLastName; }
	public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
	public void setStarID(int starID) { this.starID = starID; }
	public void setMovieID(int movieID) { this.movieID = movieID; }
	
	@Override 
	public boolean equals(Object object){
		
		StarInMovie comparison = (StarInMovie) object; 
		
		if (this.actorFirstName != null && !this.actorFirstName.equals(comparison.getActorFirstName()))
			return false; 
		if (this.actorLastName != null && !this.actorLastName.equals(comparison.getActorLastName()))
			return false; 
		if (this.movieTitle != null && !this.movieTitle.equals(comparison.getMovieTitle()))
			return false; 
		if (this.starID != comparison.getStarID())
			return false; 
		if (this.movieID != comparison.getMovieID())
			return false; 
			
		return true; 
		
	}
	
	@Override
	public int hashCode(){
		
		int hash = 7; 
		hash = 31 * hash + (null == this.actorFirstName ? 0 : this.actorFirstName.hashCode()); 
		hash = 31 * hash + (null == this.actorLastName ? 0 : this.actorLastName.hashCode()); 
		hash = 31 * hash + (null == this.movieTitle ? 0 : this.movieTitle.hashCode()); 
		hash = 31 * hash + this.starID; 
		hash = 31 * hash + this.movieID; 
		return hash; 
		
	}

}
