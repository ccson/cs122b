package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.view.View.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MovieOnClickListener implements OnClickListener {

    Movie movie;

    public MovieOnClickListener(Movie movie){

        this.movie = movie;

    }

    @Override
    public void onClick(View v){

        getMovieInformation(v);
    }

    public void getMovieInformation(final View view){

        final Map<String, String> params = new HashMap<String, String>();

        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final Context context = view.getContext();
        String movieID = Integer.toString(movie.getID());

//        String url = "http://10.0.2.2:8080/project3_29/android/singlemovie?movieID=" + movieID;
        String url = "http://52.34.97.23:8080/project4/android/singlemovie?movieID=" + movieID;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Intent resultPage = new Intent(view.getContext(), SingleMovie.class);
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
