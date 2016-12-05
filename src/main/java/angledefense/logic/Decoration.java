package angledefense.logic;

import angledefense.gui.*;
import com.google.gson.annotations.*;

/**
 * Created by sumner on 11/29/16.
 */
public class Decoration implements IDrawable {
    public enum Type {
        @SerializedName("pillar")
        PILLAR,

        @SerializedName("flag")
        FLAG
    }

    public final Type type;
    public final Location location;

    public Decoration(Type type, Location location) {
        this.type = type;
        this.location = location;
    }

    @Override
    public void draw(DrawContext drawContext) {
        // TODO: Implement
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Decoration that = (Decoration) o;

        return type == that.type && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }
}
