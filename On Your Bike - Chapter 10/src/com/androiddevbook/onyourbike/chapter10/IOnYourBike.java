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

    public void startTimer(Trip trip);

    public void startSearching(Trip trip);

    public void stopTimer();

    public void stopSearching();

    public void setMap(GoogleMap map);

    public boolean isTimerRunning();

    public String timerDisplay();

    public Settings getSettings();

    public void setSettings(Settings settings);

    public SQLiteHelper getSQLiteHelper();

    public void checkBattery();

    public void vibrateCheck();

    public String notifyCheck();

}