package com.androiddevbook.onyourbike.chapter9.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Battery
 * 
 * Battery for the "On Your Bike" application.
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
public class Battery {
    private static String CLASS_NAME;

    private static final int LOW_LEVEL = 35;
    private static final int CRITICAL_LEVEL = 15;

    public Battery() {
        CLASS_NAME = getClass().getName();
    }

    public int level(Context context) {
        Log.d(CLASS_NAME, "level");

        IntentFilter changed = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent battery = context.registerReceiver(null, changed);

        int level = battery.getIntExtra("level", 0);
        int scale = battery.getIntExtra("scale", 100);

        return level * 100 / scale;
    }

    public boolean isLow(Context context) {
        Log.d(CLASS_NAME, "isLow");

        if (level(context) < LOW_LEVEL) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCritical(Context context) {
        Log.d(CLASS_NAME, "isCritical");

        if (level(context) < CRITICAL_LEVEL) {
            return true;
        } else {
            return false;
        }
    }

}
