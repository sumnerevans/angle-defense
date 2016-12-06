package angledefense.logic.minions;

import angledefense.config.Node;
import angledefense.draw.DrawContext;
import angledefense.draw.ModelHandle;
import angledefense.logic.Game;
import angledefense.logic.IDrawable;
import angledefense.logic.ITickable;
import angledefense.logic.Location;
import angledefense.logic.towers.Tower;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.function.Function;

public abstract class Minion implements IDrawable, ITickable {
    private static ModelHandle teapot = ModelHandle.create("teapot");
    protected boolean dead = false;
    protected int health;
    protected int goldReward;
    protected Location location;
    protected Node currentNode;
    protected float speed;
    protected float size = 1;
    protected float rotation = Float.NaN;

    protected int owchticks = 0;

    protected Minion(Node node) {
        this.location = node.location;
        this.currentNode = node;
    }

    public void loadStats(JsonObject stats) {
        if (stats == null) return;
        JsonElement health = stats.get("health");
        if (health != null) {
            this.health = health.getAsInt();
        }
        JsonElement speed = stats.get("speed");
        if (speed != null) {
            this.speed = speed.getAsFloat();
        }
        JsonElement size = stats.get("size");
        if (size != null) {
            this.size = size.getAsFloat();
        }
    }

    public void moveForward(float distanceToTravel) {
        while (distanceToTravel > 0) {
            Location current = location;
            if (currentNode.next == null) {
                this.dead = true;
                break;
            }

            Location next = currentNode.next.location;

            float nd = Location.dist(current, next);
            if (distanceToTravel < nd) {
                location = Location.lerp(current, next, distanceToTravel / nd);
                break;
            } else {
                distanceToTravel -= nd;
                currentNode = currentNode.next;
                location = currentNode.location;
            }
        }
    }

    @Override
    public void tick(Game game, float dt) {
        Location a = location;
        this.moveForward(this.speed * dt);
        Location b = location;
        rotation = Location.angle(a, b);

        if (owchticks > 0) owchticks--;
    }

    @Override
    public void draw(DrawContext drawContext) {
        if (owchticks > 0) drawContext.setColorMult(1f, .2f, .2f);
        teapot.setTransform(location, size, -1, rotation);
        teapot.draw();
        drawContext.setColorMult(1f, 1f, 1f);
    }

    abstract protected void receiveDamage(Tower tower, int amount);

    public void attacked(Tower tower, int amount) {
        this.receiveDamage(tower, amount);

        owchticks = 10;

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

    public boolean gotToCastle() {
        return currentNode.next == null;
    }

    public Location getLocation() {
        return this.location;
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
}
