package org.acieran.minesweeper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CleanTileTest
{
    //TODO Tests for all methods in CleanTile
    @Test
    void testCountProximityMineCount_nullInput()
    {
        CleanTile cleanTile = new CleanTile(0, 0);
        assertThrows(IllegalArgumentException.class, () -> cleanTile.countProximityMineCount(null));
    }

    //TODO Open Test For Clean tile
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 7})
    void open(int mines)
    {
        int minesSet = 0;
        GameBoard gameBoard = new GameBoard(3, 3, mines, false);
        gameBoard.setCleanTiles();

        for (int i = 0; i < mines; i++)
        {
            gameBoard.board[3 - i][3 - (i / 3)] = new Mine(3 - (i / 3), 3 - i);
        }
        gameBoard.setMineCount();
        gameBoard.board[0][0].open();
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 3, 7})
    void countProximityMineCount(int mines)
    {
        int mineCount = mines;
        GameBoard gameBoard = new GameBoard(3, 3, mines);
        if (!(gameBoard.board[1][1] instanceof CleanTile))
        {
            mineCount--;
            gameBoard.board[1][1] = new CleanTile(1, 1);
        }
        CleanTile tile = (CleanTile) gameBoard.board[1][1];
        tile.countProximityMineCount(gameBoard.board);
        assertEquals(mineCount, tile.proximityMineCount);
        assertTrue(tile.mineCountSet);
    }
}