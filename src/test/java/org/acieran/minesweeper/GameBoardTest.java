package org.acieran.minesweeper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest
{

    @Test
    void testGameBoardEasyDifficulty() {
        GameBoard board = new GameBoard(Difficulty.Easy);
        assertEquals(8, board.getHeight());
        assertEquals(8, board.getWidth());
        assertEquals(10, board.getMineCount());
        assertNotNull(board.board); // Check that the board array is not null
    }


    @Test
    void testGameBoardNormalDifficulty() {
        GameBoard board = new GameBoard(Difficulty.Normal);
        assertEquals(16, board.getHeight());
        assertEquals(16, board.getWidth());
        assertEquals(40, board.getMineCount());
        assertNotNull(board.board);
    }

    @Test
    void testGameBoardHardDifficulty() {
        GameBoard board = new GameBoard(Difficulty.Hard);
        assertEquals(22, board.getHeight());
        assertEquals(22, board.getWidth());
        assertEquals(99, board.getMineCount());
        assertNotNull(board.board);
    }

    @Test
    void testGameBoardCustomDimensions() {
        GameBoard board = new GameBoard(10, 10, 15);
        assertEquals(10, board.getHeight());
        assertEquals(10, board.getWidth());
        assertEquals(15, board.getMineCount());
        assertNotNull(board.board);
    }

    @ParameterizedTest
    @ValueSource(ints = {0,5,10,40,99})
    void testSetMines(int mines) {
        GameBoard board = new GameBoard(30, 30, mines); // Create a board with no mines
        int minecount = 0;
        for (int y = 0; y < board.height; y++) {
            for (int x = 0; x < board.width; x++) {
                if (board.board[y][x] instanceof Mine) {
                    minecount++;
                }
            }
        }
        assertEquals(mines,minecount);
        assertEquals(mines,board.mineCount);
        assertEquals(mines,board.mineList.size());
    }

    @Test
    void testSetCleanTiles()
    {
        GameBoard board = new GameBoard(3,3,2);
        for (int y = 0; y < board.height; y++) {
            for (int x = 0; x < board.width; x++) {
                assertNotEquals(Tile.class,board.board[y][x].getClass());
            }
        }
        assertEquals(7,board.cleanTileList.size());
    }

    @Test
    void testSetMineCount()
    {
        GameBoard board = new GameBoard(3,3,2);
        for (int y = 0; y < board.height; y++) {
            for (int x = 0; x < board.width; x++) {
                if (board.board[y][x] instanceof CleanTile)
                    assertTrue(((CleanTile) board.board[y][x]).isMineCountSet());
            }
        }
        assertEquals(2,board.mineList.size());
    }


    @ParameterizedTest
    @CsvSource({
            "0, 0, 1, 1",  // Top-left corner
            "0, 4, 1, -1",  // Top-right corner
            "4, 0, -1, 1",  // Bottom-left corner
            "4, 4, -1, -1",  // Bottom-right corner
            "2, 2, 1, 1",    //center
            "0, 3, 1, 0",  //random Top side tile
            "2, 0, 1, 1", //random Left side tile
            "2, 4, 1, -1",  //random Right side tile
            "4, 2, -1, 1", //random Bottom side tile
    })
    void testGetSmallerBoard(int y, int x, int mineDifY, int mineDifX) {
        GameBoard board = new GameBoard(5, 5, 1); // 5x5 board with one mine
        board.board[y + mineDifY][x + mineDifX] = new Mine(x + mineDifX, y + mineDifY);

        Tile[][] smallerBoard = board.getSmallerBoard(y, x);
        assertNotNull(smallerBoard);

        //Check if the mine is correctly placed in the smaller board
        for (int smallY = 0, boardY = y - 1; smallY < 3; smallY++, boardY++) {
            for (int smallX = 0, boardX = x - 1; smallX < 3; smallX++, boardX++) {
                if (boardY < 0 || boardY >= board.height || boardX < 0 || boardX >= board.width) {
                    assertEquals(smallerBoard[smallY][smallX].getClass(), CleanTile.class);
                } else
                    assertEquals(smallerBoard[smallY][smallX].getClass(), board.board[boardY][boardX].getClass());
            }
        }
    }

    @Test
    void testCalculateProximityMines()
    {
        GameBoard board = new GameBoard(3,3,2);
        for (int y = 0; y < board.height; y++) {
            for (int x = 0; x < board.width; x++) {
                if (board.board[y][x] instanceof CleanTile)
                    assertTrue(((CleanTile) board.board[y][x]).isMineCountSet());
            }
        }
        assertEquals(2,board.mineList.size());
    }
}