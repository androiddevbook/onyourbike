package com.androiddevbook.onyourbike.chapter10.model;

import com.androiddevbook.onyourbike.utils.Time;

/**
 * TimerState
 * 
 * State of the timer in the On Your Bike application.
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
public class TimerState {
    static String CLASS_NAME;

    private long startedAt;
    private long lastStopped;
    private boolean running;
    private long lastTime;

    protected Time time;

    public TimerState() {
        CLASS_NAME = getClass().getName();
        time = new Time();
    }

    /**
     * Return the current elapsed time.
     * This is based off the current time if the timer is running and off the
     * last time the timer was stopped if its not running.
     * 
     * @return Time since timer started.
     */
    public long elapsedTime() {
        long timeNow;

        if (running) {
            timeNow = time.now();
        } else {
            timeNow = lastStopped;
        }

        lastTime = timeNow - startedAt;

        return lastTime;
    }

    /**
     * Convenience function.
     * 
     * @return Seconds elapsed since last call to elapsedTime.
     */
    public long seconds() {
        return (lastTime / 1000) % 60;
    }

    /**
     * Convenience function.
     * 
     * @return Minutes elapsed since last call to elapsedTime.
     */
    public long minutes() {
        return (lastTime / 1000 / 60) % 60;
    }

    /**
     * Convenience function.
     * 
     * @return Hours elapsed since last call to elapsedTime.
     */
    public long hours() {
        return (lastTime / 1000 / 60 / 60);
    }

    /**
     * Calculates and returns a formatted elapsed time string for display.
     * 
     * @return Formatted elapsed time.
     */
    public String display() {
        long diff = elapsedTime();
        long seconds;
        long minutes;
        long hours;

        // no negative time
        if (diff < 0) {
            diff = 0;
        }

        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;

        return String.format("%d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds);
    }

    /**
     * Resets (and stops) the timer.
     */
    public void reset() {
        running = false;

        lastStopped = time.now();
        startedAt = time.now();
    }

    /**
     * Starts the timer.
     */
    public void start() {
        running = true;

        startedAt = time.now();
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        running = false;

        lastStopped = time.now();
    }

    /**
     * @return True if the timer is running, false if the timer is not.
     */
    public boolean isRunning() {
        return running;
    }

}
