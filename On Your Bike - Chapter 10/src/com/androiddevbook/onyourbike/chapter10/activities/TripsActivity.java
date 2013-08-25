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
import com.androiddevbook.onyourbike.chapter10.model.Trip;
import com.androiddevbook.onyourbike.chapter10.model.Trips;

/**
 * TripsActivity
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
public class TripsActivity extends ListActivity {

    private long route_id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteHelper helper = ((IOnYourBike) getApplication()).getSQLiteHelper();
        SQLiteDatabase database = helper.open();

        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            route_id = extras.getLong("route_id");
        }

        List<Trip> trips = Trips.getAllInRoute(helper, database, route_id);

        ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(this,
                android.R.layout.simple_list_item_1, trips);

        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_trips, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position,
            long id) {
        Trip trip = (Trip) listView.getItemAtPosition(position);

        super.onListItemClick(listView, view, position, id);

        if (trip != null) {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("trip_id", trip.getid());
            startActivity(intent);
        }
    }

}
