package com.androiddevbook.onyourbike.chapter10.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

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
