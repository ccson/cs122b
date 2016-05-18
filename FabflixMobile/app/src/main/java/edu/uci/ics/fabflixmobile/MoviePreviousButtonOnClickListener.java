package edu.uci.ics.fabflixmobile;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.*;

import java.util.HashMap;
import java.util.Map;

public class MoviePreviousButtonOnClickListener implements View.OnClickListener {

    AndroidClass movie;

    public MoviePreviousButtonOnClickListener(AndroidClass movie){

        this.movie = new AndroidClass(movie.getListOfMovies(), movie.getNumberOfMovies(), movie.getTitleQuery(), movie.getCurrentPageNumber());

    }

    @Override
    public void onClick(View v){

        connectToFabflix(v);

    }

    public void connectToFabflix(final View view){

        final Map<String, String> params = new HashMap<String, String>();

        RequestQueue queue = Volley.newRequestQueue(view.getContext());

//          String url = "https://52.34.97.23:8443/fabfix-android/android/moviesearch?pageNumber=" + (movie.getCurrentPageNumber() - 1)  + "&title=" + movie.getTitleQuery();
        String url = "http://10.0.2.2:8080/project3_29/android/moviesearch?pageNumber=" + (movie.getCurrentPageNumber() - 1)  + "&title=" + movie.getTitleQuery();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Intent resultPage = new Intent(view.getContext(), SearchResultPage.class);
                        resultPage.putExtra("result", response);
                        view.getContext().startActivity(resultPage);

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }

        };

        queue.add(postRequest);


        return ;
    }

};
