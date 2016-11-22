package angleDefenseLogic;

import com.google.gson.JsonObject;

/**
 * Created by sumner on 11/22/16.
 */
public class Wave {
    private Minion.Type minionType;
    private int count, start, end;
    private JsonObject stats;

    public Minion.Type getMinionType() {
        return this.minionType;
    }

    public int getCount() {
        return count;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
