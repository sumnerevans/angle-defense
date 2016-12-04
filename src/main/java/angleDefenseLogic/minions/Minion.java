package angleDefenseLogic.minions;

import angleDefenseLogic.*;
import angleDefenseLogic.towers.*;
import com.google.gson.annotations.SerializedName;
import config.*;

import java.util.function.*;

public abstract class Minion implements IDrawable, ITickable {
    public enum Type {
        @SerializedName("ground")
        GROUND(GroundUnit::new),

        @SerializedName("air")
        AIR(AirUnit::new);

        private Function<Node, Minion> spawner;

        Type(Function<Node, Minion> s) {
            this.spawner = s;
        }

        public Minion create(Node n) {
            return this.spawner.apply(n);
        }
    }

    protected boolean dead = false;
    protected int health;
    protected int goldReward;
    protected Location location;
    protected Node currentNode;

    protected Minion(Node node) {
        this.location = node.location;
        this.currentNode = node;
    }

    public void moveForward(float distanceToTravel) {
        // I don't think this should happen, but just in case
        if (this.currentNode == null) return;

        if (this.currentNode.next == null) return;

        if (this.currentNode.location.getX() == this.currentNode.next.location.getX()) {
            // The minion should move vertically
            float distToNext = this.currentNode.next.location.getY() - this.location.getY();

            if (distanceToTravel > Math.abs(distToNext)) {
                // Just go to the next node and recursively call moveForward
                this.moveToNode(this.currentNode.next);
                this.moveForward(distanceToTravel - distToNext);
            } else {
                // Linearly propagate it
                this.location.setY(this.location.getY() + distanceToTravel);
            }
        } else if (this.currentNode.location.getY() == this.currentNode.next.location.getY()) {
            // The minion should move horizontally
            float distToNext = this.currentNode.next.location.getX() - this.location.getX();

            if (distanceToTravel > Math.abs(distToNext)) {
                // Just go to the next node and recursively call moveForward
                this.moveToNode(this.currentNode.next);
                this.moveForward(distanceToTravel - distToNext);
            } else {
                // Linearly propagate it
                this.location.setX(this.location.getX() + distanceToTravel);
            }
        }
    }

    abstract protected void receiveDamage(Tower tower, int amount);

    public void attacked(Tower tower, int amount) {
        this.receiveDamage(tower, amount);

        if (this.health <= 0) {
            this.dead = true;
            tower.getOwner().addGold(this.goldReward);
        }
    }

    public boolean isDead() {
        return this.dead;
    }

    private void moveToNode(Node n) {
        this.currentNode = n;

        if (n == null)
            this.location = null;
        else
            this.location = n.location;
    }

    // Testing Only
    public void _setLocation(int x, int y) {
        this.location.setX(x);
        this.location.setY(y);
    }

    public void _setLocation(Location l) {
        this.location = l;
    }

    public void _setLocation(float x, float y) {
        this.location.setX(x);
        this.location.setY(y);
    }

    public Location _getLocation() {
        return this.location;
    }
}
