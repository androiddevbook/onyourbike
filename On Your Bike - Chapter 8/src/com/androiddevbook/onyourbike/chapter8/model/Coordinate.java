package com.androiddevbook.onyourbike.chapter8.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Coordinate
 * 
 * Coordinate for the "On Your Bike" application.
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
public class Coordinate {
    private long _id;
    public double latitude;
    public double longitude;
    public long timeAt;

    public Coordinate(double latitude, double longitude, long timeAt) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeAt = timeAt;
    }

    public static void create(SQLiteDatabase database) {
        String createTable = "create table if not exists coordinates ("
                + "_id integer primary key autoincrement, "
                + "trip_id integer references trips(_id), "
                + "latitude real not null, " + "longitude real not null, "
                + "timeAt long not null)";

        database.execSQL(createTable);
    }

    public static void drop(SQLiteDatabase database) {
        String dropTable = "drop table if exists coordinates";

        database.execSQL(dropTable);
    }

    public void update(SQLiteDatabase database) {
        String updateRow = "update coordinates set latitude = " + latitude
                + ", longitude = " + longitude + ", timeAt = " + timeAt
                + "where _id = " + _id;

        database.execSQL(updateRow);
    }

    public void insert(SQLiteDatabase database) {
        String insertRow = "insert into coordinates "
                + "(latitude, longitude, timeAt) values " + "( " + latitude
                + ", " + longitude + ", " + timeAt + " )";

        database.beginTransaction();
        try {
            database.execSQL(insertRow);

            Cursor cursor = database.rawQuery(
                    "SELECT last_insert_rowid() from coordinates", null);
            cursor.moveToFirst();
            _id = cursor.getLong(0);
            cursor.close();
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void delete(SQLiteDatabase database) {
        String removeRow = "delete from coordinates " + "where _id = " + _id;

        _id = -1;

        database.execSQL(removeRow);
    }

}
