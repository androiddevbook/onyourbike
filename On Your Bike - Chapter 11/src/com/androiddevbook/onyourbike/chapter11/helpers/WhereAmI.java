package com.androiddevbook.onyourbike.chapter11.helpers;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.androiddevbook.onyourbike.chapter11.IOnYourBike;
import com.androiddevbook.onyourbike.chapter11.R;
import com.androiddevbook.onyourbike.chapter11.model.Trip;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * WhereAmI
 * 
 * NightMode for the "On Your Bike" application.
 * 
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
public class WhereAmI implements LocationListener, Listener {
    private static String CLASS_NAME;
    private static final long MIN_TIME = 10000; // 10 seconds
    private static final float MIN_DISTANCE = 20;
    private static final float INITIAL_ZOOM = 15;
    private static final float INACCURATE = 30; // meters
    private static final double OLD_LOCATION_IMPORTANCE = 1;
    private static final double NEW_LOCATION_IMPORTANCE = 2;
    private static final long TOO_OLD = 60000; // 60 seconds

    private LocationManager locationManager;
    private Application application;
    private GoogleMap map;
    private Marker you;
    private PolylineOptions rawPoints;
    private PolylineOptions corectedPoints;
    private boolean firstLocation;
    private long lastLocationTime;
    private double longitude = -1;
    private double latitude = -1;
    private double distanceTraveled;
    private double timeStarted;
    private Trip trip;
    private SQLiteHelper helper;
    private SQLiteDatabase database;
    private int factor = 1;

    public WhereAmI() {
        CLASS_NAME = getClass().getName();
    }

    public void setMap(GoogleMap map) {
        Log.d(CLASS_NAME, "setMap");
        this.map = map;
    }

    public void startSearching(Application application, Trip trip) {
        Log.d(CLASS_NAME, "startSearching");

        this.application = application;
        this.trip = trip;

        firstLocation = true;

        rawPoints = new PolylineOptions();
        rawPoints.color(Color.RED);
        corectedPoints = new PolylineOptions();
        corectedPoints.color(Color.GREEN);

        distanceTraveled = 0;
        timeStarted = System.currentTimeMillis();

        locationManager = (LocationManager) application
                .getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, MIN_TIME * factor,
                    MIN_DISTANCE * factor, this);

