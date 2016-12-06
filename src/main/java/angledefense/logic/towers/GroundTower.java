package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.logic.CostManager;
import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.minions.Minion;

import java.awt.event.ActionEvent;

public class GroundTower extends Tower {
    public GroundTower(Player owner, Location location) {
        super(owner, location);
        this.damage = 5;
        this.price = CostManager.GroundPriceLevel1;
        this.range = 5;
        this.fireRate = 3;
    }

    @Override
    public void draw(DrawContext drawContext) {
        super.draw(drawContext);
    }

    @Override
    public void attack(Minion minion) {
        if (minion.getType() == Minion.Type.GROUND) {
            minion.attacked(this, this.damage);
        }
    }

    @Override
    public void upgrade() throws Exception {
        int cost;

        switch (this.level) {
            case 0:
                cost = CostManager.GroundPriceLevel1;
                break;
            case 1:
                cost = CostManager.GroundPriceLevel2;
                break;
            case 2:
                cost = CostManager.GroundPriceLevel3;
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
