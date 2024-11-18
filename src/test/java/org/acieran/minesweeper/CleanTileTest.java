package org.acieran.minesweeper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CleanTileTest
{
    @ParameterizedTest
    @ValueSource(ints = {1,2,3})
    void testCleanTile(int count)
    {
        ArrayList<CleanTile> tiles = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tiles.add(new CleanTile(0,5));
            assertNotNull(tiles.get(i));
            assertEquals(tiles.get(i).x,0);
            assertEquals(tiles.get(i).y,5);
        }
    }
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
        GameBoard gameBoard = new GameBoard(3, 3, mines, false);
        gameBoard.setCleanTiles();

        for (int i = 0; i < mines; i++)
        {
            GameBoard.board[3 - i][3 - (i / 3)] = new Mine(3 - (i / 3), 3 - i);
        }
        ArrayList<Tile> openTiles = GameBoard.board[0][0].open();
        //assertEquals(3*3-mines,CleanTile.cleanTileCount); //assertBeforeOpening
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 3, 7})
    void countProximityMineCount(int mines)
    {
        int mineCount = mines;
        GameBoard gameBoard = new GameBoard(3, 3, mines);
        if (!(GameBoard.board[1][1] instanceof CleanTile))
        {
            mineCount--;
            GameBoard.board[1][1] = new CleanTile(1, 1);
        }
        CleanTile tile = (CleanTile) GameBoard.board[1][1];
        tile.countProximityMineCount(GameBoard.board);
        assertEquals(mineCount, tile.proximityMineCount);
        assertTrue(tile.mineCountSet);
    }
}