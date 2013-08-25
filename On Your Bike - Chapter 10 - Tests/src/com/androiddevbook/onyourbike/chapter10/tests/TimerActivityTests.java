package com.androiddevbook.onyourbike.chapter10.tests;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

import com.androiddevbook.onyourbike.chapter10.IOnYourBike;
import com.androiddevbook.onyourbike.chapter10.R;
import com.androiddevbook.onyourbike.chapter10.activities.TimerActivity;

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
