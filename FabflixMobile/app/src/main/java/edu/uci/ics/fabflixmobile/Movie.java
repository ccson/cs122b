package edu.uci.ics.fabflixmobile;

import java.sql.*;
import java.util.*;

public class Movie {

    private int id;
    private String title;
    private int year;
    private String director;
    private String bannerURL;
    private String trailerURL;
    private ArrayList<Genre> genres;
    private ArrayList<Star> stars;

    public Movie(int id, String title) {

        this.id = id;
        this.title = title;

    }

    public Movie(int id, String title, int year, String director, ArrayList<Genre> genres, ArrayList<Star> stars) {

        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.genres = new ArrayList<Genre>(genres);
        this.stars = new ArrayList<Star>(stars);

    }

    public Movie(int id, String title, int year, String director, ArrayList<Genre> genres, ArrayList<Star> stars, String bannerURL, String trailerURL) {

        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.genres = new ArrayList<Genre>(genres);
        this.stars = new ArrayList<Star>(stars);
        this.bannerURL = bannerURL;
        this.trailerURL = trailerURL;

    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public ArrayList<Star> getStars() {
        return stars;
    }

    public String getBannerURL() {
        return bannerURL;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

}

