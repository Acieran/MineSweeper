package org.acieran.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CleanTile extends Tile{
    protected int proximityMineCount;
    protected boolean mineCountSet;
    protected final static CleanTile nullTile = new CleanTile(-1,-1);
    protected static int cleanTileCount = 0;

    public CleanTile(int x, int y)
    {
        super(x, y);
        cleanTileCount++;
    }

    public int getProximityMineCount() {
        return proximityMineCount;
    }

    protected void countProximityMineCount(Tile[][] smalltile) {
        if (smalltile == null)
            throw new IllegalArgumentException("Передано пустое поле для подсчета мин");
        proximityMineCount = 0;
        for (Tile[] tiles : smalltile)
        {
            for (Tile tile : tiles)
            {
                if (tile instanceof Mine)
                    proximityMineCount++;
            }
        }
        mineCountSet = true;
    }

    @Override
    public ArrayList<Tile> open() {
        super.open();
        ArrayList <Tile> tiles = new ArrayList<>();
        tiles.add(this);
        cleanTileCount--;
        if (proximityMineCount == 0) {
            if (y - 1 >= 0)
                tiles = ((CleanTile) GameBoard.board[y - 1][x]).openProximity(tiles);
            if (y + 1 < GameBoard.board.length)
                tiles = ((CleanTile) GameBoard.board[y + 1][x]).openProximity(tiles);
            if (x - 1 >= 0)
                tiles = ((CleanTile) GameBoard.board[y][x - 1]).openProximity(tiles);
            if (x + 1 < GameBoard.board[y].length)
                tiles = ((CleanTile) GameBoard.board[y][x + 1]).openProximity(tiles);
        }
        return tiles;
    }

    private ArrayList<Tile> openProximity(ArrayList<Tile> tiles)
    {
        tiles.add(this);
        cleanTileCount--;
        if (proximityMineCount == 0)
        {
            if (y - 1 >= 0 && !(tiles.contains(GameBoard.board[y - 1][x])))
                tiles = ((CleanTile) GameBoard.board[y - 1][x]).openProximity(tiles);
            if (y + 1 < GameBoard.board.length && !(tiles.contains(GameBoard.board[y + 1][x])))
                tiles = ((CleanTile) GameBoard.board[y + 1][x]).openProximity(tiles);
            if (x - 1 >= 0 && !(tiles.contains(GameBoard.board[y][x - 1])))
                tiles = ((CleanTile) GameBoard.board[y][x - 1]).openProximity(tiles);
            if (x + 1 < GameBoard.board[y].length && !(tiles.contains(GameBoard.board[y][x + 1])))
                tiles = ((CleanTile) GameBoard.board[y][x + 1]).openProximity(tiles);
        }
        return tiles;
    }
}
