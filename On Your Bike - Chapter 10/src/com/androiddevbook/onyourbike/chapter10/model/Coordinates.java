package com.androiddevbook.onyourbike.chapter10.model;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androiddevbook.onyourbike.chapter10.helpers.SQLiteHelper;

/**
 * Coordinates
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
public class Coordinates {

    static public ArrayList<Coordinate> getAllForTrip(SQLiteHelper helper,
            SQLiteDatabase database, long trip_id) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        Cursor cursor = database.rawQuery(
                "select * from coordinates where trip_id = " + trip_id
                        + " order by _id", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Coordinate coordinate = cursorToCoordinate(cursor);
            coordinates.add(coordinate);
            cursor.moveToNext();
        }

        cursor.close();

        return coordinates;
    }

    static private Coordinate cursorToCoordinate(Cursor cursor) {
        double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
        double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
        long timeAt = cursor.getLong(cursor.getColumnIndex("timeAt"));

        Coordinate coordinate = new Coordinate(latitude, longitude, timeAt);

        return coordinate;
    }
}
