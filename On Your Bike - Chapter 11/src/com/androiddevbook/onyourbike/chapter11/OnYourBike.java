package com.androiddevbook.onyourbike.chapter11;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import com.androiddevbook.onyourbike.chapter11.helpers.Battery;
import com.androiddevbook.onyourbike.chapter11.helpers.SQLiteHelper;
import com.androiddevbook.onyourbike.chapter11.model.Settings;
import com.androiddevbook.onyourbike.chapter11.model.TimerState;
import com.androiddevbook.onyourbike.chapter11.model.Trip;
import com.androiddevbook.onyourbike.chapter11.receivers.BatteryCheck;
import com.androiddevbook.onyourbike.chapter11.services.TimerService;
import com.androiddevbook.onyourbike.chapter11.services.TimerService.TimerBinder;
import com.androiddevbook.onyourbike.chapter11.services.WhereAmIService;
import com.androiddevbook.onyourbike.chapter11.services.WhereAmIService.WhereAmIBinder;
import com.google.android.gms.maps.GoogleMap;

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
public class OnYourBike extends Application implements IOnYourBike {
    private static String CLASS_NAME;

    private Settings settings;
    private SQLiteHelper helper;
    private Vibrator vibrate;
    private long lastSeconds = -1;

    private TimerService timerService;
    private WhereAmIService whereAmIService;

    private BatteryCheck batteryCheck;

