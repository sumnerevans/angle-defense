package angleDefenseLogic;

public abstract class Tower implements IDrawable, ITickable {
    protected int x, y;
    protected float angle;
    protected float range;
    protected Player owner;
    protected int price;

    public abstract void attack(Minion minion);

    public void setAngle(float angle) {
        // TODO: sets the angle of the tower's shot in radians
    }

    public void setPosition(int x, int y) {
        // TODO: moves the tower to the specified x,y coordinates
    }

}
