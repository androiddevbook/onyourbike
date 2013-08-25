package com.androiddevbook.onyourbike.chapter11.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.androiddevbook.onyourbike.chapter11.model.TimerState;

public class TimerService extends Service {
    private static String CLASS_NAME;

    private final IBinder binder;
    private final TimerState timer;
    private boolean isRunning;

    public TimerService() {
        CLASS_NAME = getClass().getName();
        binder = new TimerBinder();
        timer = new TimerState();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(CLASS_NAME, "onStartCommand");

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d(CLASS_NAME, "OnCreate");

        isRunning = true;
        timer.reset();
    }

    @Override
    public void onDestroy() {
        Log.d(CLASS_NAME, "onDestroy");

        isRunning = false;
    }

    public TimerState getTimer() {
        return timer;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class TimerBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

}
