package edu.uci.ics.fabflixmobile;

import android.content.*;
import android.widget.*;

public class LoginAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Incorrect Login Information", Toast.LENGTH_SHORT).show();

    }

}