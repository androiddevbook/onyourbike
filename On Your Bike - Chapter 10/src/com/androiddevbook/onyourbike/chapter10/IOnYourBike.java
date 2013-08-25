package com.androiddevbook.onyourbike.chapter10;

import com.androiddevbook.onyourbike.chapter10.helpers.SQLiteHelper;
import com.androiddevbook.onyourbike.chapter10.model.Settings;
import com.androiddevbook.onyourbike.chapter10.model.Trip;
import com.google.android.gms.maps.GoogleMap;

/**
 * IOnYourBike
 * 
 * On YOur Bike application interface for the "On Your Bike" application.
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