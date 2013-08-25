package com.androiddevbook.onyourbike.chapter10.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.androiddevbook.onyourbike.chapter10.IOnYourBike;

/**
 * BatteryCheck
 * 
 * Battery check receiver for the "On Your Bike" application.
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
public class BatteryCheck extends BroadcastReceiver {

    private static String CLASS_NAME;

    public BatteryCheck(Context context) {
        CLASS_NAME = getClass().getName();

        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, BatteryCheck.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_HALF_HOUR,
                pendingIntent);
    }

    public BatteryCheck() {
        CLASS_NAME = getClass().getName();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(CLASS_NAME, "onReceive");

        ((IOnYourBike) context.getApplicationContext()).checkBattery();
    }
}
