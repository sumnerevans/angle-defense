package config;

import angleDefenseLogic.Minion;
import com.google.gson.*;
import com.google.gson.annotations.*;

/**
 * Created by sumner on 11/22/16.
 */
public class Wave {
    @SerializedName("minion")
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

    public int getLength() {
        return this.end - this.start;
    }
}
