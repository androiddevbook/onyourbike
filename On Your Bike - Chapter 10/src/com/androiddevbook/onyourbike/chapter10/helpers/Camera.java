package com.androiddevbook.onyourbike.chapter10.helpers;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

/**
 * Camera
 * 
 * Camera for the "On Your Bike" application.
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
public class Camera {
    private static String CLASS_NAME;

    public static final int PHOTO_TAKEN = 1001;

    private final Activity activity;

    public Camera(Activity activity) {
        CLASS_NAME = getClass().getName();

        this.activity = activity;
    }

    public void takePhoto() {
        Log.d(CLASS_NAME, "takePhoto");

        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(takePhoto, PHOTO_TAKEN);
    }

    public void displayPhoto(ImageView image, Intent intent) {
        Log.d(CLASS_NAME, "displayPhoto");

        Bundle extras = intent.getExtras();
        Bitmap bitmap = (Bitmap) extras.get("data");

        if (bitmap != null) {
            image.setImageBitmap(bitmap);
        }
    }

    public boolean hasCamera() {
        Log.d(CLASS_NAME, "hasCamera");

        PackageManager manager = activity.getPackageManager();

        return manager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public boolean hasCameraApplication() {
        Log.d(CLASS_NAME, "hasCameraApplication");

        PackageManager manager = activity.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = manager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);

        return list.size() > 0;
    }

}