            locationManager.addGpsStatusListener(this);
        } else {
            Log.w(CLASS_NAME, "GPS provider not enabled");

            AlertDialog.Builder alert = new AlertDialog.Builder(application);
            alert.setIcon(android.R.attr.alertDialogIcon)
                    .setTitle(
                            application.getResources().getText(
                                    R.string.gps_title))
                    .setMessage(
                            application.getResources().getText(
                                    R.string.gps_settings))
                    .setPositiveButton(android.R.string.yes, new DialogClick())
                    .setNegativeButton(android.R.string.no, null).show();
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, MIN_TIME * factor,
                    MIN_DISTANCE * factor, this);
        } else {
            Log.w(CLASS_NAME, "Network provider not enabled");
        }

        helper = ((IOnYourBike) application).getSQLiteHelper();
        database = helper.open();
    }

    public void stopSearching() {
        Log.d(CLASS_NAME, "stopSearching");

        firstLocation = false;

        if (locationManager != null) {
            locationManager.removeUpdates(this);
            locationManager = null;
        }

        application = null;
        map = null;
        trip = null;
    }

    double calcLongitude(Location location, double oldLongitude) {
        double newLongtitude = oldLongitude;

        Log.d(CLASS_NAME, "calcLongtitude");

        if (newLongtitude == -1) {
            newLongtitude = location.getLongitude();
        }

        if (location.getAccuracy() <= INACCURATE * factor) {
            newLongtitude = (OLD_LOCATION_IMPORTANCE * oldLongitude + NEW_LOCATION_IMPORTANCE
                    * location.getLongitude())
                    / (OLD_LOCATION_IMPORTANCE + NEW_LOCATION_IMPORTANCE);
            lastLocationTime = System.currentTimeMillis();
        } else {
            Log.d(CLASS_NAME, "longitute discarded");
        }

        if ((System.currentTimeMillis() - lastLocationTime) >= TOO_OLD * factor) {
            Log.d(CLASS_NAME, "longitude too old");
            newLongtitude = location.getLongitude();
        }

        return newLongtitude;
    }

    double calcLatitude(Location location, double oldLatitude) {
        double newLatitude = oldLatitude;

        Log.d(CLASS_NAME, "calcLatitude");

        if (newLatitude == -1) {
            newLatitude = location.getLatitude();
        }

        if (location.getAccuracy() <= INACCURATE * factor) {
            newLatitude = (OLD_LOCATION_IMPORTANCE * oldLatitude + NEW_LOCATION_IMPORTANCE
                    * location.getLatitude())
                    / (OLD_LOCATION_IMPORTANCE + NEW_LOCATION_IMPORTANCE);
            lastLocationTime = System.currentTimeMillis();
        } else {
            Log.d(CLASS_NAME, "longitute discarded");
        }

        if ((System.currentTimeMillis() - lastLocationTime) >= TOO_OLD * factor) {
            Log.d(CLASS_NAME, "latitude too old");
            newLatitude = location.getLatitude();
        }

        return newLatitude;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(CLASS_NAME, "onLocationChanged");
        Log.d(CLASS_NAME,
                "latitude : " + Double.toString(location.getLatitude()));
        Log.d(CLASS_NAME,
                "longitude : " + Double.toString(location.getLongitude()));
        Log.d(CLASS_NAME,
                "accuracy : " + Float.toString(location.getAccuracy()));

        long now = System.currentTimeMillis();
        LatLng rawLatLong = new LatLng(location.getLatitude(),
                location.getLongitude());
        LatLng oldLatLong = new LatLng(latitude, longitude);

        longitude = calcLongitude(location, longitude);
        latitude = calcLatitude(location, latitude);

        LatLng newLatLong = new LatLng(latitude, longitude);

        rawPoints.add(rawLatLong);
        corectedPoints.add(newLatLong);

        distanceTraveled += distance(oldLatLong, newLatLong);

        if (map != null) {
            if (firstLocation) {
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLong,
                        INITIAL_ZOOM));
                firstLocation = false;
            } else {
                map.animateCamera(CameraUpdateFactory.newLatLng(newLatLong));
            }
        }

        if (you != null) {
            you.remove();
        }

        if (map != null) {
            you = map.addMarker(new MarkerOptions().position(newLatLong)
                    .title("I am here").draggable(false));

            // TODO only keep last x number of points? Depends on zoom???
            map.addPolyline(rawPoints);
            map.addPolyline(corectedPoints);
        }

        // Sometime the database will be closes when closing cursors re open if
        // needed
        if (!database.isOpen()) {
            database = helper.open();
        }

        trip.addCoordinate(database, newLatLong.latitude, newLatLong.longitude,
                now);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(CLASS_NAME, "onProviderDisabled " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(CLASS_NAME, "onProviderEnabled " + provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(CLASS_NAME, "onStatusChanged");
        switch (status) {
        case LocationProvider.AVAILABLE:
            Log.d(CLASS_NAME, provider + " available");
            break;
        case LocationProvider.TEMPORARILY_UNAVAILABLE:
            Log.d(CLASS_NAME, provider + " unavailable");
            break;
        case LocationProvider.OUT_OF_SERVICE:
            Log.d(CLASS_NAME, provider + " out of service");
            break;
        }
    }

    @Override
    public void onGpsStatusChanged(int event) {
        switch (event) {
        case GpsStatus.GPS_EVENT_FIRST_FIX:
            Log.d(CLASS_NAME, "GPS First Fix");
            break;
        case GpsStatus.GPS_EVENT_STARTED:
            Log.d(CLASS_NAME, "GPS Started");
            break;
        case GpsStatus.GPS_EVENT_STOPPED:
            Log.d(CLASS_NAME, "GPS Stopped");
            break;
        case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
            // too many don???t log
            break;
        }
    }

    // Java implementation of Haversine formula
    private double distance(LatLng latLongA, LatLng latLongB) {
        double earthRadius = 3959; // miles
        double diffLat = Math.toRadians(latLongB.latitude - latLongA.latitude);
        double diffLng = Math
                .toRadians(latLongB.longitude - latLongB.longitude);
        double a = Math.sin(diffLat / 2) * Math.sin(diffLat / 2)
                + Math.cos(Math.toRadians(latLongA.latitude))
                * Math.cos(Math.toRadians(latLongB.latitude))
                * Math.sin(diffLng / 2) * Math.sin(diffLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

    private class DialogClick implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            Intent settings = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);

            application.startActivity(settings);
        }
    }

    public void savePower() {
        setFactor(5);
    }

    public void normalPower() {
        setFactor(1);
    }

    private void setFactor(int newFactor) {
        if (factor != newFactor) {
            factor = newFactor;
            stopSearching();
            startSearching(application, trip);
        }
    }
}
