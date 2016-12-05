package angledefense.logic.minions;

import angledefense.logic.*;
import angledefense.logic.towers.*;
import com.google.gson.annotations.SerializedName;
import angledefense.config.*;
import com.google.gson.stream.MalformedJsonException;

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
        while (distanceToTravel > 0) {
            Location a = location;
            if (currentNode.next == null) break;
            Location b = currentNode.next.location;
            float nd = Location.dist(a, b);
            if (distanceToTravel < nd) {
                location = Location.lerp(a, b, distanceToTravel / nd);
                break;
            } else {
                distanceToTravel -= nd;
                currentNode = currentNode.next;
                location = currentNode.location;
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

    public boolean shouldRemove() {
        return this.isDead();
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
        this.location = new Location(x, y);
    }

    public void _setLocation(Location l) {
        this.location = l;
    }

    public void _setLocation(float x, float y) {
        this.location = new Location(x, y);
    }

    public Location _getLocation() {
        return this.location;
    }
}
