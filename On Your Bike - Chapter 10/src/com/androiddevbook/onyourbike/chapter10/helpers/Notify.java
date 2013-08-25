package com.androiddevbook.onyourbike.chapter10.helpers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.androiddevbook.onyourbike.chapter10.R;

/**
 * Notify
 * 
 * Notify for the "On Your Bike" application.
 * Copyright [2012] Pearson Education, Inc
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
public class Notify {

    private static String CLASS_NAME;

    private static final int MESSAGE_ID = 1;

    private final NotificationManager manager;
    private final Context context;

    public int smallIcon = R.drawable.ic_stat_notification;

    public Notify(Activity activity) {
        CLASS_NAME = getClass().getName();

        manager = (NotificationManager) activity
                .getSystemService(Context.NOTIFICATION_SERVICE);
        context = activity;
    }

    private Notification create(String title, String message, long when) {
        Log.d(CLASS_NAME, "create()");

        // NOTE must supply icon otherwise notification dosn't show
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(title).setContentText(message).setWhen(when)
                .setSmallIcon(smallIcon).build();

        return notification;
    }

    public void notify(String title, String message, long when) {
        Log.d(CLASS_NAME, "notify()");

        Notification notification = create(title, message, when);

        manager.notify(MESSAGE_ID, notification);
    }

    public void notify(String title, String message) {
        Log.d(CLASS_NAME, "notify()");

        Notification notification = create(title, message,
                System.currentTimeMillis());

        manager.notify(MESSAGE_ID, notification);
    }
}
