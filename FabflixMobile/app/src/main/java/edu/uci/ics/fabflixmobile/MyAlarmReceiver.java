package edu.uci.ics.fabflixmobile;

import android.content.*;
import android.widget.*;

public class MyAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Cannot Search For Empty Search Input", Toast.LENGTH_SHORT).show();

    }

}