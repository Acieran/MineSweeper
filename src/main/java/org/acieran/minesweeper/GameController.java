package org.acieran.minesweeper;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {


    private static GameBoard game;
    private static Timer timer;
    private static int seconds = 0;

    protected static Difficulty selectedDifficulty = Difficulty.Easy;

    public static int getSeconds()
    {
        return seconds;
    }

    public static void setSeconds(int seconds)
    {
        GameController.seconds = seconds;
    }

    public static void updateDifficulty(Difficulty newValue) {
        selectedDifficulty = newValue;
    }

    public static Difficulty getSelectedDifficulty()
    {
        return selectedDifficulty;
    }

    @FXML
    public static void initialize(Label timerLabel) {
        // Initialize the game state
        game = new GameBoard(selectedDifficulty);
        System.out.println("Done");
        //startTimer(timerLabel);
    }



    public static void startTimer(Label timerLabel) {
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
}