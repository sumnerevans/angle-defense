package config;

import angleDefenseLogic.minions.*;
import com.google.gson.*;
import com.google.gson.annotations.*;

/**
 * Created by sumner on 11/22/16.
 */
public class Wave {
    @SerializedName("minion")
    public final Minion.Type minionType;
    public final float start, end;
    public final int count;
    private final JsonObject stats;

    public Wave(Minion.Type minionType, float start, float end, int count, JsonObject stats) {
        this.minionType = minionType;
        this.start = start;
        this.end = end;
        this.count = count;
        this.stats = stats;
    }

    public float length() {
        return end - start;
    }
}
