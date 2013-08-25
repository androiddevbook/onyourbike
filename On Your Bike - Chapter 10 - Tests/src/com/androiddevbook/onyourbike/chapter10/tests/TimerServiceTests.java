package com.androiddevbook.onyourbike.chapter10.tests;

import android.content.Intent;
import android.test.ServiceTestCase;

import com.androiddevbook.onyourbike.chapter10.services.TimerService;

public class TimerServiceTests extends ServiceTestCase<TimerService> {

    public TimerServiceTests() {
        super(TimerService.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setApplication(new MockOnYourBike());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testStart() {
        Intent intent = new Intent(getContext(), TimerService.class);

        assertNull(getService());

        startService(intent);
        assertTrue(getService().isRunning());

        startService(intent); // no effect
        assertTrue(getService().isRunning());

        assertNotNull(getService().getTimer());
    }

    public void testShutdown() {
        Intent intent = new Intent(getContext(), TimerService.class);

        assertNull(getService());

        startService(intent);
        assertTrue(getService().isRunning());

        shutdownService();
        assertFalse(getService().isRunning());

    }
}
