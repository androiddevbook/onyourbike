import com.androiddevbook.onyourbike.utils.Time;

public class SettableTime extends Time {

    public long time;

    @Override
    public long now() {
        return time;
    }
}
