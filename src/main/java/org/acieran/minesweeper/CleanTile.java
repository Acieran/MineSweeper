package org.acieran.minesweeper;

import java.util.ArrayList;

public class CleanTile extends Tile{
    private int proximityMineCount;
    private boolean mineCountSet;
    private final static CleanTile nullTile = new CleanTile(-1,-1);

    public CleanTile(int x, int y)
    {
        super(x, y);
    }

    public int getProximityMineCount() {
        return proximityMineCount;
    }

    public boolean isMineCountSet() {
        return mineCountSet;
    }

    public static CleanTile getNullTile()
    {
        return nullTile;
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
        if (proximityMineCount == 0) {
            if (getY() - 1 >= 0)
                tiles = ((CleanTile) GameBoard.board[getY() - 1][getX()]).openProximity(tiles);
            if (getY() + 1 < GameBoard.board.length)
                tiles = ((CleanTile) GameBoard.board[getY() + 1][getX()]).openProximity(tiles);
            if (getX() - 1 >= 0)
                tiles = ((CleanTile) GameBoard.board[getY()][getX() - 1]).openProximity(tiles);
            if (getX() + 1 < GameBoard.board[getY()].length)
                tiles = ((CleanTile) GameBoard.board[getY()][getX() + 1]).openProximity(tiles);
        }
        return tiles;
    }

    private ArrayList<Tile> openProximity(ArrayList<Tile> tiles)
    {
        tiles.add(this);
        if (proximityMineCount == 0)
        {
            if (getY() - 1 >= 0 && !(tiles.contains(GameBoard.board[getY() - 1][getX()])))
                tiles = ((CleanTile) GameBoard.board[getY() - 1][getX()]).openProximity(tiles);
            if (getY() + 1 < GameBoard.board.length && !(tiles.contains(GameBoard.board[getY() + 1][getX()])))
                tiles = ((CleanTile) GameBoard.board[getY() + 1][getX()]).openProximity(tiles);
            if (getX() - 1 >= 0 && !(tiles.contains(GameBoard.board[getY()][getX() - 1])))
                tiles = ((CleanTile) GameBoard.board[getY()][getX() - 1]).openProximity(tiles);
            if (getX() + 1 < GameBoard.board[getY()].length && !(tiles.contains(GameBoard.board[getY()][getX() + 1])))
                tiles = ((CleanTile) GameBoard.board[getY()][getX() + 1]).openProximity(tiles);
        }
        return tiles;
    }
}
