package org.acieran.minesweeper;

import java.util.Random;

public class GameBoard {
    protected static int height;
    protected static int width;
    protected static int mineCount;
    protected static Tile[][] board;

    public GameBoard(Difficulty difficulty) {
        switch (difficulty)
        {
            case Easy:
            {
                height = 8;
                width = 8;
                mineCount = 10;
                board = new Tile[height][width];
            }
            case Normal:
            {
                height = 16;
                width = 16;
                mineCount = 40;
                board = new Tile[height][width];
            }
            case Hard:
            {
                height = 30;
                width = 16;
                mineCount = 99;
                board = new Tile[height][width];
            }
        }
        setBoard();
    }

    public GameBoard(int height,int width,int mineCount)
    {
        GameBoard.height = height;
        GameBoard.width = width;
        GameBoard.mineCount = mineCount;
        board = new Tile[height][width];
        setBoard();
    }

    public static int getHeight()
    {
        return height;
    }

    public static void setHeight(int height)
    {
        GameBoard.height = height;
    }

    public static int getWidth()
    {
        return width;
    }

    public static void setWidth(int width)
    {
        GameBoard.width = width;
    }

    public static int getMineCount()
    {
        return mineCount;
    }

    public static void setMineCount(int mineCount)
    {
        GameBoard.mineCount = mineCount;
    }

    private static void setMines()
    {
        Random random = new Random();

        int minesPlaced = 0;
        while (minesPlaced < mineCount) {
            int row = random.nextInt(height);
            int col = random.nextInt(width);

            if (!(board[row][col] instanceof Mine)) {
                board[row][col] = new Mine();
                minesPlaced++;
            }
        }
    }

    private static void setCleanTiles()
    {
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                if (!(board[i][j] instanceof Mine))
                {
                    board[i][j] = new CleanTile();
                }
            }
        }
    }

    private static void setBoard()
    {
        setMines();
        setCleanTiles();
    }
}
