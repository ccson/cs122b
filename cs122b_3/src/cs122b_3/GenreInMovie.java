package cs122b_3;

public class GenreInMovie {
	
	private String title; 
	private int year; 
	private String director;
	private String genre; 
	
	public GenreInMovie() { }
	
	public GenreInMovie(String title, int year, String director){
		
		this.title = title; 
		this.year = year; 
		this.director = director; 
		
	}
	
	public GenreInMovie(String genre){
		
		this.genre = genre; 
		
	}
	
	public String getTitle() { return title; }
	public int getYear() { return year; }
	public String getDirector() { return director; }
	public String getGenre() { return genre; }
	
	public void setTitle(String title) { this.title = title; }
	public void setYear(int year) { this.year = year; }
	public void setDirector(String director) { this.director = director; }
	public void setGenre(String genre) { this.genre = genre; }

}
