package com.androiddevbook.onyourbike.chapter10;

import com.androiddevbook.onyourbike.chapter10.helpers.SQLiteHelper;
import com.androiddevbook.onyourbike.chapter10.model.Settings;
import com.androiddevbook.onyourbike.chapter10.model.Trip;
import com.google.android.gms.maps.GoogleMap;

public interface IOnYourBike {

    public abstract void startTimer(Trip trip);

    public abstract void startSearching(Trip trip);

    public abstract void stopTimer();

    public abstract void stopSearching();

    public abstract void setMap(GoogleMap map);

    public abstract boolean isTimerRunning();

    public abstract String timerDisplay();

    public abstract Settings getSettings();

    public abstract void setSettings(Settings settings);

    public abstract SQLiteHelper getSQLiteHelper();

    public abstract void checkBattery();

    public abstract void vibrateCheck();

    public abstract String notifyCheck();

}