    public OnYourBike() {
        CLASS_NAME = getClass().getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.androiddevbook.onyourbike.chapter10.IOnYourBike#startTimer(com.
     * androiddevbook.onyourbike.chapter10.model.Trip)
     */
    @Override
    public void startTimer(Trip trip) {
        if (timerService != null) {
            timerService.getTimer().start();
        }
        startSearching(trip);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.androiddevbook.onyourbike.chapter10.IOnYourBike#startSearching(com
     * .androiddevbook.onyourbike.chapter10.model.Trip)
     */
    @Override
    public void startSearching(Trip trip) {
        if (whereAmIService != null) {
            whereAmIService.getWhereAmI().startSearching(this, trip);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.androiddevbook.onyourbike.chapter10.IOnYourBike#stopTimer()
     */
    @Override
    public void stopTimer() {
        if (timerService != null) {
            timerService.getTimer().stop();
        }
        stopSearching();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.androiddevbook.onyourbike.chapter10.IOnYourBike#stopSearching()
     */
    @Override
    public void stopSearching() {
        if (whereAmIService != null) {
            whereAmIService.getWhereAmI().stopSearching();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.androiddevbook.onyourbike.chapter10.IOnYourBike#setMap(com.google
     * .android.gms.maps.GoogleMap)
     */
    @Override
    public void setMap(GoogleMap map) {
        if (whereAmIService != null) {
            whereAmIService.getWhereAmI().setMap(map);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.androiddevbook.onyourbike.chapter10.IOnYourBike#isTimerRunning()
     */
    @Override
    public boolean isTimerRunning() {
        if (timerService != null) {
            return timerService.getTimer().isRunning();
        } else {
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.androiddevbook.onyourbike.chapter10.IOnYourBike#timerDisplay()
     */
    @Override
    public String timerDisplay() {
        if (timerService != null) {
            return timerService.getTimer().display();
        }

        return "0:00:00";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.androiddevbook.onyourbike.chapter10.IOnYourBike#getSettings()
     */
    @Override
    public Settings getSettings() {
        Log.d(CLASS_NAME, "getSettings");

        if (settings == null) {
            settings = new Settings();
        }

        return settings;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.androiddevbook.onyourbike.chapter10.IOnYourBike#setSettings(com.
     * androiddevbook.onyourbike.chapter10.model.Settings)
     */
    @Override
    public void setSettings(Settings settings) {
        Log.d(CLASS_NAME, "setSettings");

        this.settings = settings;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.androiddevbook.onyourbike.chapter10.IOnYourBike#getSQLiteHelper()
     */
    @Override
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

        // NOTE some phone and tablets may not vibrate
        vibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (vibrate == null) {
            Log.w(CLASS_NAME, "No vibration service exists.");
        }

        getSQLiteHelper().create();

        Intent timerIntent = new Intent(getApplicationContext(),
                TimerService.class);
        bindService(timerIntent, new TimerServiceConnection(),
                Context.BIND_AUTO_CREATE);

        Intent whereAmIIntent = new Intent(getApplicationContext(),
                WhereAmIService.class);
        bindService(whereAmIIntent, new WhereAmIServiceConnection(),
                Context.BIND_AUTO_CREATE);

        batteryCheck = new BatteryCheck(getApplicationContext());

        checkBattery();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.androiddevbook.onyourbike.chapter10.IOnYourBike#checkBattery()
     */
    @Override
    public void checkBattery() {
        Battery battery = new Battery();
        Context context = getApplicationContext();

        // If battery is critical shut off location tracking
        if (battery.isCritical(context)) {
            Intent whereAmIIntent = new Intent(getApplicationContext(),
                    WhereAmIService.class);

            if (whereAmIService != null) {
                whereAmIService.getWhereAmI().stopSearching();
                whereAmIService.stopService(whereAmIIntent);
            }
        }
        // If battery is low track less often
        else if (battery.isLow(context)) {
            if (whereAmIService != null) {
                whereAmIService.getWhereAmI().savePower();
            }
        }
        // Otherwise track normally
        else {
            if (whereAmIService != null) {
                whereAmIService.getWhereAmI().normalPower();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.androiddevbook.onyourbike.chapter10.IOnYourBike#vibrateCheck()
     */
    @Override
    public void vibrateCheck() {
        long seconds;
        long minutes;

        Log.d(CLASS_NAME, "vibrateCheck");

        TimerState timer = timerService.getTimer();
        timer.elapsedTime();
        seconds = timer.seconds();
        minutes = timer.minutes();

        // NOTE done this way to avoid Array/ArrayList issues
        // NOTE hasVibrator() only on API 11+
        // NOTE try/catch to stop force close on emulator
        // NOTE very easy to get manifest wrong!
        try {
            // NOTE seconds != lastSeconds so it only vibrates once/second
            if (vibrate != null && seconds == 0 && seconds != lastSeconds) {
                long[] once = { 0, 100 };
                long[] twice = { 0, 100, 400, 100 };
                long[] thrice = { 0, 100, 400, 100, 400, 100 };

                // every hour
                if (minutes == 0) {
                    Log.i(CLASS_NAME, "Vibrate 3 times");
                    vibrate.vibrate(thrice, -1);
                }
                // every 15 minutes
                else if (minutes % 15 == 0) {
                    Log.i(CLASS_NAME, "Vibrate 2 time");
                    vibrate.vibrate(twice, -1);
                }
                // every 5 minutes
                else if (minutes % 5 == 0) {
                    Log.i(CLASS_NAME, "Vibrate once");
                    vibrate.vibrate(once, -1);
                }
            }
        } catch (Exception e) {
            Log.w(CLASS_NAME, "Exception: " + e.getMessage());
        }

        lastSeconds = seconds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.androiddevbook.onyourbike.chapter10.IOnYourBike#notifyCheck()
     */
    @Override
    public String notifyCheck() {
        long seconds;
        long minutes;
        long hours;

        Log.d(CLASS_NAME, "notifyCheck");

        TimerState timer = timerService.getTimer();
        timer.elapsedTime();
        seconds = timer.seconds();
        minutes = timer.minutes();
        hours = timer.hours();

        if (minutes % 15 == 0 && seconds == 0 && seconds != lastSeconds) {
            String message = getResources().getString(
                    R.string.time_running_message);

            if (hours == 0 && minutes == 0) {
                message = getResources().getString(R.string.time_start_message);
            }

            message = String.format(message, hours, minutes);

            return message;
        }

        lastSeconds = seconds;

        return null;
    }

    private class TimerServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(CLASS_NAME, "onServiceConnected");

            TimerBinder binder = (TimerBinder) service;
            timerService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(CLASS_NAME, "onServiceDisconnected");
            timerService = null;
        }
    }

    private class WhereAmIServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(CLASS_NAME, "onServiceConnected");

            WhereAmIBinder binder = (WhereAmIBinder) service;
            whereAmIService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(CLASS_NAME, "onServiceDisconnected");
            whereAmIService = null;
        }
    }

}
