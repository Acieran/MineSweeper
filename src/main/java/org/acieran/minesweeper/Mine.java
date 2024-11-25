package org.acieran.minesweeper;

import java.util.ArrayList;

public class Mine extends Tile{
    public Mine(int x, int y)
    {
        super(x, y);
    }

    @Override
    public ArrayList<Tile> open() {
        super.open();
        GameController.working = false;
        ArrayList<Tile> tile = new ArrayList<>();
        tile.add(this);
        return tile;
    }
}
