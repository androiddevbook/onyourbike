package com.androiddevbook.onyourbike.chapter9.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.androiddevbook.onyourbike.chapter9.helpers.WhereAmI;

/**
 * WhereAmI
 * 
 * WhereAmI Service for the "On Your Bike" application.
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
