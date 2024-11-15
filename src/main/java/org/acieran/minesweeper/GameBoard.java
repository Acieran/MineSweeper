package org.acieran.minesweeper;

import java.util.Random;

public class GameBoard {
    protected int height;
    protected int width;
    protected int mineCount;
    protected Tile[][] board;

    public GameBoard(Difficulty difficulty) {
        switch (difficulty)
        {
            case Easy:
            {
                height = 8;
                width = 8;
                mineCount = 10;
                board = new Tile[height][width];
                break;
            }
            case Normal:
            {
                height = 16;
                width = 16;
                mineCount = 40;
                board = new Tile[height][width];
                break;
            }
            case Hard:
            {
                height = 22;
                width = 22;
                mineCount = 99;
                board = new Tile[height][width];
                break;
            }
        }
        setBoard();
    }

    public GameBoard(int height,int width,int mineCount)
    {
        this.height = height;
        this.width = width;
        this.mineCount = mineCount;
        board = new Tile[height][width];
        setBoard();
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getMineCount()
    {
        return mineCount;
    }

    public  void setMineCount(int mineCount)
    {
        this.mineCount = mineCount;
    }

    private void setMines()
    {
        Random random = new Random();

        int minesPlaced = 0;
        while (minesPlaced < mineCount) {
            int y = random.nextInt(height);
            int x = random.nextInt(width);

            if (!(board[y][x] instanceof Mine)) {
                board[y][x] = new Mine(x,y);
                minesPlaced++;
            }
        }
    }

    private void setCleanTiles()
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (!(board[y][x] instanceof Mine))
                {
                    board[y][x] = new CleanTile(x,y);
                }
            }
        }
    }

    private void setBoard()
    {
        setMines();
        setCleanTiles();
    }
}
