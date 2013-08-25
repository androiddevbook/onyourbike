package com.androiddevbook.onyourbike.chapter5.activities;

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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.androiddevbook.onyourbike.chapter5.BuildConfig;
import com.androiddevbook.onyourbike.chapter5.OnYourBike;
import com.androiddevbook.onyourbike.chapter5.R;
import com.androiddevbook.onyourbike.chapter5.helpers.Notify;
import com.androiddevbook.onyourbike.chapter5.model.Settings;
import com.androiddevbook.onyourbike.chapter5.model.TimerState;

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

    private TextView counter;
    private Button start;
    private Button stop;
    private Handler handler;
    private Vibrator vibrate;
    private UpdateTimer updateTimer;
    private final TimerState timer;
    private Notify notify;
    private long lastSeconds;

    public TimerActivity() {
        CLASS_NAME = getClass().getName();
        timer = new TimerState();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(CLASS_NAME, "onCreate");

        // Make sure we do nothing silly!
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll().penaltyLog().build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll().penaltyLog().penaltyDeath().build());
        }

        setContentView(R.layout.activity_timer);

        // NOTE findViewById must be called after setContentView or we'll get an
        // RTE
        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);

        // NOTE some phone and tablets may not vibrate
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (vibrate == null) {
            Log.w(CLASS_NAME, "No vibration service exists.");
        }

        timer.reset();

        stayAwakeOrNot();

        // NOTE Needs to be created here not in constructor or you'll get an RTE
        notify = new Notify(this);
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

        timer.start();

        enableButtons();

        handler = new Handler();
        updateTimer = new UpdateTimer(this);
        handler.postDelayed(updateTimer, UPDATE_EVERY);
    }

    /**
     * Called when the stop button is clicked on.
     * 
     * @param view
     * the button click on
     */
    public void clickedStop(View view) {
        Log.d(CLASS_NAME, "clickedStop");

        timer.stop();

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

        Intent settings = new Intent(this, SettingsActivity.class);

        startActivity(settings);
    }

    private void clickedRoutes() {
        Log.d(CLASS_NAME, "clickedRoutes");

        Intent routes = new Intent(this, RoutesActivity.class);

        startActivity(routes);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(CLASS_NAME, "onStart");

        if (timer.isRunning()) {
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

        start.setEnabled(!timer.isRunning());
        stop.setEnabled(timer.isRunning());
    }

    /**
     * Change the counter text view to display the current formatted time
     */
    protected void setTimeDisplay() {
        Log.d(CLASS_NAME, "setTimeDisplay");

        counter.setText(timer.display());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(CLASS_NAME, "onResume");
        enableButtons();
        setTimeDisplay();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(CLASS_NAME, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(CLASS_NAME, "onStop");

        Settings settings = ((OnYourBike) getApplication()).getSettings();

        if (timer.isRunning()) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(CLASS_NAME, "onDestroy");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(CLASS_NAME, "onRestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(CLASS_NAME, "onCreateOptionsMenu");

        getMenuInflater().inflate(R.menu.activity_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_settings:
            clickedSettings(null);
            return true;
        case R.id.menu_routes:
            clickedRoutes();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Keep the screen on depending on the stay awake setting
     */
    protected void stayAwakeOrNot() {
        Log.d(CLASS_NAME, "stayAwakeOrNot");

        Settings settings = ((OnYourBike) getApplication()).getSettings();

        if (settings.isCaffeinated(this)) {
            // Log.i(CLASS_NAME, "Staying awake");
            getWindow()
                    .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            // Log.i(CLASS_NAME, "Not staying awake");
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    // NOTE could be an anonymous inner class - easier to understand this way
    class UpdateTimer implements Runnable {

        Activity activity;

        public UpdateTimer(Activity activity) {
            this.activity = activity;
        }

        /**
         * Updates the counter display and vibrated if needed
         */
        public void run() {
            // Log.d(CLASS_NAME, display);
            Settings settings = ((OnYourBike) getApplication()).getSettings();

            setTimeDisplay();

            if (timer.isRunning()) {
                if (settings.isVibrateOn(activity)) {
                    vibrateCheck();
                }
                notifyCheck();
            }

            stayAwakeOrNot();

            if (handler != null) {
                handler.postDelayed(this, UPDATE_EVERY);
            }
        }

        protected void vibrateCheck() {
            long seconds;
            long minutes;

            Log.d(CLASS_NAME, "vibrateCheck");

            timer.elapsedTime();
            seconds = timer.seconds();
            minutes = timer.minutes();

            // NOTE done this way to avoid Array/ArrayList issues
            // NOTE hasVibrator() only on API 11+
            // NOTE try/catch to stop force close on emulator
            // NOTE very easy to get manifest wrong!
            try {
                // NOTE seconds != lastSeconds so it only vibrates once/second
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
            } catch (Exception e) {
                Log.w(CLASS_NAME, "Exception: " + e.getMessage());
            }

            lastSeconds = seconds;
        }

        protected void notifyCheck() {
            long seconds;
            long minutes;
            long hours;

            Log.d(CLASS_NAME, "notifyCheck");

            timer.elapsedTime();
            seconds = timer.seconds();
            minutes = timer.minutes();
            hours = timer.hours();

            if (minutes % 15 == 0 && seconds == 0 && seconds != lastSeconds) {
                String title = getResources().getString(R.string.time_title);
                String message = getResources().getString(
                        R.string.time_running_message);

                if (hours == 0 && minutes == 0) {
                    message = getResources().getString(
                            R.string.time_start_message);
                }

                message = String.format(message, hours, minutes);

                notify.notify(title, message);
            }

            lastSeconds = seconds;
        }
    }
}
