package angleDefenseLogic;

public abstract class Tower implements IDrawable, ITickable {
    int x, y;
    float angle;
    Player owner;

    public abstract void attack(Minion minion);

}
