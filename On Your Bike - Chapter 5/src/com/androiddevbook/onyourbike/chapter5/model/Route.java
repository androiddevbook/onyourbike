package com.androiddevbook.onyourbike.chapter5.model;

import android.database.sqlite.SQLiteDatabase;

import com.androiddevbook.onyourbike.chapter5.helpers.SQLiteHelper;

/**
 * Route
 * 
 * Route for the "On Your Bike" application.
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
public class Route {
    private long _id;
    public String name;
    public String notes;
    public Trip trips[];

    public static void create(SQLiteHelper helper, SQLiteDatabase database) {
        String createTable = "create table if not exists routes ("
                + "_id integer primary key autoincrement, "
                + "name text not null, " + "notes text not null );";
        String createIndex = "create unique index if not exists "
                + "pk_routes on routes(_id)";

        helper.execAsyncSQL(database, createTable);
        helper.execAsyncSQL(database, createIndex);
    }

    public static void drop(SQLiteHelper helper, SQLiteDatabase database) {
        String dropTable = "drop table if exists routes;";
        String dropIndex = "drop index if exists pk_routes;";

        helper.execAsyncSQL(database, dropIndex);
        helper.execAsyncSQL(database, dropTable);
    }

    @Override
    public String toString() {
        return name;
    }

    public void setid(long id) {
        _id = id;
    }
}
