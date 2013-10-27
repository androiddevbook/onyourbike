package com.androiddevbook.onyourbike.chapter5.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.androiddevbook.onyourbike.chapter5.model.Route;
import com.androiddevbook.onyourbike.chapter5.model.Trip;

/**
 * SQLiteHelper
 * 
 * SQLiteHelper for the "On Your Bike" application.
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
public class SQLiteHelper extends SQLiteOpenHelper {
    private static String CLASS_NAME;

    private static final String DATABASE_NAME = "OnYourBike.db";
    private static final int DATABASE_VERSION = 15;

    static private int numAsyncCalls = 0;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        CLASS_NAME = getClass().getName();
    }

    public void create() {
        Log.d(CLASS_NAME, "create");

        // Force onCreate to be called
        open();
    }

    public SQLiteDatabase open() {
        Log.d(CLASS_NAME, "open");

        return getWritableDatabase();
    }

    public void execAsyncSQL(SQLiteDatabase database, String sql) {
        Log.d(CLASS_NAME, "execAsyncSQL");

        numAsyncCalls++;
        new AsyncDatabase(database).execute(sql);
    }

    public void execAsyncSQL(SQLiteDatabase database, String... sqls) {
        Log.d(CLASS_NAME, "execAsyncSQL");

        numAsyncCalls++;
        new AsyncDatabase(database).execute(sqls);
    }

    private class AsyncDatabase extends AsyncTask<String, Void, Void> {
        private final SQLiteDatabase database;

        public AsyncDatabase(SQLiteDatabase database) {
            this.database = database;
        }

        @Override
        protected Void doInBackground(String... sqls) {
            Log.d(CLASS_NAME, "doInBackground");

            int length = sqls.length;

            for (int i = 0; i < length; i++) {
                database.execSQL(sqls[0]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(CLASS_NAME, "onPostExecute");

            numAsyncCalls--;
            if (numAsyncCalls == 0) {
                database.close();
            }
        }
    }

    public void onConfigure(SQLiteDatabase database) {
        Log.d(CLASS_NAME, "onConfigure");

        execAsyncSQL(database, "pragma foreign_keys=ON;");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(CLASS_NAME, "onCreate");

        Route.create(this, database);
        Trip.create(this, database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
            int newVersion) {
        Log.d(CLASS_NAME, "onUpgrade");

        Trip.drop(this, database);
        Route.drop(this, database);

        onCreate(database);
    }
}
