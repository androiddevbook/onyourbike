package com.androiddevbook.onyourbike.chapter9.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androiddevbook.onyourbike.chapter9.helpers.SQLiteHelper;

/**
 * Trip
 * 
 * Trip for the "On Your Bike" application.
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
public class Trip {
    private long _id;
    public long timeStarted;
    public long timeTaken;
    public long totalDistance;
    private final List<Coordinate> coordinates = new ArrayList<Coordinate>();

    public Trip() {
        _id = -1;
    }

    public static void create(SQLiteDatabase database) {
        String createTable = "create table if not exists trips ("
                + "_id integer primary key autoincrement, "
                + "route_id integer references routes(_id), "
                + "timeStarted integer not null, "
                + "timeTaken integer not null)";
        String createIndex = "create unique index if not exists "
                + "pk_trips on trips(_id)";
        String createForeignIndex = "create index if not exists "
                + "fk_trips_routes on trips(route_id)";

        database.execSQL(createTable);
        database.execSQL(createIndex);
        database.execSQL(createForeignIndex);
    }

    public static void drop(SQLiteDatabase database) {
        String dropTable = "drop table if exists trips";
        String dropIndex = "drop index if exists pk_routes";
        String dropForeignIndex = "drop index if exists fk_trips_routes";

        database.execSQL(dropIndex);
        database.execSQL(dropForeignIndex);
        database.execSQL(dropTable);
    }

    public void setid(int id) {
        _id = id;
    }

    public long getid() {
        return _id;
    }

    public void insert(SQLiteDatabase database, long route_id) {
        String insertRow = "insert into trips"
                + "(route_id, timeStarted, timeTaken) values" + "(" + route_id
                + ", " + timeStarted + ", " + timeTaken + " )";

        database.beginTransaction();
        try {
            database.execSQL(insertRow);

            Cursor cursor = database.rawQuery(
                    "SELECT last_insert_rowid() from trips", null);
            cursor.moveToFirst();
            _id = cursor.getLong(0);
            cursor.close();
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void update(SQLiteHelper helper, SQLiteDatabase database) {
        String updateRow = "update trips set timeStated = " + timeStarted
                + ", timeTaken = " + timeTaken + "where _id = " + _id;

        helper.execAsyncSQL(database, updateRow);
    }

    public void delete(SQLiteHelper helper, SQLiteDatabase database) {
        String removeRow = "delete from trips" + "where _id = " + _id;

        helper.execAsyncSQL(database, removeRow);

        _id = -1;
    }

    public void addCoordinate(SQLiteDatabase database, double latitude,
            double longitude, long timeAt) {
        Coordinate coordinate = new Coordinate(latitude, longitude, timeAt);

        coordinate.insert(database);
        coordinates.add(coordinate);
    }

    @Override
    public String toString() {
        return DateFormat.getDateInstance().format(timeStarted) + " "
                + DateFormat.getTimeInstance().format(timeStarted);
    }
}
