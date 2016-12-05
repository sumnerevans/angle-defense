package config;

import angleDefenseLogic.IDrawable;
import draw.DrawContext;

public class Square implements IDrawable {
    public enum SquareType {
        GROUND, TRENCH
    }

    public enum CliffSide {
        TOP, BOTTOM, LEFT, RIGHT
    }

    private int x, y;
    private SquareType squareType;
    private CliffSide[] cliffSides;

    public Square(int x, int y, SquareType squareType, CliffSide[] cliffSides) {
        this.x = x;
        this.y = y;
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