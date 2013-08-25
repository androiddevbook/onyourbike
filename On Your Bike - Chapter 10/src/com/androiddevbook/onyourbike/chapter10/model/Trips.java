package com.androiddevbook.onyourbike.chapter10.model;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androiddevbook.onyourbike.chapter10.helpers.SQLiteHelper;

/**
 * Routes
 * 
 * Routes for the "On Your Bike" application.
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
public class Trips {

    static public ArrayList<Trip> getAll(SQLiteHelper helper,
            SQLiteDatabase database) {
        ArrayList<Trip> trips = new ArrayList<Trip>();
        Cursor cursor = database.rawQuery("select * from trips", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Trip route = cursorToTrip(cursor);
            trips.add(route);
            cursor.moveToNext();
        }

        cursor.close();

        return trips;
    }

    static public ArrayList<Trip> getAllInRoute(SQLiteHelper helper,
            SQLiteDatabase database, long route_id) {
        ArrayList<Trip> trips = new ArrayList<Trip>();
        Cursor cursor = database.rawQuery(
                "select * from trips where route_id = " + route_id, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Trip trip = cursorToTrip(cursor);
            trips.add(trip);
            cursor.moveToNext();
        }

        cursor.close();

        return trips;
    }

    static private Trip cursorToTrip(Cursor cursor) {
        Trip trip = new Trip();
        trip.setid(cursor.getInt(cursor.getColumnIndex("_id")));
        trip.timeStarted = cursor.getLong(cursor.getColumnIndex("timeStarted"));
        trip.timeTaken = cursor.getLong(cursor.getColumnIndex("timeTaken"));

        return trip;
    }
}
