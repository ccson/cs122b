package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class StarOnClickListener implements View.OnClickListener {

    Star star;

    public StarOnClickListener(Star star){

        this.star = star;

    }

    @Override
    public void onClick(View v){

        getStarInformation(v);

    }

    public void getStarInformation(final View view){

        final Map<String, String> params = new HashMap<String, String>();

        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final Context context = view.getContext();
        String starID = Integer.toString(star.getID());
        String url = "http://10.0.2.2:8080/project3_29/android/singlestar?starID=" + starID;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Intent resultPage = new Intent(view.getContext(), SingleStar.class);
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
