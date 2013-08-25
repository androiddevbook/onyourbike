package com.androiddevbook.onyourbike.chapter11.model;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androiddevbook.onyourbike.chapter11.helpers.SQLiteHelper;

/**
 * Route
 * 
 * Route for the "On Your Bike" application.
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
public class Route {
    private long _id;
    public String name;
    public String notes;
    public List<Trip> trips = new ArrayList<Trip>();
    public long totalDistance;
    public long averageTime;

    public static void create(SQLiteDatabase database) {
        String createTable = "create table if not exists routes ("
                + "_id integer primary key autoincrement, "
                + "name text not null, " + "notes text not null )";
        String createIndex = "create unique index if not exists "
                + "pk_routes on routes(_id)";

        database.execSQL(createTable);
        database.execSQL(createIndex);
    }

    public static void drop(SQLiteDatabase database) {
        String dropTable = "drop table if exists routes";
        String dropIndex = "drop index if exists pk_routes";

        database.execSQL(dropIndex);
        database.execSQL(dropTable);
    }

    @Override
    public String toString() {
        return name;
    }

    public void setid(long id) {
        _id = id;
    }

    public long getid() {
        return _id;
    }

    public void insert(SQLiteDatabase database) {
        String insertRow = "insert into routes " + "(name, notes) values "
                + "( '" + name + "', '" + notes + "' )";

        database.beginTransaction();
        try {
            database.execSQL(insertRow);

            Cursor cursor = database.rawQuery(
                    "select last_insert_rowid() from routes", null);
            cursor.moveToFirst();
            _id = cursor.getLong(0);
            cursor.close();

            for (Trip trip : trips) {
                trip.insert(database, _id);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void update(SQLiteHelper helper, SQLiteDatabase database) {
        String updateRow = "update routes set name = '" + name + "', notes = '"
                + notes + "' where _id = " + _id;

        database.beginTransaction();
        try {
            helper.execAsyncSQL(database, updateRow);

            for (Trip trip : trips) {
                trip.update(helper, database);
            }
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public void delete(SQLiteHelper helper, SQLiteDatabase database) {
        String removeRow = "delete from routes " + "where _id = " + _id;

        helper.execAsyncSQL(database, removeRow);

        _id = -1;
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
    }
}
