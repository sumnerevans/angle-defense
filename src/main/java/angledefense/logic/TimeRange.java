package angledefense.logic;

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
