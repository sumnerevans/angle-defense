package angledefense.config;

import angledefense.logic.Game;
import angledefense.logic.TimeRange;

import java.util.ArrayList;
import java.util.Random;

public class Level {
	private int levelNum;
	private ArrayList<Wave> waves;

	public Level() {

	}

	/**
	 * Spawns minions for the given time range
	 *
	 * @param time
	 * @param game
	 */
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

	public boolean hasMoreMinions(float currentTime) {
		for (Wave w : this.waves) {
			if (w.end > currentTime)
				return true;
		}

		return false;
	}
}
