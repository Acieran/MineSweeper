package org.acieran.minesweeper;

import java.util.ArrayList;

public class CleanTile extends Tile implements Comparable<CleanTile>{
    protected int proximityMineCount;
    protected boolean mineCountSet;
    protected final static CleanTile nullTile = new CleanTile(-1,-1);

    public CleanTile(int x, int y)
    {
        super(x, y);
    }

    public int getProximityMineCount() {
        return proximityMineCount;
    }

    @Override
    public int compareTo(CleanTile other) {
        // Sort by x-coordinate, then y-coordinate
        int compareX = Integer.compare(this.x, other.x);
        if (compareX != 0) {
            return compareX;
        }
        return Integer.compare(this.y, other.y);
    }

    @Override
    public String toString()
    {

        return this.getClass() + " x - " + this.x + " y - " + this.y ;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        CleanTile cleanTile = (CleanTile) obj;
        return x == cleanTile.x && y == cleanTile.y;
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
    public ArrayList<Tile> open(GameBoard gameBoard) {
        super.open(gameBoard);
        ArrayList <Tile> tiles = new ArrayList<>();
        tiles.add(this);
        if (proximityMineCount == 0) {
            if (y - 1 >= 0)
                tiles = ((CleanTile) gameBoard.board[y - 1][x]).openProximity(gameBoard,tiles);
            if (y + 1 < gameBoard.board.length)
                tiles = ((CleanTile) gameBoard.board[y + 1][x]).openProximity(gameBoard,tiles);
            if (x - 1 >= 0)
                tiles = ((CleanTile) gameBoard.board[y][x - 1]).openProximity(gameBoard,tiles);
            if (x + 1 < gameBoard.board[y].length)
                tiles = ((CleanTile) gameBoard.board[y][x + 1]).openProximity(gameBoard,tiles);
        }
        return tiles;
    }

    private ArrayList<Tile> openProximity(GameBoard gameBoard,ArrayList<Tile> tiles)
    {
        tiles.add(this);
        if (proximityMineCount == 0)
        {
            if (y - 1 >= 0 && !(tiles.contains(gameBoard.board[y - 1][x])))
                tiles = ((CleanTile) gameBoard.board[y - 1][x]).openProximity(gameBoard,tiles);
            if (y + 1 < gameBoard.board.length && !(tiles.contains(gameBoard.board[y + 1][x])))
                tiles = ((CleanTile) gameBoard.board[y + 1][x]).openProximity(gameBoard,tiles);
            if (x - 1 >= 0 && !(tiles.contains(gameBoard.board[y][x - 1])))
                tiles = ((CleanTile) gameBoard.board[y][x - 1]).openProximity(gameBoard,tiles);
            if (x + 1 < gameBoard.board[y].length && !(tiles.contains(gameBoard.board[y][x + 1])))
                tiles = ((CleanTile) gameBoard.board[y][x + 1]).openProximity(gameBoard,tiles);
        }
        return tiles;
    }
}
