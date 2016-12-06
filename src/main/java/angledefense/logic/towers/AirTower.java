package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.draw.ModelHandle;
import angledefense.logic.CostManager;
import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.minions.GroundUnit;
import angledefense.logic.minions.Minion;

public class AirTower extends Tower {
    private static ModelHandle antiair = ModelHandle.create("antiair");

    public AirTower(Player owner, Location location) {
        super(owner, location);
        this.price = CostManager.AirPriceLevel1;
        this.damage = 5;
        this.range = 5;
        this.fireRate = 3;
        this.towerModel = antiair;
    }

    @Override
    public void draw(DrawContext drawContext) {
        super.draw(drawContext);
    }

    @Override
    public void attack(Minion minion) {

        if (minion.getType() != Minion.Type.GROUND) {
            minion.attacked(this, this.damage);
        }
    }

    @Override
    public void upgrade() throws Exception {
        this.damage *= this.level;
        this.range += 0.5;

        switch (this.level) {
            case 1:
                this.price = CostManager.AirPriceLevel2;
                break;
            case 2:
                this.price = CostManager.AirPriceLevel3;
                break;
            default:
                throw new Exception("Can't upgrade any more");
        }
    }
}
