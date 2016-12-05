package angledefense.logic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
 * Created by sumner on 12/2/16.
 */
public class TimeRange {
    private float start;
    private float end;

    public TimeRange(float start, float end) {
        this.start = start;
        this.end = end;
    }

    public TimeRange(Instant epoch, Instant last, Instant now) {
        this(relativeSecs(epoch, last), relativeSecs(epoch, now));
    }

    public float getLength() {
        return this.end - this.start;
    }

    public float getEnd() {
        return end;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public static float relativeSecs(Instant epoch, Instant time) {
        return epoch.until(time, ChronoUnit.MILLIS) / 1000.0f;
    }

    @Override
    public String toString() {
        return "TimeRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
