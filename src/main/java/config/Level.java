package config;

import angleDefenseLogic.*;

import java.util.*;

public class Level {
    private int levelNum;
    private ArrayList<Wave> waves;

    public Level() {

    }

    public void spawnMinions(TimeRange timeRange, Game game) {
        for (Wave w : this.waves) {
            // Spawn the minions if timeSinceStart corresponds to a wave
            int start = w.getStart();
            int end = w.getEnd();
            int count = w.getCount();

            int minionsToSpawn = 5;// TODO: Sam figure this out

            for (int i = 0; i < minionsToSpawn; i++) {
                Location loc = new Location(1, 1);// Figure this out
                Minion m = w.getMinionType().create(loc);
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
