package com.androiddevbook.onyourbike.chapter10.tests;

import android.test.mock.MockApplication;

import com.androiddevbook.onyourbike.chapter10.IOnYourBike;
import com.androiddevbook.onyourbike.chapter10.helpers.SQLiteHelper;
import com.androiddevbook.onyourbike.chapter10.model.Settings;
import com.androiddevbook.onyourbike.chapter10.model.Trip;
import com.google.android.gms.maps.GoogleMap;

public class MockOnYourBike extends MockApplication implements IOnYourBike {

    private boolean running;

    @Override
    public void startTimer(Trip trip) {
        running = true;
    }

    @Override
    public void startSearching(Trip trip) {
    }

    @Override
    public void stopTimer() {
        running = false;
    }

    @Override
    public void stopSearching() {
    }

    @Override
    public void setMap(GoogleMap map) {
    }

    @Override
    public boolean isTimerRunning() {
        return running;
    }

    @Override
    public String timerDisplay() {
        return "0:00:00";
    }

    @Override
    public Settings getSettings() {
        return new Settings();
    }

    @Override
    public void setSettings(Settings settings) {
    }

    @Override
    public SQLiteHelper getSQLiteHelper() {
        return null;
    }

    @Override
    public void checkBattery() {
    }

    @Override
    public void vibrateCheck() {
    }

    @Override
    public String notifyCheck() {
        return null;
    }

}
