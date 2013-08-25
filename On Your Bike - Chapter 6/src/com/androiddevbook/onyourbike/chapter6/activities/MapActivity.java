package com.androiddevbook.onyourbike.chapter6.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.androiddevbook.onyourbike.chapter6.R;

public class MapActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_map, menu);
        return true;
    }

}
