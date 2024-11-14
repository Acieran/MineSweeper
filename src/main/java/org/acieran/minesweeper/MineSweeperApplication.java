package org.acieran.minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MineSweeperApplication extends Application {

    @FXML
    private GridPane gameBoard;

    @FXML
    protected Label timerLabel;
    @FXML
    private ChoiceBox<Difficulty> gameDifficulty;

    @FXML
    private Label mineCountLabel;

    private Timer timer;

    private GameController gameController;

    private int seconds = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MineSweeperApplication.class.getResource("gameboard.fxml"));
        Parent root = fxmlLoader.load();


        // Set the scene and show the stage
        Scene scene = new Scene(root);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        UIManager();
        GameController.initialize(timerLabel);
    }

    private void UIManager() {
        // Populate the ChoiceBox with Enum values
        gameDifficulty.getItems().addAll(Difficulty.values());

        // Set the initial selection (optional)
        gameDifficulty.getSelectionModel().selectFirst();

        gameDifficulty.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    updateDifficulty(newValue);
                }
        );
    }

    public void updateDifficulty(Difficulty newDifficulty)
    {
        GameController.updateDifficulty(newDifficulty);
    }

    // Method to create the grid
//    private GridPane createGrid(int height, int width) {
//        GridPane gridPane = new GridPane();
//        gridPane.setHgap(1);
//        gridPane.setVgap(1);
//
//        // Add buttons to the grid
//        for (int row = 0; row < height; row++) {
//            for (int col = 0; col < width; col++) {
//                Button cellButton = new Button();
//                cellButton.setPrefWidth(MineSweeperApplication.width / width);
//                cellButton.setPrefHeight(MineSweeperApplication.height / height);
//                gridPane.add(cellButton, col, row);
//
//
//                cellButton.setOnMouseClicked(event -> {
//                    if (event.getButton() == MouseButton.PRIMARY) {
//                        System.out.println("Left mouse button clicked!");
//                    } else if (event.getButton() == MouseButton.SECONDARY) {
//                        System.out.println("Right mouse button clicked!");
//                    }
//                });
//            }
//        }
//        return gridPane;
//    }

    public static void main(String[] args) {
        launch();
    }
}