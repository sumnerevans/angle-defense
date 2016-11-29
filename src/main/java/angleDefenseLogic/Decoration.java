package angleDefenseLogic;

import angleDefenseGui.*;
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

    }
}
