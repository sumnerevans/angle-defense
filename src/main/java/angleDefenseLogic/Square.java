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

    private int x, y;
    private SquareType squareType;
    private List<CliffSide> cliffSides;

    public Square(int x, int y, SquareType squareType, List<CliffSide> cliffSides) {
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

    public List<CliffSide> getCliffSides() {
        return this.cliffSides;
    }
}
