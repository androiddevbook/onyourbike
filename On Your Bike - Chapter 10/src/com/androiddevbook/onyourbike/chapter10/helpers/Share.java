package com.androiddevbook.onyourbike.chapter10.helpers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Share
 * 
 * Share for the "On Your Bike" application.
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
public class Share {
    private static String CLASS_NAME;

    private final Activity activity;

    public Share(Activity activity) {
        CLASS_NAME = getClass().getName();

        this.activity = activity;
    }

    public void share(Intent intent) {
        activity.startActivity(Intent.createChooser(intent, "Share via?"));
    }

    public void addPhoto(Intent intent, Bitmap bitmap) {
        Log.d(CLASS_NAME, "addPhoto");

        intent.setAction(android.content.Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_STREAM, bitmap);
    }

    public void addText(Intent intent, String text) {
        Log.d(CLASS_NAME, "addText");

        intent.setAction(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
    }

    public void addSubject(Intent intent, String subject) {
        Log.d(CLASS_NAME, "addSubject");

        intent.setAction(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    }

}
