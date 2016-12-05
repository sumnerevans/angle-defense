package angledefense.logic.towers;

import angledefense.logic.*;
import angledefense.logic.minions.*;
import angledefense.draw.DrawContext;

public class GroundTower extends Tower {

    public GroundTower(Player owner, Location location) {
        super(owner, location);
    }

    @Override
    public void draw(DrawContext drawContext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void tick(Game game) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attack(Minion minion) {
        minion.attacked(this, 10);
    }

    @Override
    public void upgrade() {
        // TODO: Implement
    }
}
