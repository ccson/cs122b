package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import android.widget.EditText;

public class SearchPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

    }

    public void connectToFabflix(View view){

        //

        final Map<String, String> params = new HashMap<String, String>();


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        EditText editText = (EditText)findViewById(R.id.searchTextBox);
        String query = editText.getText().toString();
        String url = "http://10.0.2.2:8080/project3_29/android/moviesearch?title=" + query;


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Intent resultPage = new Intent(SearchPage.this, SearchResultPage.class);
                        resultPage.putExtra("result", response);
                        startActivity(resultPage);

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


        // Add the request to the RequestQueue.
        queue.add(postRequest);


        return ;
    }

}
