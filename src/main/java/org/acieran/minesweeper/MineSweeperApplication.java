package org.acieran.minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    private Label timerLabel;

    @FXML
    private Label mineCountLabel;
    private Timer timer;


    private int seconds = 0;
    private static double height;
    private static double width;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MineSweeperApplication.class.getResource("gameboard.fxml"));
        Parent root = fxmlLoader.load();

        // Get the controller from the loader
        GameController controller = fxmlLoader.getController();

        // Set the scene and show the stage
        Scene scene = new Scene(root);
        stage.setTitle("Minesweeper");
        stage.setScene(scene);
        stage.show();

        // Start the timer (if you want to start it from here)
        controller.startTimer();

        stage.setTitle("MineSweeper");

        // Create the GridPane
//        GridPane grid = createGrid(GameBoard.height, GameBoard.width); // 5 rows, 5 columns
//
//        // Wrap the GridPane in a VBox for layout
//        VBox root = new VBox(10);
//        root.setAlignment(Pos.CENTER);
//        root.setPadding(new Insets(25));
//
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
//            seconds++;
//            timerLabel.setText(String.valueOf(seconds));
//        }));
//        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
//        timeline.play();
//        root.getChildren().add(timerLabel);
//        root.getChildren().add(grid);
//
//        // Create the scene
//        Scene scene = new Scene(root);
//        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
//            width = newValue.doubleValue();
//            System.out.println(width);
//            // Adjust the button's width to 20% of the window's width
//        });
//        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
//            height = newValue.doubleValue();
//            System.out.println(height);
//            // Adjust the button's width to 20% of the window's width
//        });
//        stage.setScene(scene);
//        stage.show();
    }

    // Method to create the grid
    private GridPane createGrid(int height, int width) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);

        // Add buttons to the grid
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Button cellButton = new Button();
                cellButton.setPrefWidth(MineSweeperApplication.width / width);
                cellButton.setPrefHeight(MineSweeperApplication.height / height);
                gridPane.add(cellButton, col, row);


                cellButton.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        System.out.println("Left mouse button clicked!");
                    } else if (event.getButton() == MouseButton.SECONDARY) {
                        System.out.println("Right mouse button clicked!");
                    }
                });
            }
        }
        return gridPane;
    }

    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    int time = Integer.parseInt(timerLabel.getText());
                    time++;
                    timerLabel.setText(String.valueOf(time));
                });
            }
        }, 0, 1000);
    }

    public static void main(String[] args) {
        launch();
    }
}