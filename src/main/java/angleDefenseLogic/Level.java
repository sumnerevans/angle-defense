package angleDefenseLogic;

import java.util.*;

public class Level implements ITickable {
    private int levelNum;
    private ArrayList<Wave> waves;

    public Level() {

    }

    @Override
    public void tick(Game game) {
        // TODO Auto-generated method stub

    }

    public int getLevelNum() {
        return levelNum;
    }

    public Wave getWave(int index) {
        return this.waves.get(index);
    }
}
