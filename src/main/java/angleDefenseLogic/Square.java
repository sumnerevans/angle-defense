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

    public enum Decoration {
        FLAG, PILLAR
    }

    private int x, y;
    private SquareType squareType;
    private List<CliffSide> cliffSides;
    private List<Decoration> decorations;

    public Square(int x, int y, SquareType squareType, List<CliffSide> cliffSides,
                  List<Decoration> decorations) {
        this.x = x;
        this.y = y;
        this.squareType = squareType;
        this.cliffSides = cliffSides;
        this.decorations = decorations;
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

    public List<Decoration> getDecorations() {
        return this.decorations;
    }
}
