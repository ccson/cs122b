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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.util.*;

import java.util.ArrayList;

public class SingleStar extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_star);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            String result = bundle.getString("result");
            if (result != null) {

                Star star = new Gson().fromJson(result, new TypeToken<Star>(){}.getType());

                TextView starIDTextView = (TextView) findViewById(R.id.starIDTextView);
                starIDTextView.setText(Html.fromHtml("<b>Star ID: </b>" + star.getID()));
                starIDTextView.setTextSize(20);
                starIDTextView.setPadding(20, 0, 0, 0);

                TextView starNameTextView = (TextView) findViewById(R.id.starNameTextView);
                starNameTextView.setText(Html.fromHtml("<b>Star Name: </b>" + star.getFirstName() + " " + star.getLastName()));
                starNameTextView.setTextSize(20);
                starNameTextView.setPadding(20, 0, 0, 0);

                TextView starDOBTextView = (TextView) findViewById(R.id.starDOBTextView);
                if (star.getDOB() == null)
                    starDOBTextView.setText(Html.fromHtml("<b>Star DOB: </b>Not On Record"));
                else
                    starDOBTextView.setText(Html.fromHtml("<b>Star DOB: </b>" + star.getDOB()));
                starDOBTextView.setTextSize(20);
                starDOBTextView.setPadding(20, 0, 0, 0);

                TextView starPictureURLTextView = (TextView) findViewById(R.id.starPictureURLTextView);
                if (star.getPhotoURL() == null)
                    starPictureURLTextView.setText(Html.fromHtml("<b>Star Photo URL: </b>Not On Record"));
                else
                    starPictureURLTextView.setText(Html.fromHtml("<b>Star Photo URL: </b>" + star.getPhotoURL()));
                starPictureURLTextView.setTextSize(20);
                starPictureURLTextView.setPadding(20, 0, 0, 0);


                LinearLayout listOfMovieLayout = (LinearLayout) findViewById(R.id.listOfMovieLayout);

                listOfMovieLayout.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, 1));

                ArrayList<TextView> movieList = new ArrayList<TextView>();

                for (Movie movie : star.getMovies()) {

                    TextView movieTextView = new TextView(this);
                    movieTextView.setPadding(40, 0, 0, 0);
                    movieTextView.setTextSize(20);
                    movieTextView.setTextColor(Color.BLUE);
                    movieTextView.setPaintFlags(movieTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    movieTextView.setText(movie.getTitle());
                    movieTextView.setClickable(true);
                    movieTextView.setOnClickListener(new MovieOnClickListener(movie));
                    movieTextView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                    movieTextView.setVerticalScrollBarEnabled(true);
                    movieTextView.setMovementMethod(new ScrollingMovementMethod());
                    movieTextView.setMaxLines(2);
                    listOfMovieLayout.addView(movieTextView);
                    movieList.add(movieTextView);

                }


            }


        }

    }

    public void searchPageOnClick(View view) {

        Intent resultPage = new Intent(SingleStar.this, SearchPage.class);
        startActivity(resultPage);

    }

}
