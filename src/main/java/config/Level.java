package config;

import angleDefenseLogic.*;
import angleDefenseLogic.minions.*;

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

            // Spawn the minions at random start nodes
            for (int i = 0; i < minionsToSpawn; i++) {
                Node[] startNodes = game.getBoard().getStartNodes();
                Node randNode = startNodes[new Random().nextInt(startNodes.length)];
                game.spawnMinion(w.minionType.create(randNode));
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
