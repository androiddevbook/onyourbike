package com.androiddevbook.onyourbike.chapter10.helpers;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toaster
 * 
 * Toaster for the "On Your Bike" application.
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
public class Toaster {

    private static String CLASS_NAME;

    private static int TOAST_DURATION = Toast.LENGTH_SHORT;

    private final Context context;

    public Toaster(Context context) {
        CLASS_NAME = getClass().getName();

        this.context = context;
    }

    public void make(int resource) {
        Log.d(CLASS_NAME, "make()");

        // NOTE Toast static call to create an instance not via new/constructor
        Toast toast = Toast.makeText(context, resource, TOAST_DURATION);

        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);

        toast.show();
    }

}
