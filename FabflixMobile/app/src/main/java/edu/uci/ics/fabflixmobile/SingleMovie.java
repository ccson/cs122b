package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.*;
import com.google.gson.reflect.*;

import java.util.ArrayList;
import android.util.*;

public class SingleMovie extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            String result = bundle.getString("result");
            if (result != null) {

                Movie movie = new Gson().fromJson(result, new TypeToken<Movie>(){}.getType());

                TextView movieIDTextView = (TextView) findViewById(R.id.movieIDTextView);
                movieIDTextView.setText(Html.fromHtml("<b>Movie ID: </b>" + movie.getID()));
                movieIDTextView.setTextSize(20);
                movieIDTextView.setPadding(20, 0, 0, 0);

                TextView movieTitleTextView = (TextView) findViewById(R.id.movieTitleTextView);
                movieTitleTextView.setText(Html.fromHtml("<b>Movie Title: </b>" + movie.getTitle()));
                movieTitleTextView.setTextSize(20);
                movieTitleTextView.setPadding(20, 0, 0, 0);
                movieTitleTextView.setMaxLines(2);

                TextView movieYearTextView = (TextView) findViewById(R.id.movieYearTextView);
                movieYearTextView.setText(Html.fromHtml("<b>Movie Year: </b>" + movie.getYear()));
                movieYearTextView.setTextSize(20);
                movieYearTextView.setPadding(20, 0, 0, 0);
                movieYearTextView.setMaxLines(2);

                TextView movieDirectorTextView = (TextView) findViewById(R.id.movieDirectorTextView);
                movieDirectorTextView.setText(Html.fromHtml("<b>Movie Director: </b>" + movie.getDirector()));
                movieDirectorTextView.setTextSize(20);
                movieDirectorTextView.setPadding(20, 0, 0, 0);
                movieDirectorTextView.setMaxLines(2);

                TextView bannerURLTextView = (TextView) findViewById(R.id.bannerURLTextView);
                if (movie.getBannerURL() == null)
                    bannerURLTextView.setText(Html.fromHtml("<b>Movie ID: </b>Not On Record"));
                else
                    bannerURLTextView.setText(Html.fromHtml("<b>Movie Banner URL: </b>" + movie.getBannerURL()));
                bannerURLTextView.setTextSize(20);
                bannerURLTextView.setPadding(20, 0, 0, 0);
                bannerURLTextView.setMaxLines(3);

                TextView trailerURLTextView = (TextView) findViewById(R.id.trailerURLTextView);
                if (movie.getBannerURL() == null)
                    trailerURLTextView.setText(Html.fromHtml("<b>Movie Trailer URL: </b>Not On Record"));
                else
                    trailerURLTextView.setText(Html.fromHtml("<b>Movie Trailer URL: </b>" + movie.getTrailerURL()));
                trailerURLTextView.setTextSize(20);
                trailerURLTextView.setPadding(20, 0, 0, 0);
                trailerURLTextView.setMaxLines(3);

                LinearLayout listOfStarsLayout = (LinearLayout) findViewById(R.id.listOfStarsLayout);
                listOfStarsLayout.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, 1));
                ArrayList<TextView> starList = new ArrayList<TextView>();
                for (Star star : movie.getStars()){

                    TextView starTextView = new TextView(this);
                    starTextView.setPadding(40, 0, 0, 0);
                    starTextView.setTextSize(20);
                    starTextView.setTextColor(Color.BLUE);
                    starTextView.setPaintFlags(starTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    starTextView.setText(star.getFirstName() + " " + star.getLastName());
                    starTextView.setClickable(true);
                    starTextView.setOnClickListener(new StarOnClickListener(star));
                    starTextView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                    starTextView.setVerticalScrollBarEnabled(true);
                    starTextView.setMovementMethod(new ScrollingMovementMethod());
                    starTextView.setMaxLines(2);
                    listOfStarsLayout.addView(starTextView);
                    starList.add(starTextView);

                }

                LinearLayout listOfGenresLayout = (LinearLayout) findViewById(R.id.listOfGenresLayout);
                ArrayList<TextView> genreList = new ArrayList<TextView>();
                for (Genre genre : movie.getGenres()){

                    TextView genreTextView = new TextView(this);
                    genreTextView.setPadding(40, 0, 0, 0);
                    genreTextView.setTextSize(20);
                    genreTextView.setText("-> " + genre.getName());
                    genreTextView.setMaxLines(1);
                    listOfGenresLayout.addView(genreTextView);
                    genreList.add(genreTextView);

                }


            }


        }

    }

    public void searchPageOnClick(View view) {

        Intent resultPage = new Intent(SingleMovie.this, SearchPage.class);
        startActivity(resultPage);

    }

}
