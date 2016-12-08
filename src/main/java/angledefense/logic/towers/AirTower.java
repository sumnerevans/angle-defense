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
        this.price = CostManager.AirPriceLevel0;
        this.damage = 10;
        this.range = 5;
        this.fireRate = 3;
        this.towerModel = antiair;
    }

    @Override
    public void draw(DrawContext drawContext) {
        super.draw(drawContext);
    }

    @Override
    public void upgrade() throws Exception {
        int cost;

        switch (this.level) {
            case 0:
                cost = CostManager.AirPriceLevel1;
                break;
            case 1:
                cost = CostManager.AirPriceLevel2;
                break;
            case 2:
                cost = CostManager.AirPriceLevel3;
                break;
            default:
                throw new Exception("Can't upgrade any more");
        }

        owner.spendGold(cost);

        this.level++;
        this.damage *= this.level;
        this.range += 0.5;
    }
}
