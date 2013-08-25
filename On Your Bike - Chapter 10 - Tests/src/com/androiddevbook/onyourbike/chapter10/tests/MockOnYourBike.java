package com.androiddevbook.onyourbike.chapter10.tests;

import android.test.mock.MockApplication;

import com.androiddevbook.onyourbike.chapter10.IOnYourBike;
import com.androiddevbook.onyourbike.chapter10.helpers.SQLiteHelper;
import com.androiddevbook.onyourbike.chapter10.model.Settings;
import com.androiddevbook.onyourbike.chapter10.model.Trip;
import com.google.android.gms.maps.GoogleMap;

/**
 * MockOnYourBike
 * 
 * Mock OnYOurBike class for the "On Your Bike" application.
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
