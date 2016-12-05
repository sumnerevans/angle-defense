package angledefense.config;

import angledefense.draw.ModelHandle;
import angledefense.logic.IDrawable;
import angledefense.logic.Location;
import angledefense.draw.DrawContext;

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

    private ModelHandle mymodel;

    private static ModelHandle blankGreen = ModelHandle.create("tile_blank_green");
    private static ModelHandle blankBrown = ModelHandle.create("tile_blank_brown");

    public Square(Location location, SquareType squareType, CliffSide[] cliffSides) {
        this.location = location;
        this.squareType = squareType;
        this.cliffSides = cliffSides;

        switch (squareType) {
            case GROUND:
                mymodel = blankGreen;
                break;
            case TRENCH:
                mymodel = blankBrown;
                break;
        }
    }

    @Override
    public void draw(DrawContext drawContext) {
        mymodel.setTransform(location, 1, 0, 0);
        mymodel.draw();
    }

    public SquareType getSquareType() {
        return this.squareType;
    }

    public CliffSide[] getCliffSides() {
        return this.cliffSides;
    }
}
