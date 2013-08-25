package com.androiddevbook.onyourbike.chapter4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.StrictMode;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * TimerActivity
 * 
 * Timer Activity for the "On Your Bike" application.
 * 
 * Copyright [2013] Pearson Education, Inc
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
public class TimerActivity extends Activity {

    private static String CLASS_NAME;
    private static long UPDATE_EVERY = 200;

    // Anything need to be private?
    protected TextView counter;
    protected Button start;
    protected Button stop;
    protected long startedAt;
    protected long lastStopped;
    protected long lastSeconds;
    protected Handler handler;
    protected Vibrator vibrate;
    protected boolean timerRunning;
    protected UpdateTimer updateTimer;

    public TimerActivity() {
        CLASS_NAME = getClass().getName();
    }

    /**
     * Called when the Activity is created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(CLASS_NAME, "onCreate");

        // Make sure we do nothing naughty!
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll().penaltyLog().build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll().penaltyLog().penaltyDeath().build());
        }

        setContentView(R.layout.activity_timer);

        // note findViewById must be called after setContentView or we'll get an
        // RTE
        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);

        // NOTE some phone and tablets may not vibrate
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (vibrate == null) {
            Log.w(CLASS_NAME, "No vibration service exists.");
        }

        // So 0:00:00 is displayed
        timerRunning = false;
        startedAt = System.currentTimeMillis();
        lastStopped = 0;

        stayAwakeOrNot();
    }

    // Note must be public or RTE

    /**
     * Called when the start button is clicked on.
     * 
     * @param view
     * the button clicked on
     */
    public void clickedStart(View view) {
        Log.d(CLASS_NAME, "clickedStart");

        timerRunning = true;
        startedAt = System.currentTimeMillis();

        enableButtons();

        handler = new Handler();
        updateTimer = new UpdateTimer(this);
        handler.postDelayed(updateTimer, UPDATE_EVERY);
    }

    /**
     * Called when the stop button is clicked on.
     * 
     * @param view
     * the button clicked on
     */
    public void clickedStop(View view) {
        Log.d(CLASS_NAME, "clickedStop");

        timerRunning = false;
        lastStopped = System.currentTimeMillis();

        enableButtons();

        handler.removeCallbacks(updateTimer);
        updateTimer = null;
        handler = null;
    }

    /**
     * Called when the settings button is clicked on.
     * 
     * @param view
     * the button clicked on
     */
    public void clickedSettings(View view) {
        Log.d(CLASS_NAME, "clickedSettings");

        Intent settings = new Intent(getApplicationContext(),
                SettingsActivity.class);

        startActivity(settings);
    }

    /**
     * Called when the Activity starts.
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.d(CLASS_NAME, "onStart");

        if (timerRunning) {
            handler = new Handler();
            updateTimer = new UpdateTimer(this);
            handler.postDelayed(updateTimer, UPDATE_EVERY);
        }
    }

    /**
     * Enable/disable the stop and start buttons
     */
    protected void enableButtons() {
        Log.d(CLASS_NAME, "enableButtons");

        start.setEnabled(!timerRunning);
        stop.setEnabled(timerRunning);
    }

    /**
     * Change the counter text view to display the current formatted time
     */
    protected void setTimeDisplay() {
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;

        Log.d(CLASS_NAME, "setTimeDisplay");

        if (timerRunning) {
            timeNow = System.currentTimeMillis();
        } else {
            timeNow = lastStopped;
        }

        diff = timeNow - startedAt;

        // no negative time
        if (diff < 0) {
            diff = 0;
        }

        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;

        display = String.format("%d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds);

        Log.i(CLASS_NAME, "Time is " + display);

        counter.setText(display);
    }

    /**
     * Called when the Activity is resumed.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d(CLASS_NAME, "onResume");

        enableButtons();
        setTimeDisplay();
    }

    /**
     * Called when the Activity is paused.
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d(CLASS_NAME, "onPause");
    }

    /**
     * Called when the Activity is stopped.
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.d(CLASS_NAME, "onStop");

        Settings settings = ((OnYourBike) getApplication()).getSettings();

        if (timerRunning) {
            handler.removeCallbacks(updateTimer);
            updateTimer = null;
            handler = null;
        }

        if (settings.isCaffeinated(this)) {
            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
                    PowerManager.FULL_WAKE_LOCK, CLASS_NAME);

            if (wakeLock.isHeld()) {
                wakeLock.release();
            }
        }
    }

    /**
     * Called when the Activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(CLASS_NAME, "onDestroy");
    }

    /**
     * Called when the Activity is restarted.
     */
    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(CLASS_NAME, "onRestart");
    }

    /**
     * Called when the Menu is to be populated.
     * 
     * @param menu
     * menu to add menu items to
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(CLASS_NAME, "onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.activity_settings, menu);

        return true;
    }

    /**
     * Keep the screen on depending of the saved stay awake setting
     */
    protected void stayAwakeOrNot() {
        Log.d(CLASS_NAME, "stayAwakeOrNot");

        Settings settings = ((OnYourBike) getApplication()).getSettings();

        if (settings.isCaffeinated(this)) {
            Log.i(CLASS_NAME, "Staying awake");
            getWindow()
                    .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            Log.i(CLASS_NAME, "Not staying awake");
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    /**
     * UpdateTimer class to display and update a counter on the screen
     */
    class UpdateTimer implements Runnable {

        Activity activity;

        public UpdateTimer(Activity activity) {
            this.activity = activity;
        }

        /**
         * Updates the counter display and vibrate if needed.
         * Is called at regular intervals.
         */
        public void run() {
            // Log.d(CLASS_NAME, "run");
            Settings settings = ((OnYourBike) getApplication()).getSettings();

            setTimeDisplay();

            if (timerRunning && settings.isVibrateOn(activity)) {
                vibrateCheck();
            }

            stayAwakeOrNot();

            if (handler != null) {
                handler.postDelayed(this, UPDATE_EVERY);
            }
        }

        /**
         * Check time and vibrate if at right time.
         */
        protected void vibrateCheck() {
            long timeNow = System.currentTimeMillis();
            long diff = timeNow - startedAt;
            long seconds = diff / 1000;
            long minutes = seconds / 60;

            Log.d(CLASS_NAME, "vibrateCheck");

            seconds = seconds % 60;
            minutes = minutes % 60;

            // NOTE done this way to avoid Array/ArrayList issues
            // NOTE hasVibrator() only on API 11+
            // NOTE very easy to get manifest wrong!
            // NOTE seconds != lastSeconds so it dosn't vibrate
            // multiple times a second
            if (vibrate != null && seconds == 0 && seconds != lastSeconds) {
                long[] once = { 0, 100 };
                long[] twice = { 0, 100, 400, 100 };
                long[] thrice = { 0, 100, 400, 100, 400, 100 };

                // every hour
                if (minutes == 0) {
                    Log.i(CLASS_NAME, "Vibrate 3 times");
                    vibrate.vibrate(thrice, -1);
                }
                // every 15 minutes
                else if (minutes % 15 == 0) {
                    Log.i(CLASS_NAME, "Vibrate 2 time");
                    vibrate.vibrate(twice, -1);
                }
                // every 5 minutes
                else if (minutes % 5 == 0) {
                    Log.i(CLASS_NAME, "Vibrate once");
                    vibrate.vibrate(once, -1);
                }
            }

            lastSeconds = seconds;
        }
    }
}
