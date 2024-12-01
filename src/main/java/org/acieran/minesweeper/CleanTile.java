package org.acieran.minesweeper;

import java.util.ArrayList;

public class CleanTile extends Tile implements Comparable<CleanTile>{
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

    @Override
    public int compareTo(CleanTile other) {
        // Sort by x-coordinate, then y-coordinate
        int compareX = Integer.compare(this.getX(), other.getX());
        if (compareX != 0) {
            return compareX;
        }
        return Integer.compare(this.getY(), other.getY());
    }

    @Override
    public String toString()
    {

        return this.getClass() + " x - " + this.getX() + " y - " + this.getY() ;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;

        if (obj == null) return false;
        if (!(obj instanceof CleanTile cleanTile)) return false;
        return getX() == cleanTile.getX() && getY() == cleanTile.getY();
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

    public ArrayList<Tile> open(GameBoard gameBoard) {
        super.open(gameBoard);
        ArrayList <Tile> tiles = new ArrayList<>();
        tiles.add(this);
        if (proximityMineCount == 0) {
            for (int yTemp  = getY() - 1; yTemp  <= getY() + 1; yTemp++)
            {
                for (int xTemp = getX() - 1; xTemp <= getX() + 1; xTemp++)
                {
                    if (yTemp >= 0 && yTemp < gameBoard.getBoard().length && xTemp >= 0 && xTemp < gameBoard.getBoard()[yTemp].length && (xTemp != getX() || yTemp != getY()))
                    {
                        tiles = ((CleanTile) gameBoard.getBoard()[yTemp][xTemp]).openProximity(gameBoard, tiles);
                    }
                }
            }
        }
        return tiles;
    }

    private ArrayList<Tile> openProximity(GameBoard gameBoard,ArrayList<Tile> tiles)
    {
        if (!(tiles.contains(gameBoard.getBoard()[getY()][getX()])))
            tiles.add(this);
        if (proximityMineCount == 0)
        {
            for (int yTemp  = getY() - 1; yTemp  <= getY() + 1; yTemp++)
            {
                for (int xTemp = getX() - 1; xTemp <= getX() + 1; xTemp++)
                {
                    if (yTemp >= 0 && yTemp < gameBoard.getBoard().length && xTemp >= 0 && xTemp < gameBoard.getBoard()[yTemp].length && (xTemp != getX() || yTemp != getY()) && !(tiles.contains(gameBoard.getBoard()[yTemp][xTemp])))
                    {
                        tiles = ((CleanTile) gameBoard.getBoard()[yTemp][xTemp]).openProximity(gameBoard, tiles);
                    }
                }
            }
        }
        return tiles;
    }
}
