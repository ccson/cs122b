package MyClasses; 

import java.util.*;

public class AndroidClass {
	
	private ArrayList<Movie> listOfMovies; 
	private int numberOfMovies; 
	private String titleQuery; 
	private int currentPageNumber; 
	
	public AndroidClass(ArrayList<Movie> listOfMovies, int numberOfMovies, String titleQuery, int currentPageNumber){
		
		this.listOfMovies = new ArrayList<Movie>(listOfMovies); 
		this.numberOfMovies = numberOfMovies; 
		this.titleQuery = titleQuery; 
		this.currentPageNumber = currentPageNumber; 
		
	}
	
	public void setCurrentPageNumber(int currentPageNumber) { this.currentPageNumber = currentPageNumber; }
	
	public ArrayList<Movie> getListOfMovies() { return listOfMovies; }
	public int getNumberOfMovies() { return numberOfMovies; }
	public String getTitleQuery() { return titleQuery; }
	public int getCurrentPageNumber() { return currentPageNumber; }
	
}