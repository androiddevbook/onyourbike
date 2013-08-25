import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimerStateTests {

    private TimerStateTestable timer;

    public TimerStateTests() {
    }

    @Before
    public void setUp() throws Exception {
        timer = new TimerStateTestable();
    }

    @After
    public void tearDown() throws Exception {
        timer = null;
    }

    // private void delay() {
    // try {
    // Thread.sleep(1000);
    // } catch (InterruptedException e) {
    // // do nothing
    // }
    // }

    @Test
    public void initialState() {
        assertTrue("ElapsedTime is 0", timer.elapsedTime() == 0);
        assertTrue("Seconds is 0", timer.seconds() == 0);
        assertTrue("Minutes is 0", timer.minutes() == 0);
        assertTrue("Hours is 0", timer.hours() == 0);
        assertTrue("IsRunning is false", timer.isRunning() == false);
    }

    @Test
    public void timerStarted() {
        timer.start();
        assertTrue("IsRunning is true", timer.isRunning() == true);
        timer.setElapsededTime(1000);
        assertTrue("ElapsedTime is not 0", timer.elapsedTime() > 0);
    }

    @Test
    public void timerStoped() {
        timer.start();
        assertTrue("IsRunning is true", timer.isRunning() == true);
        timer.setElapsededTime(1000);
        assertTrue("ElapsedTime is not 0", timer.elapsedTime() > 0);
        timer.stop();
        assertTrue("IsRunning is true", timer.isRunning() == false);
        assertTrue("ElapsedTime is not 0", timer.elapsedTime() > 0);
    }

    @Test
    public void timerReset() {
        timer.start();
        assertTrue("IsRunning is true", timer.isRunning() == true);
        timer.setElapsededTime(1000);
        assertTrue("ElapsedTime is not 0", timer.elapsedTime() > 0);
        timer.reset();
        assertTrue("ElapsedTime is 0", timer.elapsedTime() == 0);
        assertTrue("Seconds is 0", timer.seconds() == 0);
        assertTrue("Minutes is 0", timer.minutes() == 0);
        assertTrue("Hours is 0", timer.hours() == 0);
        assertTrue("IsRunning is false", timer.isRunning() == false);
    }

    @Test
    public void displayTime() {
        timer.setElapsededTime(2 * 1000 * 60 * 60 + 25 * 1000 * 60 + 7 * 1000);
        timer.stop();
        assertTrue("Time is correct", timer.display().equals("2:25:07"));
    }

    @Test
    public void oneMinute() {
        timer.setElapsededTime(1000 * 60);
        timer.stop();
        assertTrue("One minute has passed", timer.display().equals("0:01:00"));
    }

    @Test
    public void oneHour() {
        timer.setElapsededTime(1000 * 60 * 60);
        timer.stop();
        assertTrue("One hour has passed", timer.display().equals("1:00:00"));
    }

    @Test
    public void tenHours() {
        timer.setElapsededTime(10 * 1000 * 60 * 60);
        timer.stop();
        assertTrue("Ten hours has passed", timer.display().equals("10:00:00"));
    }

}
