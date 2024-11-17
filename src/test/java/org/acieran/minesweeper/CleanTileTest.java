package org.acieran.minesweeper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CleanTileTest {
    //TODO Create proper Test for mineCount in CleanTile

    // Helper method to create a 3x3 tile array for testing
    static Tile[][] createTileArray(int mineCount) {
        Tile[][] smallTile = new Tile[3][3];
        int minesPlaced = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (minesPlaced < mineCount) {
                    smallTile[i][j] = new Mine(j, i);
                    minesPlaced++;
                } else {
                    smallTile[i][j] = new CleanTile(j, i);
                }
            }
        }
        return smallTile;
    }

    // Method to provide test data for parameterized tests
    static Stream<Arguments> proximityMineCountProvider() {
        return Stream.of(
                Arguments.of(createTileArray(0), 0),   // No mines
                Arguments.of(createTileArray(1), 1),   // One mine
                Arguments.of(createTileArray(2), 2),   // Two mines
                Arguments.of(createTileArray(3), 3),   // Three mines
                Arguments.of(createTileArray(4), 4),  // Four mines
                Arguments.of(createTileArray(5), 5),  // Five mines
                Arguments.of(createTileArray(8), 8),  // Eight mines
                Arguments.of(createTileArray(9), 9)   // Nine mines
        );
    }


    @ParameterizedTest
    @MethodSource("proximityMineCountProvider")
    void testCountProximityMineCount(Tile[][] smallTile, int expectedCount) {
        CleanTile cleanTile = new CleanTile(0, 0); //Location doesn't matter for this test
        cleanTile.countProximityMineCount(smallTile);
        assertEquals(expectedCount, cleanTile.getProximityMineCount());
    }

    @Test
    void testGetProximityMineCount_beforeCount(){
        CleanTile cleanTile = new CleanTile(0,0);
        assertThrows(IllegalStateException.class, () -> cleanTile.getProximityMineCount());
    }

    @Test
    void testCountProximityMineCount_nullInput() {
        CleanTile cleanTile = new CleanTile(0, 0);
        assertThrows(IllegalArgumentException.class, () -> cleanTile.countProximityMineCount(null));
    }


    @Test
    void open() {
        //TODO Finish testOpen for CleanTile
    }

    @Test
    void mark() {
    }

    @ParameterizedTest
    @CsvFileSource
    void countProximityMineCount(Tile[][] smalltile) {

    }
}