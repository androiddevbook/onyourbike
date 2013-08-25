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
public class Routes {

    static public ArrayList<Route> getAll(SQLiteHelper helper,
            SQLiteDatabase database) {
        ArrayList<Route> routes = new ArrayList<Route>();
        Cursor cursor = database.rawQuery("select * from routes", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Route route = cursorToRoute(cursor);
            routes.add(route);
            cursor.moveToNext();
        }

        cursor.close();

        return routes;
    }

    static private Route cursorToRoute(Cursor cursor) {
        Route route = new Route();
        route.setid(cursor.getInt(cursor.getColumnIndex("_id")));
        route.name = cursor.getString(cursor.getColumnIndex("name"));
        route.notes = cursor.getString(cursor.getColumnIndex("notes"));

        return route;
    }
}
