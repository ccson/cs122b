package cs122b_3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;

public class SAXStarParser extends DefaultHandler{
	
	private StarInMovie tempStarInMovie; 
	private ArrayList<StarInMovie> starInMovieList; 
	
	boolean bTitle = false; 
	boolean bActor = false; 
	
	public SAXStarParser(){
		
		starInMovieList = new ArrayList<StarInMovie>(); 
		
	}
	
	public void runExample() {
		parseDocument();
		printData(); 
	}

	private void parseDocument() {
		
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			SAXParser sp = spf.newSAXParser();
			sp.parse("/Users/Colin_Son/Downloads/stanford-movies/casts124.xml", this);  
			
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
			
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		
		if (bTitle){
			
			tempStarInMovie.setMovieTitle(new String(ch, start, length));
			bTitle = false; 
			
		}
		
		else if (bActor){
			
			tempStarInMovie.setActorName(new String(ch, start, length));
			bActor = false; 
			
		}
		
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (qName.equalsIgnoreCase("m")){
			
			starInMovieList.add(tempStarInMovie); 
			tempStarInMovie = null; 
			
		}
		
	}
	
	private void printData(){
		
		for (int i = 0; i < starInMovieList.size(); i++){
			
			System.out.println(starInMovieList.get(i).getActorName() + " --- " + starInMovieList.get(i).getMovieTitle()); 
			
		}
		
		System.out.println(starInMovieList.size());
		
	}
	
	public static void main(String[] args){
		
		SAXStarParser spe = new SAXStarParser();
		spe.runExample();
		
	}

}
