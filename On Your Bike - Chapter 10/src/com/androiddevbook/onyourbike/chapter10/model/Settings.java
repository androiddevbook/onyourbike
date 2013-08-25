package com.androiddevbook.onyourbike.chapter10.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Settings
 * 
 * Contains the settings for the "On Your Bike" application preference screen.
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
public class Settings {
    private static String CLASS_NAME;

    private static String VIBRATE = "vibrate";
    private static String STAYAWAKE = "stayawake";

    private boolean vibrateOn;
    private boolean stayAwake;

    public Settings() {
        CLASS_NAME = getClass().getName();
    }

    /**
     * Return the saved vibrate setting
     * 
     * @return true if vibrate is on, false if it is not
     */
    public boolean isVibrateOn(Activity activity) {
        Log.d(CLASS_NAME, "isVibrateOn");

        SharedPreferences preferences = activity
                .getPreferences(Activity.MODE_PRIVATE);

        if (preferences.contains(VIBRATE)) {
            vibrateOn = preferences.getBoolean(VIBRATE, false);
        }

        // Log.i(CLASS_NAME, "Vibrate is " + vibrateOn);

        return vibrateOn;
    }

    /**
     * Set the vibration settings
     * 
     * @param activity
     * activity to get preferences from
     * @param vibrate
     * true to turn vibration on, false to turn it off
     */
    public void setVibrate(Activity activity, boolean vibrate) {
        Log.d(CLASS_NAME, "setVibrate");
        Log.i(CLASS_NAME, "Setting vibrate to " + vibrate);

        vibrateOn = vibrate;

        SharedPreferences preferences = activity
                .getPreferences(Activity.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(VIBRATE, vibrate);
        editor.apply(); // rather than commit()
    }

    /**
     * Return the stay awake setting
     * 
     * @return true if stay awake is on, false if it is not
     */
    public Boolean isCaffeinated(Activity activity) {
        Log.d(CLASS_NAME, "isCaffeinated");

        SharedPreferences preferences = activity
                .getPreferences(Activity.MODE_PRIVATE);

        if (preferences.contains(STAYAWAKE)) {
            stayAwake = preferences.getBoolean(STAYAWAKE, false);
        }

        // Log.i(CLASS_NAME, "Stay awake is " + stayAwake);

        return stayAwake;
    }

    /**
     * Set the stay awake settings
     * 
     * @param activity
     * activity to get preferences from
     * @param stayawake
     * true to stay awake on, false to work as normal.
     */
    public void setCaffeinated(Activity activity, boolean stayawake) {
        Log.d(CLASS_NAME, "setCaffeinated");

        Log.i(CLASS_NAME, "Setting stay awake to " + stayawake);

        stayAwake = stayawake;

        SharedPreferences preferences = activity
                .getPreferences(Activity.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(STAYAWAKE, stayAwake);
        editor.apply(); // rather than commit()
    }
}
