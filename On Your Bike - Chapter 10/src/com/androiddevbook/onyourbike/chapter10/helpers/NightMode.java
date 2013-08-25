package com.androiddevbook.onyourbike.chapter10.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * NightMode
 * 
 * NightMode for the "On Your Bike" application.
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
public class NightMode implements SensorEventListener {

    private static final float MIN_LIGHT = SensorManager.LIGHT_SUNRISE;
    private static final float NIGHT_LEVEL = 0.1F;
    private static final long STOP_RAPID_CHANGES = 30000;
    private static String CLASS_NAME;

    private float oldLevel = 0.5F;
    private final Window window;
    private final boolean useLightSensor;
    private final Activity activity;
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private boolean isLightTheme;
    private long themeChanged;
    private float average;

    public NightMode(Window window, Activity activity, boolean useLightSensor,
            boolean isLightTheme) {
        CLASS_NAME = getClass().getName();
        this.window = window;
        this.activity = activity;
        this.useLightSensor = useLightSensor;
        this.isLightTheme = isLightTheme;
    }

    public void setDarkTheme() {
        int apiLevel = Build.VERSION.SDK_INT;

        Log.d(CLASS_NAME, "setDarkTheme");

        if (!isLightTheme) {
            return;
        }

        isLightTheme = false;

        if (apiLevel < 11) {
            changeTheme(android.R.style.Theme_Black);
        } else {
            changeTheme(android.R.style.Theme_Holo);
        }
    }

    public void setLightTheme() {
        int apiLevel = Build.VERSION.SDK_INT;

        Log.d(CLASS_NAME, "setLightTheme");

        if (isLightTheme) {
            return;
        }

        isLightTheme = true;

        if (apiLevel < 11) {
            changeTheme(android.R.style.Theme_Light);
        } else if (apiLevel < 14) {
            changeTheme(android.R.style.Theme_Holo_Light);
        } else {
            changeTheme(android.R.style.Theme_Holo_Light_DarkActionBar);
        }
    }

    private void changeTheme(int themeID) {
        Intent intent = activity.getIntent();

        themeChanged = System.currentTimeMillis();

        intent.putExtra("Theme", themeID);
        intent.putExtra("isLightTheme", isLightTheme);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        activity.overridePendingTransition(0, 0);
        activity.finish();

        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);
    }

    public void on() {
        Log.d(CLASS_NAME, "Turing night mode on");

        WindowManager.LayoutParams params = window.getAttributes();
        oldLevel = params.screenBrightness;
        params.screenBrightness = NIGHT_LEVEL;
        window.setAttributes(params);
    }

    public void off() {
        Log.d(CLASS_NAME, "Turing night mode off");

        WindowManager.LayoutParams params = window.getAttributes();
        params.screenBrightness = oldLevel;
        window.setAttributes(params);
    }

    public void startSensing() {
        sensorManager = (SensorManager) activity
                .getSystemService(Context.SENSOR_SERVICE);

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sensorManager.registerListener(this, lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopSensing() {
        sensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(CLASS_NAME, "Light sensor accuracy changed");
    }

    public void onSensorChanged(SensorEvent event) {
        Log.d(CLASS_NAME, "Light sensor value changed");

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float value = event.values[0];
            average = (4 * average + value) / 5;

            Log.d(CLASS_NAME, "Light sensor is raw" + value + " average "
                    + average);

            if ((System.currentTimeMillis() - themeChanged) > STOP_RAPID_CHANGES) {
                if (average <= MIN_LIGHT) {
                    on();
                    setDarkTheme();
                } else {
                    off();
                    setLightTheme();
                }
            }
        }
    }
}
