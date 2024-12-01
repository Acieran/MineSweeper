package org.acieran.minesweeper;

import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private static Timer timer;
    private static int seconds;

    private static Difficulty selectedDifficulty;
    private static GameBoard game;
    private static int cleanTileCount;

    public static void updateDifficulty(Difficulty newValue) {
        selectedDifficulty = newValue;
    }

    public static int getSeconds()
    {
        return seconds;
    }

    public static GameBoard getGame()
    {
        return game;
    }

    public static int getCleanTileCount()
    {
        return cleanTileCount;
    }

    protected static void initialize() {
        // Initialize the game state
        game = new GameBoard(selectedDifficulty);
        cleanTileCount = game.getHeight() * game.getWidth() - game.getMineCount();
    }

    //Just Timer
    protected static void startTimer(Label timerLabel) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    seconds++;
                    timerLabel.setText("Timer: " + seconds);
                });
            }
        }, 0, 1000);
    }

    //And Timer Stopper
    protected static void stopTimer()
    {
        if (timer != null)
            timer.cancel();
        seconds = 0;
    }

    protected static void stopGame()
    {
        stopTimer();
    }
}