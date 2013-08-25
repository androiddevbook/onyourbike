package com.androiddevbook.onyourbike.chapter10.activities;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.androiddevbook.onyourbike.chapter10.IOnYourBike;
import com.androiddevbook.onyourbike.chapter10.R;
import com.androiddevbook.onyourbike.chapter10.helpers.SQLiteHelper;
import com.androiddevbook.onyourbike.chapter10.model.Route;
import com.androiddevbook.onyourbike.chapter10.model.Routes;

/**
 * RoutesActivity
 * 
 * Routes Activity for the "On Your Bike" application.
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
public class RoutesActivity extends ListActivity {

    private List<Route> routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteHelper helper = ((IOnYourBike) getApplication()).getSQLiteHelper();
        SQLiteDatabase database = helper.open();

        super.onCreate(savedInstanceState);

        routes = Routes.getAll(helper, database); // note not async

        ArrayAdapter<Route> adapter = new ArrayAdapter<Route>(this,
                android.R.layout.simple_list_item_1, routes);

        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_routes, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position,
            long id) {
        Route route = (Route) listView.getItemAtPosition(position);

        super.onListItemClick(listView, view, position, id);

        if (route != null) {
            Intent intent = new Intent(this, TripsActivity.class);
            intent.putExtra("route_id", route.getid());
            startActivity(intent);
        }
    }
}
