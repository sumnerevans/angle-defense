package config;

import angleDefenseLogic.*;

import java.util.*;

public class Level {
    private int levelNum;
    private ArrayList<Wave> waves;

    public Level() {

    }

    public void spawnMinions(TimeRange time, Game game) {
        for (Wave w : this.waves) {
            if (time.getEnd() < w.start || time.getStart() > w.end) continue;

            float ma = (time.getStart() - w.start) / w.length() * w.count;
            float mb = (time.getEnd() - w.start) / w.length() * w.count;

            int minionsToSpawn = (int) mb - (int) ma;

            for (int i = 0; i < minionsToSpawn; i++) {
                Location loc = new Location(1, 1);// Figure this out
                Minion m = w.minionType.create(loc);
                game.spawnMinion(m);
            }
        }
    }

    public int getLevelNum() {
        return levelNum;
    }

    public Wave getWave(int index) {
        return this.waves.get(index);
    }
}
