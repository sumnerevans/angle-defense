package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.minions.Minion;
import angledefense.util.Util;

public class AirGroundTower extends Tower {

    public AirGroundTower(Player owner, Location location) {
        super(owner, location);
        this.damage = 10;
        this.range = 4;
        this.fireRate = 1.5f;
    }

    @Override
    public void draw(DrawContext drawContext) {
        // TODO Auto-generated method stub

    }

    @Override
    public void attack(Minion minion) {
        minion.attacked(this, this.damage);
    }

    @Override
    public void upgrade() {
        this.damage *= this.level;
    }
}
