package com.androiddevbook.onyourbike.chapter5.activities;

import java.util.List;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;

import com.androiddevbook.onyourbike.chapter5.OnYourBike;
import com.androiddevbook.onyourbike.chapter5.R;
import com.androiddevbook.onyourbike.chapter5.helpers.SQLiteHelper;
import com.androiddevbook.onyourbike.chapter5.model.Route;
import com.androiddevbook.onyourbike.chapter5.model.Routes;

/**
 * RoutesActivity
 * 
 * Routes Activity for the "On Your Bike" application.
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
public class RoutesActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SQLiteHelper helper = ((OnYourBike) getApplication()).getSQLiteHelper();
        SQLiteDatabase database = helper.open();

        super.onCreate(savedInstanceState);

        List<Route> routes = Routes.getAll(helper, database); // note not async
        helper.close();

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

}
