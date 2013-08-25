package com.androiddevbook.onyourbike.chapter4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;

/**
 * SettingsActivity
 * 
 * Setting Activity for the "On Your Bike" application.
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
public class SettingsActivity extends Activity {

    private static String CLASS_NAME;

    private CheckBox vibrate;
    private CheckBox stayAwake;

    public SettingsActivity() {
        CLASS_NAME = getClass().getName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(CLASS_NAME, "onCreate");
        setContentView(R.layout.activity_settings);

        vibrate = (CheckBox) findViewById(R.id.vibrate_checkbox);
        stayAwake = (CheckBox) findViewById(R.id.awake_checkbox);

        Settings settings = ((OnYourBike) getApplication()).getSettings();

        vibrate.setChecked(settings.isVibrateOn(this));
        stayAwake.setChecked(settings.isCaffeinated(this));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(CLASS_NAME, "onStop");

        Settings settings = ((OnYourBike) getApplication()).getSettings();

        Log.i(CLASS_NAME, "Saving settings");
        settings.setVibrate(this, vibrate.isChecked());
        settings.setCaffeinated(this, stayAwake.isChecked());
    }

}
