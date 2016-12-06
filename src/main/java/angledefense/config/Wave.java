package angledefense.config;

import angledefense.logic.minions.Minion;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sumner on 11/22/16.
 */
public class Wave {
	@SerializedName("minion")
	public final Minion.Type minionType;
	public final float start, end;
	public final int count;
	public final JsonObject stats;

	/**
	 * Create a minion
	 * @param minionType
	 * @param start
	 * @param end
	 * @param count
	 * @param stats
	 */
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

	@Override
	public String toString() {
		return "Wave{" +
				"minionType=" + minionType +
				", start=" + start +
				", end=" + end +
				", count=" + count +
				", stats=" + stats +
				'}';
	}
}
