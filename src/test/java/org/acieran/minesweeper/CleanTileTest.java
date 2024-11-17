package org.acieran.minesweeper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CleanTileTest {
    @Test
    void testCountProximityMineCount_nullInput() {
        CleanTile cleanTile = new CleanTile(0, 0);
        assertThrows(IllegalArgumentException.class, () -> cleanTile.countProximityMineCount(null));
    }


    @Test
    void open() {
        //TODO Finish testOpen for CleanTile
    }

    @ParameterizedTest
    @ValueSource(ints = {1,3,7})
    void countProximityMineCount(int mines) {
        int mineCount = mines;
        GameBoard gameBoard = new GameBoard(3,3,mines);
        if (!(gameBoard.board[1][1] instanceof CleanTile))
        {
            mineCount--;
            gameBoard.board[1][1] = new CleanTile(1, 1);
        }
        CleanTile tile =(CleanTile) gameBoard.board[1][1];
        tile.countProximityMineCount(gameBoard.board);
        assertEquals(mineCount,tile.proximityMineCount);
        assertTrue(tile.mineCountSet);
    }
}