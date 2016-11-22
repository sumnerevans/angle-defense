package angleDefenseLogic;

import angleDefenseGui.DrawContext;

public class Board implements IDrawable {
    private int width, height;
    private Square[][] squares;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.squares = new Square[width][height];
    }

    @Override
    public void draw(DrawContext drawContext) {
        // TODO Auto-generated method stub

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
