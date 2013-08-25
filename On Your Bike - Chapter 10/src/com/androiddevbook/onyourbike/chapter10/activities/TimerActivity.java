package com.androiddevbook.onyourbike.chapter10.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.androiddevbook.onyourbike.chapter10.IOnYourBike;
import com.androiddevbook.onyourbike.chapter10.R;
import com.androiddevbook.onyourbike.chapter10.helpers.Camera;
import com.androiddevbook.onyourbike.chapter10.model.Settings;
import com.androiddevbook.onyourbike.chapter10.model.Trip;

/**
 * TimerActivity
 * 
 * Main Activity for the "On Your Bike" application.
 * 
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
public class TimerActivity extends BaseActivity {

    private static String CLASS_NAME;

    private static long UPDATE_EVERY = 200;

    private TextView counter;
    private Button start;
    private Button stop;
    private Button takePhoto;
    private Handler handler;
    private UpdateTimer updateTimer;
    private IOnYourBike application;
    private Camera camera;

    public TimerActivity() {
        CLASS_NAME = getClass().getName();
        canGoHome = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(CLASS_NAME, "onCreate");

        setContentView(R.layout.activity_timer);

        // NOTE findViewById must be called after setContentView or we'll get an
        // RTE
        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);
        takePhoto = (Button) findViewById(R.id.photo_button);

        application = ((IOnYourBike) getApplication());

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

        application.startTimer(new Trip());

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

        application.stopTimer();

        enableButtons();

        handler.removeCallbacks(updateTimer);
        updateTimer = null;
        handler = null;
    }

    public void takePhoto(View view) {
        Log.d(CLASS_NAME, "takePhoto");

        Intent photo = new Intent(this, PhotoActivity.class);
        startActivity(photo);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(CLASS_NAME, "onStart");

        camera = new Camera(this);

        if (application.isTimerRunning()) {
            handler = new Handler();
            updateTimer = new UpdateTimer(this);
            handler.postDelayed(updateTimer, UPDATE_EVERY);
        }

        if (!camera.hasCamera()) {
            takePhoto.setVisibility(View.GONE);
        }

        if (!camera.hasCameraApplication()) {
            takePhoto.setEnabled(false);
        }
    }

    /**
     * Enable/disable the stop and start buttons
     */
    protected void enableButtons() {
        Log.d(CLASS_NAME, "enableButtons");

        if (start != null) {
            start.setEnabled(!application.isTimerRunning());
        }

        if (stop != null) {
            stop.setEnabled(application.isTimerRunning());
        }
    }

    /**
     * Change the counter text view to display the current formatted time
     */
    protected void setTimeDisplay() {
        Log.d(CLASS_NAME, "setTimeDisplay");

        if (counter != null) {
            counter.setText(application.timerDisplay());
        }
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

        Settings settings = ((IOnYourBike) getApplication()).getSettings();

        camera = null;

        if (application.isTimerRunning()) {
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

    /**
     * Keep the screen on depending on the stay awake setting
     */
    protected void stayAwakeOrNot() {
        Log.d(CLASS_NAME, "stayAwakeOrNot");

        Settings settings = ((IOnYourBike) getApplication()).getSettings();

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
        @Override
        public void run() {
            // Log.d(CLASS_NAME, display);
            Settings settings = ((IOnYourBike) getApplication()).getSettings();

            setTimeDisplay();

            if (application.isTimerRunning()) {
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
            ((IOnYourBike) getApplication()).vibrateCheck();
        }

        protected void notifyCheck() {
            String message = ((IOnYourBike) getApplication()).notifyCheck();

            if (message != null) {
                String title = getResources().getString(R.string.time_title);

                notification(title, message);
            }
        }
    }

    // Methods used for testing
    public boolean isCameraNull() {
        return camera == null;
    }

    public boolean isHandlerNull() {
        return handler == null;
    }

    public boolean isUpdateTimerNull() {
        return updateTimer == null;
    }
}
