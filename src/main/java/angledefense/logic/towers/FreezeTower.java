package angledefense.logic.towers;

import angledefense.draw.DrawContext;
import angledefense.draw.ModelHandle;
import angledefense.logic.CostManager;
import angledefense.logic.Game;
import angledefense.logic.Location;
import angledefense.logic.Player;
import angledefense.logic.minions.Minion;
import angledefense.util.Util;

public class FreezeTower extends Tower {
    private static ModelHandle freeze = ModelHandle.create("freeze");
    private static ModelHandle feztow = ModelHandle.create("feztow");
    private float slowAmmount;

    public FreezeTower(Player owner, Location location) {
        super(owner, location);
        this.price = CostManager.FreezePriceLevel0;
        this.damage = 3;
        this.range = 4;
        this.fireRate = 1.5f;
        this.slowAmmount = 0.5f;
        this.lazerModel = freeze;
        this.towerModel = feztow;
    }

    @Override
    public void draw(DrawContext drawContext) {
        super.draw(drawContext);
    }

    @Override
    public boolean attack(Minion minion) {
        minion.decreaseSpeed(slowAmmount);
        minion.attacked(this, this.damage);
        return true;
    }

    @Override
    public void upgrade() throws Exception {
        int cost;

        switch (this.level) {
            case 0:
                cost = CostManager.FreezePriceLevel1;
                break;
            case 1:
                cost = CostManager.FreezePriceLevel2;
                break;
            case 2:
                cost = CostManager.FreezePriceLevel3;
                break;
            default:
                throw new Exception("Can't upgrade any more");
        }

        owner.spendGold(cost);

        this.level++;
        this.damage *= 2;
        this.slowAmmount += 0.15;
        if (level >= 2) {
            fireRate += 1;
        }
    }

    @Override
    protected boolean isAreaOfEffect() {
        return true;
    }
}
