package com.androiddevbook.onyourbike.chapter10.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.androiddevbook.onyourbike.chapter10.BuildConfig;
import com.androiddevbook.onyourbike.chapter10.R;
import com.androiddevbook.onyourbike.chapter10.helpers.Camera;
import com.androiddevbook.onyourbike.chapter10.helpers.NightMode;
import com.androiddevbook.onyourbike.chapter10.helpers.Notify;

/**
 * BaseActivity
 * 
 * Routes Activity for the "On Your Bike" application.
 * Copyright [2012] Pearson Education, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @author androiddevbook.com
 * @version 1.0
 */
public class BaseActivity extends Activity {

    private static String CLASS_NAME;

    private NightMode nightMode;
    private Notify notify;

    public boolean canGoHome = true;

    public BaseActivity() {
        CLASS_NAME = getClass().getName();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(canGoHome);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make sure we do nothing silly!
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll().penaltyLog().build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll().penaltyLog().penaltyDeath().build());
        }

        int themeID = getIntent().getIntExtra("Theme", -1);
        if (themeID != -1) {
            setTheme(themeID);
        }
        boolean isLightTheme = getIntent()
                .getBooleanExtra("isLightTheme", true);

        notify = new Notify(this);

        nightMode = new NightMode(getWindow(), this, true, isLightTheme);

        setupActionBar();
    }

    @Override
    public void onResume() {
        super.onResume();

        // nightMode.startSensing();
    }

    @Override
    public void onPause() {
        super.onPause();

        // nightMode.stopSensing();
    }

    private void gotoRoutes() {
        Log.d(CLASS_NAME, "gotoRoutes");

        Intent routes = new Intent(this, RoutesActivity.class);

        startActivity(routes);
    }

    /**
     * Called when the settings button is clicked on.
     * 
     * @param view
     * the button clicked on
     */
    public void gotoSettings(View view) {
        Log.d(CLASS_NAME, "gotoSettings");

        Intent settings = new Intent(this, SettingsActivity.class);

        startActivity(settings);
    }

    private void gotoHome() {
        Log.d(CLASS_NAME, "gotoHome");

        Intent timer = new Intent(this, TimerActivity.class);
        timer.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(timer);
    }

    public void gotoMap() {
        Log.d(CLASS_NAME, "gotoMap");

        Intent map = new Intent(this, MapActivity.class);

        startActivity(map);
    }

    public void gotoPhoto() {
        Log.d(CLASS_NAME, "gotoPhoto");

        Intent photo = new Intent(this, PhotoActivity.class);

        startActivity(photo);
    }

    public void shareActivity() {
        Log.d(CLASS_NAME, "shareActivity");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            gotoHome();
            return true;
        case R.id.menu_settings:
            gotoSettings(null);
            return true;
        case R.id.menu_routes:
            gotoRoutes();
            return true;
        case R.id.menu_map:
            gotoMap();
            return true;
        case R.id.menu_photo:
            gotoPhoto();
            return true;
        case R.id.menu_share:
            shareActivity();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void goBack(View view) {
        finish();
    }

    public void notification(String title, String message) {
        notify.notify(title, message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
            Intent intent) {

        Log.d(CLASS_NAME, "onActivityResult");

        switch (requestCode) {
        case Camera.PHOTO_TAKEN:
            if (resultCode == RESULT_OK) {
                PhotoActivity photoActivity = (PhotoActivity) this;
                photoActivity.displayPhoto(intent);
            }
        default:
            Log.d(CLASS_NAME,
                    "Unknown request code " + Integer.toString(requestCode));
        }
    }
}
