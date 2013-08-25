package com.androiddevbook.onyourbike.chapter10.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.androiddevbook.onyourbike.chapter10.helpers.WhereAmI;

public class WhereAmIService extends Service {
    private static String CLASS_NAME;

    private final IBinder binder;
    private final WhereAmI whereAmI;

    public WhereAmIService() {
        CLASS_NAME = getClass().getName();
        whereAmI = new WhereAmI();
        binder = new WhereAmIBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(CLASS_NAME, "onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d(CLASS_NAME, "OnCreate");
    }

    @Override
    public void onDestroy() {
        Log.d(CLASS_NAME, "onDestroy");
    }

    public WhereAmI getWhereAmI() {
        return whereAmI;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class WhereAmIBinder extends Binder {
        public WhereAmIService getService() {
            return WhereAmIService.this;
        }
    }

}
