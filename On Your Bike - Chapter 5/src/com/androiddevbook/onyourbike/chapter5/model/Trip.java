package com.androiddevbook.onyourbike.chapter5.model;

import android.database.sqlite.SQLiteDatabase;

import com.androiddevbook.onyourbike.chapter5.helpers.SQLiteHelper;

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
    public long timeStated;
    public long timeTaken;

    public static void create(SQLiteHelper helper, SQLiteDatabase database) {
        String createTable = "create table if not exists trips ("
                + "_id integer primary key autoincrement, "
                + "route_id integer references routes(_id), "
                + "timeStarted integer not null, "
                + "timeTaken integer not null);";
        String createIndex = "create unique index if not exists "
                + "pk_trips on trips(_id)";
        String createForeignIndex = "create index if not exists "
                + "fk_trips_routes on trips(route_id)";

        helper.execAsyncSQL(database, createTable);
        helper.execAsyncSQL(database, createIndex);
        helper.execAsyncSQL(database, createForeignIndex);
    }

    public static void drop(SQLiteHelper helper, SQLiteDatabase database) {
        String dropTable = "drop table if exists trips;";
        String dropIndex = "drop index if exists pk_routes;";
        String dropForeignIndex = "drop index if exists fk_trips_routes;";

        helper.execAsyncSQL(database, dropIndex);
        helper.execAsyncSQL(database, dropForeignIndex);
        helper.execAsyncSQL(database, dropTable);
    }
}
