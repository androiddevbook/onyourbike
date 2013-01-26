package com.androiddevbook.onyourbike.chapter3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    static String className;

    public MainActivity() {
        className = getClass().getName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView hello = (TextView) findViewById(R.id.hello);

        Log.d(className, "Setting text.");

        hello.setText("On your bike!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d(className, "Showing menu.");

        getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }
}
