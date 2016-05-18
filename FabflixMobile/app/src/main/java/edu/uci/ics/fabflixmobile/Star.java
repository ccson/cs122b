package edu.uci.ics.fabflixmobile;

import java.util.*;
import java.sql.*;


public class Star {

    private int id;
    private String firstName;
    private String lastName;
    private String dob;
    private String photoURL;
    private ArrayList<Movie> movies;
    private String name;

    public Star(int id, String firstName, String lastName) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + " " + lastName;

    }

    public Star(int id, String firstName, String lastName, String dob, String photoURL, ArrayList<Movie> movies) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.photoURL = photoURL;
        this.movies = new ArrayList<Movie>(movies);
        this.name = firstName + " " + lastName;

    }

    public int getID() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDOB() {
        return dob;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public String getName() {
        return name;
    }

}

