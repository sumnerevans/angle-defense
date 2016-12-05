package angledefense.logic.towers;

import angledefense.gui.*;
import angledefense.logic.*;
import angledefense.logic.minions.*;

public class AirGroundTower extends Tower {

    public AirGroundTower(Player owner, Location location) {
        super(owner, location);

        this.range = 10;
        this.price = 15;
        this.level = 1;
        this.damage = 5;
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
        // TODO Implement
    }

    @Override
    public void upgrade() {
        this.price *= 2;
        this.damage *= 2;
    }
}
