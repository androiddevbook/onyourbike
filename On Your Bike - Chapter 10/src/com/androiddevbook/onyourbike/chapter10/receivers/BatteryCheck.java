package com.androiddevbook.onyourbike.chapter10.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.androiddevbook.onyourbike.chapter10.IOnYourBike;

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
