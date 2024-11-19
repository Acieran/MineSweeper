package org.acieran.minesweeper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest
{
    GameController gameController = new GameController();

    @Test
    void updateDifficulty()
    {
    }

    @Test
    void getSelectedDifficulty()
    {
    }

    @Test
    void isWorking()
    {
    }

    @Test
    void setWorking()
    {
    }

    @ParameterizedTest
    @MethodSource("difficultyData")
    void testInitialize(Difficulty difficulty, int expectedResult) {
        GameController.updateDifficulty(difficulty);
        GameController.initialize();
        assertEquals(expectedResult, GameController.cleanTileCount);
    }

    static Stream<Arguments> difficultyData() {
        return Stream.of(
                Arguments.of(Difficulty.Easy, 54), // Height: 8 Width: 8 Mines: 10 -> 8 * 8 - 10 = 54 CleanTiles
                Arguments.of(Difficulty.Normal, 216), // Height: 16 Width: 16 Mines: 40 -> 16 * 16 - 40 = 216 CleanTiles
                Arguments.of(Difficulty.Hard, 385) // Height: 22 Width: 22 Mines: 99 -> 16 * 16 - 40 = 385 CleanTiles
        );
    }

    @Test
    void startTimer()
    {
    }

    @Test
    void stopTimer()
    {
    }

    @Test
    void stopGame()
    {
    }
}