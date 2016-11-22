package angleDefenseLogic;

public abstract class Tower implements IDrawable, ITickable {
    protected int x, y;
    protected float angle;
    protected Player owner;
    protected int price;

    public abstract void attack(Minion minion);

}
