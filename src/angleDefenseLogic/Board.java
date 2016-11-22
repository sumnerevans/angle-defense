package angleDefenseLogic;

import angleDefenseGui.*;

public class Board implements IDrawable {

    Square[][] squares;

    public Board(int x, int y) {
        this.squares = new Square[x][y];
    }

    @Override
    public void draw(DrawContext drawContext) {
        // TODO Auto-generated method stub
        
    }

}
