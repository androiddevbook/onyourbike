package com.androiddevbook.onyourbike.chapter10.tests;

import android.content.Intent;
import android.test.ServiceTestCase;

import com.androiddevbook.onyourbike.chapter10.services.TimerService;

/**
 * TimerServiceTests
 * 
 * Tests for TimerService class for the "On Your Bike" application.
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
