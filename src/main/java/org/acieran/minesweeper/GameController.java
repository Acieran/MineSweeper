package org.acieran.minesweeper;

import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    protected static Timer timer;
    protected static int seconds;

    protected static Difficulty selectedDifficulty;

    protected static GameBoard game;
    protected static boolean working;
    protected static int cleanTileCount;

    public static void updateDifficulty(Difficulty newValue) {
        selectedDifficulty = newValue;
    }

    public static Difficulty getSelectedDifficulty()
    {
        return selectedDifficulty;
    }

    public static boolean isWorking() {
        return working;
    }

    public static void setWorking(boolean working) {
        GameController.working = working;
    }

    protected static void initialize() {
        // Initialize the game state
        game = new GameBoard(selectedDifficulty);
        cleanTileCount = game.height * game.width - game.mineCount;
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
        setWorking(false);
        stopTimer();
    }
}