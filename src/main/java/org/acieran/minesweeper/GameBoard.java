package org.acieran.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameBoard
{
    protected int height;
    protected int width;
    protected int mineCount;
    protected Tile[][] board;
    protected ArrayList<Mine> mineList = new ArrayList<>();
    protected ArrayList<CleanTile> cleanTileList = new ArrayList<>();

    public GameBoard(Difficulty difficulty) {
        switch (difficulty) {
            case Easy: {
                height = 8;
                width = 8;
                mineCount = 10;
                board = new Tile[height][width];
                break;
            }
            case Normal: {
                height = 16;
                width = 16;
                mineCount = 40;
                board = new Tile[height][width];
                break;
            }
            case Hard: {
                height = 22;
                width = 22;
                mineCount = 99;
                board = new Tile[height][width];
                break;
            }
        }
        setBoard();
    }

    public GameBoard(int height, int width, int mineCount) {
        this.height = height;
        this.width = width;
        this.mineCount = mineCount;
        board = new Tile[height][width];
        setBoard();
    }

    public GameBoard(int height, int width, int mineCount, boolean setBoard) {
        this.height = height;
        this.width = width;
        this.mineCount = mineCount;
        board = new Tile[height][width];
        if (setBoard)
            setBoard();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getMineCount() {
        return mineCount;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public ArrayList<Mine> getMineList() {
        return mineList;
    }

    //
    protected void setMines(int mineCount) {
        Random random = new Random();
        this.mineCount = mineCount;
        int minesPlaced = 0;
        while (minesPlaced < mineCount) {
            int y = random.nextInt(height);
            int x = random.nextInt(width);

            if (!(board[y][x] instanceof Mine)) {
                board[y][x] = new Mine(x, y);
                minesPlaced++;
                mineList.add(new Mine(x,y));
            }
        }
    }

    //
    protected void setCleanTiles() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (!(board[y][x] instanceof Mine)) {
                    CleanTile cleanTile = new CleanTile(x, y);
                    board[y][x] = cleanTile;
                    cleanTileList.add(cleanTile);
                }
            }
        }
    }

    //Get smaller version of Board(Tiles around current one) for further calculation
    protected Tile[][] getSmallerBoard(int y, int x)
    {
        Tile[][] smallTile = new Tile[3][3];
        for (int i = 0; i < 3; i++) {
            if ((y==0 && i == 0) || (y==(height-1) && i == 2))
                Arrays.fill(smallTile[i],CleanTile.nullTile);
            else
            {
                if (x==0){
                    smallTile[i][0] = CleanTile.nullTile;
                    smallTile[i][1] = board[y-1+i][x];
                    smallTile[i][2] = board[y-1+i][x+1];
                }
                else if (x==width-1) {
                    smallTile[i][0] = board[y-1+i][x-1];
                    smallTile[i][1] = board[y-1+i][x];
                    smallTile[i][2] = CleanTile.nullTile;
                }
                else
                {
                    smallTile[i] = Arrays.copyOfRange(board[y-1+i],x-1,x+2);
                }
            }
        }
        return smallTile;
    }

    //Calculates Proximity Mine Count for Clean Tile based on Tiles around this one
    protected void calculateProximityMines() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board[y][x] instanceof CleanTile) {
                    ((CleanTile) board[y][x]).countProximityMineCount(getSmallerBoard(y,x));
                }
            }
        }
    }

    private void setBoard() {
        setMines(mineCount);
        setCleanTiles();
        calculateProximityMines();
    }
}
