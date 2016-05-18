package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.ScrollingMovementMethod;
import java.util.*;
import com.google.gson.*;
import org.json.*;
import com.google.gson.reflect.*;
import android.widget.*;
import android.graphics.Color;
import android.view.View;
import android.graphics.*;
import android.util.Log;
import android.support.v7.app.ActionBar.LayoutParams;

public class SearchResultPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            String result = bundle.getString("result");
            if(result != null){

                Log.d("Response", result);
                AndroidClass listOfMoviesFromServer = new Gson().fromJson(result, new TypeToken<AndroidClass>(){}.getType());

                TextView titleQueryTextViewLayout = (TextView) findViewById(R.id.titleQueryTextView);
                titleQueryTextViewLayout.setText("Results For Query: \"" + listOfMoviesFromServer.getTitleQuery() + "\"");
                titleQueryTextViewLayout.setTextSize(30);
                titleQueryTextViewLayout.setTypeface(null, Typeface.BOLD);

                TextView numberOfResultsTextView = (TextView) findViewById(R.id.numberOfResultsTextView);
                numberOfResultsTextView.setText("Number of Results: " + listOfMoviesFromServer.getNumberOfMovies());
                numberOfResultsTextView.setTextSize(30);
                numberOfResultsTextView.setTypeface(null, Typeface.BOLD);

                TextView pageNumberTextView = (TextView) findViewById(R.id.pageNumberTextView);
                if (listOfMoviesFromServer.getNumberOfMovies() == 0)
                    pageNumberTextView.setText("Page Number: " + listOfMoviesFromServer.getCurrentPageNumber() + " ||| <----------> ||| " + "0 out of " + listOfMoviesFromServer.getNumberOfMovies());
                else if (((listOfMoviesFromServer.getCurrentPageNumber() - 1) * 5 + 1) >= listOfMoviesFromServer.getNumberOfMovies())
                    pageNumberTextView.setText("Page Number: " + listOfMoviesFromServer.getCurrentPageNumber() + " ||| <----------> ||| " + ((listOfMoviesFromServer.getCurrentPageNumber() - 1) * 5 + 1) + " - " + listOfMoviesFromServer.getNumberOfMovies() + " out of " + listOfMoviesFromServer.getNumberOfMovies());
                else if (listOfMoviesFromServer.getListOfMovies().size() < 5)
                    pageNumberTextView.setText("Page Number: " + listOfMoviesFromServer.getCurrentPageNumber() + " ||| <----------> ||| 1 - " + listOfMoviesFromServer.getListOfMovies().size() + " out of " + listOfMoviesFromServer.getNumberOfMovies());
                else
                    pageNumberTextView.setText("Page Number: " + listOfMoviesFromServer.getCurrentPageNumber() + " ||| <----------> ||| " + ((listOfMoviesFromServer.getCurrentPageNumber() - 1) * 5 + 1) + " - " + listOfMoviesFromServer.getCurrentPageNumber() * 5 + " out of " + listOfMoviesFromServer.getNumberOfMovies());
                pageNumberTextView.setTextSize(20);
                pageNumberTextView.setTypeface(null, Typeface.BOLD);

                ArrayList<TextView> textList = new ArrayList<TextView>();
                LinearLayout movieResultPageLayout = (LinearLayout) findViewById(R.id.movieSearchResultLinearLayout);
                movieResultPageLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT, 1));

                for (int i = 0; i < 5; i++) {

                    if (i >= listOfMoviesFromServer.getListOfMovies().size())
                        break;

                    TextView movieTextView = new TextView(this);
                    movieTextView.setPadding(0, 5, 0, 5);
                    movieTextView.setTextSize(25);
                    movieTextView.setTextColor(Color.BLUE);
                    movieTextView.setPaintFlags(movieTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    movieTextView.setText(listOfMoviesFromServer.getListOfMovies().get(i).getTitle());
                    movieTextView.setClickable(true);
                    movieTextView.setOnClickListener(new MovieOnClickListener(listOfMoviesFromServer.getListOfMovies().get(i)));
                    movieTextView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
                    movieTextView.setVerticalScrollBarEnabled(true);
                    movieTextView.setMovementMethod(new ScrollingMovementMethod());
                    movieTextView.setMaxLines(2);
                    movieResultPageLayout.addView(movieTextView);
                    textList.add(movieTextView);

                }

                Button nextButton = (Button) findViewById(R.id.nextButton);
                if (listOfMoviesFromServer.getCurrentPageNumber() * 5 >= listOfMoviesFromServer.getNumberOfMovies())
                    nextButton.setVisibility(View.INVISIBLE);
                else
                    nextButton.setOnClickListener(new MovieNextButtonOnClickListener(listOfMoviesFromServer));

                Button previousButton = (Button) findViewById(R.id.previousButton);
                if (listOfMoviesFromServer.getCurrentPageNumber() == 1)
                    previousButton.setVisibility(View.INVISIBLE);
                else
                    previousButton.setOnClickListener(new MoviePreviousButtonOnClickListener(listOfMoviesFromServer));


            }

        }

    }

    public void searchPageOnClick(View view) {

        Intent resultPage = new Intent(SearchResultPage.this, SearchPage.class);
        startActivity(resultPage);

    }

}
