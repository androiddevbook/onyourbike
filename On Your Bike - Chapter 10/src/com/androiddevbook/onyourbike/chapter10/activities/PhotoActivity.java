package com.androiddevbook.onyourbike.chapter10.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.androiddevbook.onyourbike.chapter10.R;
import com.androiddevbook.onyourbike.chapter10.helpers.Camera;
import com.androiddevbook.onyourbike.chapter10.helpers.Share;

/**
 * PhotoActivity
 * 
 * Photo Activity for the "On Your Bike" application.
 * 
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
public class PhotoActivity extends BaseActivity {

    private static String CLASS_NAME;

    private Camera camera;
    // private Button takePhoto;
    private ImageView image;

    public PhotoActivity() {
        CLASS_NAME = getClass().getName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        image = (ImageView) this.findViewById(R.id.photo_image);
    }

    public void takePhoto(View view) {
        Log.d(CLASS_NAME, "takePhoto");

        camera.takePhoto();
    }

    public void displayPhoto(Intent intent) {
        Log.d(CLASS_NAME, "displayPhoto");

        if (camera == null) {
            camera = new Camera(this);
        }

        camera.displayPhoto(image, intent);
    }

    public Bitmap getPhoto() {
        Log.d(CLASS_NAME, "getPhoto");
        BitmapDrawable bitmap = (BitmapDrawable) image.getDrawable();

        if (bitmap != null) {
            return bitmap.getBitmap();
        } else {
            return null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(CLASS_NAME, "onStart");

        camera = new Camera(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(CLASS_NAME, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(CLASS_NAME, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(CLASS_NAME, "onStop");

        camera = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(CLASS_NAME, "onDestroy");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(CLASS_NAME, "onRestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_photo, menu);
        return true;
    }

    @Override
    public void shareActivity() {
        Share share = new Share(this);
        Intent intent = new Intent();

        share.addText(intent, "On Your Bike photo");
        share.addPhoto(intent, getPhoto());

        share.share(intent);
    }

}
