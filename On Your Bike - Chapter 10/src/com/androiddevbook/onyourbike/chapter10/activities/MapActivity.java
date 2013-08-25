package com.androiddevbook.onyourbike.chapter10.activities;

import java.util.ArrayList;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.androiddevbook.onyourbike.chapter10.IOnYourBike;
import com.androiddevbook.onyourbike.chapter10.R;
import com.androiddevbook.onyourbike.chapter10.helpers.SQLiteHelper;
import com.androiddevbook.onyourbike.chapter10.model.Coordinate;
import com.androiddevbook.onyourbike.chapter10.model.Coordinates;
import com.androiddevbook.onyourbike.chapter10.model.Route;
import com.androiddevbook.onyourbike.chapter10.model.Trip;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * MapActivity
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
public class MapActivity extends FragmentActivity {
    static private String CLASS_NAME;

    private GoogleMap map;
    private Route route;
    private Trip trip;
    private SQLiteHelper helper;
    private SQLiteDatabase database;
    private long trip_id = -1;

    public MapActivity() {
        CLASS_NAME = getClass().getName();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int available = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("trip_id")) {
            trip_id = extras.getLong("trip_id");
        } else {
            trip_id = -1;
        }

        if (available == ConnectionResult.SERVICE_MISSING
                || available == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED
                || available == ConnectionResult.SERVICE_DISABLED) {

            Log.e(CLASS_NAME, "Unable to access Google Play services");

            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(available,
                    this, 0);

            dialog.show();

        } else {
            setContentView(R.layout.activity_map);

            map = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        helper = ((IOnYourBike) getApplication()).getSQLiteHelper();
        database = helper.open();

        if (trip_id == -1) {
            trackMe();
        } else {
            showTrip();
        }
    }

    private void showTrip() {
        ArrayList<Coordinate> coordinates = Coordinates.getAllForTrip(helper,
                database, trip_id);
        PolylineOptions points = new PolylineOptions();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        points.color(Color.RED);

        for (Coordinate coordinate : coordinates) {
            LatLng latLong = new LatLng(coordinate.latitude,
                    coordinate.longitude);

            points.add(latLong);
            builder.include(latLong);
        }

        if (map != null && coordinates.size() > 0) {
            map.animateCamera(CameraUpdateFactory.newLatLngBounds(
                    builder.build(), 0));
            map.addPolyline(points);
        }

    }

    private void trackMe() {
        route = new Route();
        route.name = "Dummy Route";
        trip = new Trip();
        trip.timeStarted = System.currentTimeMillis();
        route.addTrip(trip);
        route.insert(database);
        IOnYourBike application = ((IOnYourBike) getApplication());
        application.setMap(map);
        application.startSearching(trip);
    }

    @Override
    public void onStop() {
        super.onStop();
        IOnYourBike application = ((IOnYourBike) getApplication());

        application.stopSearching();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_map, menu);
        return true;
    }

}
