package edu.uci.ics.fabflixmobile;

import android.content.Context;
import android.net.Uri;
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

import android.app.AlarmManager;
import java.util.Calendar;
import android.app.PendingIntent;

public class LoginPage extends ActionBarActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            String result = bundle.getString("result");
            if(result != null && result.trim().equals("false")){
                AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(this, LoginAlarm.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
                Calendar time = Calendar.getInstance();
                time.setTimeInMillis(System.currentTimeMillis());
                time.add(Calendar.MILLISECOND, 0);
                alarmMgr.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);
            }
        }

    }

    public void loginToFabflix(View view) {

        //

        final Map<String, String> params = new HashMap<String, String>();


        // no user is logged in, so we must connect to the server
        RequestQueue queue = Volley.newRequestQueue(this);

        final Context context = this;
        EditText emaileditText = (EditText) findViewById(R.id.emailTextBox);
        EditText passwordeditText = (EditText) findViewById(R.id.passwordTextBox);
        String email = emaileditText.getText().toString();
        String pw = passwordeditText.getText().toString();

//        String url = "http://10.0.2.2:8080/project3_29/android/login?email=" + email + "&" + "pw=" + pw;
        String url = "http://52.34.97.23:8080/project4/android/login?email=" + email + "&" + "pw=" + pw;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("true")) {
                            Intent resultPage = new Intent(LoginPage.this, SearchPage.class);
                            startActivity(resultPage);
                        }
                        else{
                            Intent resultPage = new Intent(LoginPage.this, LoginPage.class);
                            resultPage.putExtra("result", response);
                            startActivity(resultPage);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("security.error", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

        };


        // Add the request to the RequestQueue.
        queue.add(postRequest);


        return;
    }



}
