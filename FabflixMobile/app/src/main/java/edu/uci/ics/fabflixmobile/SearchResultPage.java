package edu.uci.ics.fabflixmobile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.ScrollingMovementMethod;

public class SearchResultPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            String result = bundle.getString("result");
            if(result != null && !"".equals(result)){
                ((TextView)findViewById(R.id.searchResult)).setText(result);
                ((TextView)findViewById(R.id.searchResult)).setMovementMethod(new ScrollingMovementMethod());
            }

        }

    }

}
