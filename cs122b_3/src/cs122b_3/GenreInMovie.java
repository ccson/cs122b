package cs122b_3;

public class GenreInMovie {
	
	private String title; 
	private int year; 
	private String director;
	private String genre; 
	private String filmID; 
	
	private int genreID; 
	private int movieID; 
	
	public GenreInMovie() {
		
		genreID = -1; 
		movieID = -1; 
		
	}
	
	public GenreInMovie(String title, int year, String director, String filmID){
		
		this.title = title; 
		this.year = year; 
		this.director = director; 
		this.filmID = filmID; 
		
		this.genreID = -1; 
		this.movieID = -1; 
		
	}
	
	public GenreInMovie(String genre){
		
		this.genre = genre; 
		
		this.year = -1; 
		this.genreID = -1; 
		this.movieID = -1; 
		
	}
	
	public GenreInMovie(int genreID, int movieID){
		
		this.genreID = genreID; 
		this.movieID = movieID; 
		
		this.year = -1; 
		
	}
	
	public String getTitle() { return title; }
	public int getYear() { return year; }
	public String getDirector() { return director; }
	public String getGenre() { return genre; }
	public int getGenreID() { return genreID; }
	public int getMovieID() { return movieID; }
	public String getFilmID() { return filmID; }
	
	
	public void setTitle(String title) { this.title = title; }
	public void setYear(int year) { this.year = year; }
	public void setDirector(String director) { this.director = director; }
	public void setGenre(String genre) { this.genre = genre; }
	public void setGenreID(int genreID) { this.genreID = genreID; }
	public void setMovieID(int movieID) { this.movieID = movieID; }
	public void setFilmID(String filmID) { this.filmID = filmID; }
	
	@Override 
	public boolean equals(Object object){
		
		if (object == null || this.getClass() != object.getClass())
			return false; 
		
		GenreInMovie comparison = (GenreInMovie) object; 
		
		if (this.title != null && comparison.getTitle() != null && !this.title.equals(comparison.getTitle()))
			return false; 
		if (this.year != comparison.getYear())
			return false; 
		if (this.director != null && comparison.getDirector() != null && !this.director.equals(comparison.getDirector()))
			return false; 
		if (this.genre != null && comparison.getGenre() != null && !this.genre.equals(comparison.getGenre()))
			return false; 
		if (this.genreID != comparison.getGenreID())
			return false; 
		if (this.movieID != comparison.getMovieID())
			return false; 
		if (this.filmID != null && comparison.getFilmID() != null && !this.filmID.equals(comparison.getFilmID()))
			return false; 
			
		return true; 
		
	}
	
	@Override
	public int hashCode(){
		
		int hash = 7; 
		hash = 31 * hash + (null == this.title ? 0 : this.title.hashCode()); 
		hash = 31 * hash + (null == this.director ? 0 : this.director.hashCode()); 
		hash = 31 * hash + (null == this.genre ? 0 : this.genre.hashCode()); 
		hash = 31 * hash + (null == this.filmID ? 0 : this.filmID.hashCode()); 
		hash = 31 * hash + this.year; 
		hash = 31 * hash + this.genreID; 
		hash = 31 * hash + this.movieID; 
		return hash; 
		
	}

}
