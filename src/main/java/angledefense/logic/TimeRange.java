package angledefense.logic;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Created by sumner on 12/2/16.
 */
public class TimeRange {
    private float start;
    private float end;

    public TimeRange(float start, float end, float speed) {
        this.start = start * speed;
        this.end = end * speed;
    }

    public TimeRange(Instant epoch, Instant last, Instant now, float playRate) {
        this(relativeSecs(epoch, last), relativeSecs(epoch, now), playRate);
    }

    public static float relativeSecs(Instant epoch, Instant time) {
        return epoch.until(time, ChronoUnit.MILLIS) / 1000.0f;
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

    @Override
    public String toString() {
        return "TimeRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
