package angleDefenseLogic;

import angleDefenseGui.DrawContext;

import java.util.*;

public class Square implements IDrawable {
    public enum SquareType {
        GROUND, TRENCH
    }

    public enum CliffSide {
        TOP, BOTTOM, LEFT, RIGHT
    }

    private Location location;
    private SquareType squareType;
    private CliffSide[] cliffSides;

    public Square(Location location, SquareType squareType, CliffSide[] cliffSides) {
        this.location = location;
        this.squareType = squareType;
        this.cliffSides = cliffSides;
    }

    @Override
    public void draw(DrawContext drawContext) {
        // TODO Auto-generated method stub

    }

    public SquareType getSquareType() {
        return this.squareType;
    }

    public CliffSide[] getCliffSides() {
        return this.cliffSides;
    }
}
