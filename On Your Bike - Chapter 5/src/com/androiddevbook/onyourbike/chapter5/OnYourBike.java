package com.androiddevbook.onyourbike.chapter5;

import android.app.Application;
import android.util.Log;

import com.androiddevbook.onyourbike.chapter5.helpers.SQLiteHelper;
import com.androiddevbook.onyourbike.chapter5.model.Settings;

/**
 * OnYourBike.java
 * 
 * "On Your Bike" application from "Learning Android Development" published by
 * Addison-Wesley which is an imprint of Pearson.
 * 
 * For more information on this application and the book please visit the
 * Learning Android Development web site:
 * http://www.androiddevbook.com
 * 
 * Or email us:
 * questions@androiddevbook.com
 * 
 * Or contact the authors at:
 * justin@androiddevbook.com or james@androiddevbook.com
 * 
 * Or follow us on twitter:
 * 
 * @androiddevbook @justinmclean @jamesjtalbot
 * 
 * Or find us on Google+:
 * https://plus.google.com/101355380104954686723
 * 
 * The latest version of this code can be found on GitHub
 * https://github.com/androidDevBook/OnYourBike
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
public class OnYourBike extends Application {
    private static String CLASS_NAME;

    private Settings settings;
    private SQLiteHelper helper;

    public OnYourBike() {
        CLASS_NAME = getClass().getName();
    }

    public Settings getSettings() {
        Log.d(CLASS_NAME, "getSettings");

        if (settings == null) {
            settings = new Settings();
        }

        return settings;
    }

    public void setSettings(Settings settings) {
        Log.d(CLASS_NAME, "setSettings");

        this.settings = settings;
    }

    public SQLiteHelper getSQLiteHelper() {
        Log.d(CLASS_NAME, "getSQLiteHelper");

        if (helper == null) {
            helper = new SQLiteHelper(this);
        }

        return helper;
    }

    @Override
    public void onCreate() {
        Log.d(CLASS_NAME, "onCreate");

        super.onCreate();

        getSQLiteHelper().create();
    }
}
