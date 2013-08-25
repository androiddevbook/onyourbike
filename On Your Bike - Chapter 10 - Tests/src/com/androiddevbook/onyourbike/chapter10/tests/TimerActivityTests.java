package com.androiddevbook.onyourbike.chapter10.tests;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

import com.androiddevbook.onyourbike.chapter10.IOnYourBike;
import com.androiddevbook.onyourbike.chapter10.R;
import com.androiddevbook.onyourbike.chapter10.activities.TimerActivity;

/**
 * TimerActivityTests
 * 
 * Tests for TimerActivity class for the "On Your Bike" application.
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
public class TimerActivityTests extends ActivityUnitTestCase<TimerActivity> {

    private TimerActivity activity;

    public TimerActivityTests() {
        super(TimerActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        setApplication(new MockOnYourBike());

        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                TimerActivity.class);
        startActivity(intent, null, null);
        activity = getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();

        activity = null;
    }

    public void testInitialUI() {
        TextView counter = (TextView) activity.findViewById(R.id.timer);
        Button start = (Button) activity.findViewById(R.id.start_button);
        Button stop = (Button) activity.findViewById(R.id.stop_button);
        Button takePhoto = (Button) activity.findViewById(R.id.photo_button);

        assertNotNull(counter);
        assertNotNull(start);
        assertNotNull(stop);
        assertNotNull(takePhoto);
    }

    public void testOnStart() {
        getInstrumentation().callActivityOnStart(activity);

        assertFalse(activity.isCameraNull());
        assertTrue(activity.isHandlerNull());
        assertTrue(activity.isUpdateTimerNull());
    }

    public void testOnResume() {
        TextView counter = (TextView) activity.findViewById(R.id.timer);
        Button start = (Button) activity.findViewById(R.id.start_button);
        Button stop = (Button) activity.findViewById(R.id.stop_button);
        Button takePhoto = (Button) activity.findViewById(R.id.photo_button);

        getInstrumentation().callActivityOnResume(activity);

        assertTrue("Counter is correct", counter.getText().equals("0:00:00"));

        assertTrue("Start button is enabled", start.isEnabled());
        assertFalse("Stop button is not enabled", stop.isEnabled());
        assertTrue("Photo button is enabled", takePhoto.isEnabled());
    }

    public void testOnStop() {
        getInstrumentation().callActivityOnStop(activity);

        assertTrue(activity.isCameraNull());
        assertTrue(activity.isHandlerNull());
        assertTrue(activity.isUpdateTimerNull());
    }

    public void testTimerRunning() {
        IOnYourBike application = ((IOnYourBike) activity.getApplication());

        application.startTimer(null);
        assertTrue(application.isTimerRunning());

        getInstrumentation().callActivityOnStart(activity);

        assertTrue(application.isTimerRunning());
        assertFalse(activity.isCameraNull());
        assertFalse(activity.isHandlerNull());
        assertFalse(activity.isUpdateTimerNull());

        getInstrumentation().callActivityOnStop(activity);

        assertTrue(application.isTimerRunning());
        assertTrue(activity.isCameraNull());
        assertTrue(activity.isHandlerNull());
        assertTrue(activity.isUpdateTimerNull());

        getInstrumentation().callActivityOnStart(activity);
        assertTrue(application.isTimerRunning());
    }

    public void testOnPause() {
        getInstrumentation().callActivityOnPause(activity);

        // Nothing to test just check on RTE or the like
    }

    public void testOnDestroy() {
        getInstrumentation().callActivityOnDestroy(activity);

        // Nothing to test just check on RTE or the like
    }

    public void testOnRestart() {
        getInstrumentation().callActivityOnRestart(activity);

        // Nothing to test just check on RTE or the like
    }

    public void testStopStartButtons() {
        Button start = (Button) activity.findViewById(R.id.start_button);
        Button stop = (Button) activity.findViewById(R.id.stop_button);
        IOnYourBike application = ((IOnYourBike) activity.getApplication());

        getInstrumentation().callActivityOnStart(activity);

        assertFalse(application.isTimerRunning());

        TouchUtils.clickView(this, start);
        assertTrue(application.isTimerRunning());
        assertFalse("Start button is not enabled", start.isEnabled());
        assertTrue("Stop button is enabled", stop.isEnabled());

        TouchUtils.clickView(this, stop);
        assertFalse(application.isTimerRunning());
        assertTrue("Start button is enabled", start.isEnabled());
        assertFalse("Stop button is not enabled", stop.isEnabled());
    }

}
