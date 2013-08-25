import com.androiddevbook.onyourbike.chapter10.model.TimerState;

public class TimerStateTestable extends TimerState {

    public TimerStateTestable() {
        super();
        time = new SettableTime();
    }

    public void setElapsededTime(long value) {
        ((SettableTime) time).time = value;
    }
}
