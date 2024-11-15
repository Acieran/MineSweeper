package org.acieran.minesweeper;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private static Timer timer;
    private static int seconds;

    protected static Difficulty selectedDifficulty;

    protected GameBoard game;

    public static void updateDifficulty(Difficulty newValue) {
        selectedDifficulty = newValue;
    }

    public static Difficulty getSelectedDifficulty()
    {
        return selectedDifficulty;
    }

    @FXML
    protected void initialize(Label timerLabel) {
        // Initialize the game state
        game = new GameBoard(selectedDifficulty);
    }

    //Just Timer
    protected static void startTimer(Label timerLabel) {
        timer = new Timer();
        seconds = 0;
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
    }
}