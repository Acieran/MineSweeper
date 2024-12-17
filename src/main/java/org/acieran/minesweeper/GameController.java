package org.acieran.minesweeper;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

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

    protected static void openTile(Tile tile, ObservableList<Node> children)
    {
        for (Node node : children) {  //Check all gridPane elements to find element that needs to be opened
            if (GridPane.getColumnIndex(node) == tile.getX() && GridPane.getRowIndex(node) == tile.getY()) {
                Button cellButton = (Button) node;
                CleanTile t = (CleanTile) tile;
                cellButton.setDisable(true);    //Make it not clickable
                cellButton.setStyle("-fx-opacity: 0.6; " +  //Set background color and opacity for better text readability
                        "-fx-background-color: rgba(212, 226, 240, 0.6)");
                if (t.getProximityMineCount() > 0) { //If there are nearby mines set text to Number of Mines nearby
                    cellButton.setText(t.getProximityMineCount() + "");  //Recolor it, increase font
                    cellButton.setStyle("-fx-text-fill: " + MineSweeperApplication.getCOLORPICKER().get(t.getProximityMineCount()) + ";" +
                            "-fx-font-size: 16;" +
                            "-fx-font-weight: bold;" +
                            "-fx-opacity: 0.6;" +   //Repeat of above actions, because Style if replaced completely
                            "-fx-background-color: rgba(212, 226, 240, 0.6)");
                }
                game.getCleanTileList().remove(tile);   //Additionally remove this Tile from list of CleanTile,
            }                                           //for appropriate win condition check
        }
    }

    //Set text in all Tiles in List of Mines to Mine icon
    protected static void revealAllMines(ObservableList<Node> children)
    {
        for (Mine mine: game.getMineList()) {
            for (Node node : children) {
                if (GridPane.getColumnIndex(node) == mine.getX() && GridPane.getRowIndex(node) == mine.getY()) {
                    Button cellButton = (Button) node;
                    cellButton.setText("\uD83D\uDCA3");
                }
            }
        }
    }
}