package org.acieran.minesweeper;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class CleanTileTest
{
    //TODO Tests for all methods in CleanTile
    //DONE Constructor(testCleanTile)
    //DONE countProximityMineCount
    //TODO Update Open

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testCleanTile(int count)
    {
        ArrayList<CleanTile> tiles = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            tiles.add(new CleanTile(0, 5));
            assertNotNull(tiles.get(i));
            assertEquals(tiles.get(i).x, 0);
            assertEquals(tiles.get(i).y, 5);
        }
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

    @Test
    void testCountProximityMineCount_nullInput()
    {
        CleanTile cleanTile = new CleanTile(0, 0);
        assertThrows(IllegalArgumentException.class, () -> cleanTile.countProximityMineCount(null));
    }

    //TODO Comments on open plz
    @TestFactory
    Collection<DynamicTest> testOpen() {
        HashMap<Integer,ArrayList<CleanTile>> hashMap = createTestSource();

        List<DynamicTest> tests = new ArrayList<>();
        for (Map.Entry<Integer,ArrayList<CleanTile>> pair: hashMap.entrySet()) {
            String testName = "Test for " + pair.getKey();
            GameBoard gameBoard = new GameBoard(3, 3, 0, false);
            gameBoard.setCleanTiles();

            gameBoard.mineCount = pair.getKey();
            for (int x = 0; x < pair.getKey(); x++)
            {
                gameBoard.board[2 - (x / 3)][2 - (x % 3)] = new Mine(2 - (x % 3), 2 - (x/ 3));
            }
            gameBoard.calculateProximityMines();
            ArrayList<CleanTile> openTiles = new ArrayList<>();
            for (Tile tile: gameBoard.board[0][0].open(gameBoard))
            {
                openTiles.add((CleanTile) tile);
            }

            ArrayList<CleanTile> tilesThatShouldBeOpen = pair.getValue();
            Collections.sort(tilesThatShouldBeOpen);
            Collections.sort(openTiles);

            Executable testExecutable = () -> {
                assertEquals(pair.getValue(),openTiles);
            };
            DynamicTest dynamicTest = dynamicTest(testName, testExecutable);
            tests.add(dynamicTest);
        }
        return tests;
    }

    private HashMap<Integer,ArrayList<CleanTile>> createTestSource()
    {
        ArrayList<CleanTile> tilesToOpen = new ArrayList<>();
        HashMap<Integer,ArrayList<CleanTile>> hashMap = new HashMap<>();
        // Case 7 Mine
        //  M   M   M
        //  M   M   M
        //  2   4   M
        tilesToOpen.add(new CleanTile(0,0));
        hashMap.put(7,tilesToOpen);
        // Case 4 Mine
        //  M   M   M
        //  2   4   M
        //  0   1   1
        tilesToOpen = new ArrayList<>(tilesToOpen);
        tilesToOpen.addAll(List.of(new CleanTile(1,0),
                                    new CleanTile(0,1),
                                    new CleanTile(1,1)));
        hashMap.put(4,tilesToOpen);
        //  Case 3 Mine
        //  M   M   M
        //  2   3   2
        //  0   0   0
        tilesToOpen = new ArrayList<>(tilesToOpen);
        tilesToOpen.addAll(List.of(new CleanTile(2,0),
                                    new CleanTile(2,1)));
        hashMap.put(3,tilesToOpen);
        //  Case 2 Mine
        //  1   M   M
        //  1   2   2
        //  0   0   0
        hashMap.put(2,tilesToOpen);
        //  Case 1 Mine
        //  0   1   M
        //  0   1   1
        //  0   0   0
        tilesToOpen = new ArrayList<>(tilesToOpen);
        tilesToOpen.addAll(List.of(new CleanTile(0,2),
                                    new CleanTile(1,2)));
        hashMap.put(1,tilesToOpen);
        return hashMap;
    }
}