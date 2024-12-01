package org.acieran.minesweeper;

import java.util.ArrayList;

public class Mine extends Tile{
    public Mine(int x, int y)
    {
        super(x, y);
    }

    @Override
    public ArrayList<Tile> open(GameBoard gameBoard) {
        super.open(gameBoard);
        ArrayList<Tile> tile = new ArrayList<>();
        tile.add(this);
        return tile;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (obj == null) return false;
        if (!(obj instanceof Mine mine)) return false;
        return getX() == mine.getX() && getY() == mine.getY();
    }
}
