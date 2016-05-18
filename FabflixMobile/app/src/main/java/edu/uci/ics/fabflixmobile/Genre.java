package edu.uci.ics.fabflixmobile;

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

}
