package org.acieran.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;

import java.awt.*;
import java.io.IOException;

public class MineSweeperApplication extends Application {

    @FXML
    private GridPane gameBoard;
    @FXML
    protected Label timerLabel;
    @FXML
    private ChoiceBox<Difficulty> gameDifficulty;
    @FXML
    private Button restartButton;
    @FXML
    private VBox root;
    @FXML
    private Label mineCountLabel;
    
    private double height;
    private double width;

    private static final GameController gameController = new GameController();

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
        //setting gameDifficulty ChoiceBox and Listener
        setGameDifficulty();
        //Setting Restart Button action
        restartButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                newGame();
            }
        });
        newGame();
    }

    private void setGameDifficulty()
    {
        // Populate the ChoiceBox with Enum values
        gameDifficulty.getItems().addAll(Difficulty.values());
        //Selecting Initial Selection
        gameDifficulty.getSelectionModel().selectFirst();
        updateDifficulty(Difficulty.Easy);
        //Adding a Listener for dynamic updates
        gameDifficulty.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    updateDifficulty(newValue);
                }
        );
    }

    private void sizeHandler(double width,double height)
    {
        //Padding between elements
        double padding = 20;
        //Element size relative
        double relElemSize = 0.2;
        //Available width after removing padding
        double effectiveWidth = width-(padding*5);
        //Setting element sizes from left side + Padding
        timerLabel.setLayoutX(padding);
        timerLabel.setPrefWidth(effectiveWidth*2/10);
        gameDifficulty.setLayoutX(effectiveWidth*relElemSize+padding);
        gameDifficulty.setPrefWidth(effectiveWidth*relElemSize*2);
        restartButton.setLayoutX(effectiveWidth*3*relElemSize+padding*2);
        restartButton.setPrefWidth(effectiveWidth*relElemSize);
        mineCountLabel.setLayoutX(effectiveWidth*4*relElemSize+padding*3);
        mineCountLabel.setPrefWidth(effectiveWidth*relElemSize);
        
        //Window resizing for initial launch
        root.setPrefWidth(width);
        root.setPrefHeight(height);
        
        //Window Resize based on current resolution(not working on launch)
        if (gameBoard.getScene()!=null)
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = ge.getScreenDevices();

            int monitorWidth = devices[0].getDisplayMode().getWidth();
            int monitorHeight = devices[0].getDisplayMode().getHeight();

            gameBoard.getScene().getWindow().setWidth(width+20);
            gameBoard.getScene().getWindow().setHeight(height+95);
            //Additional Window Centering
            gameBoard.getScene().getWindow().setX((monitorWidth-(width+20))/2);
            gameBoard.getScene().getWindow().setY((monitorHeight-(height+95))/2);
        }
    }

    public void updateDifficulty(Difficulty newDifficulty)
    {
        GameController.updateDifficulty(newDifficulty);
    }

    protected void newGame() {
        gameController.initialize(timerLabel); //Create Field and init GameControllers
        GameController.stopTimer(); // Stop the timer
        mineCountLabel.setText("Mines: " + gameController.game.mineCount); // Update the mine count label
        gameBoard.getChildren().clear(); // Clear the game board
        width = gameController.game.width*60; //Set Width based on Field Width (60 is size of element in X)
        height = gameController.game.height*40; //Set Height based on Field Height (40 is size of element in Y)
        createGrid(gameController.game); //Create GridField Based on Field from GameController.gameField
        sizeHandler(width,height); //Resize All Elements based on current size of Grid
    }

    private void createGrid(GameBoard board) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);

        
        //Matrix cell creation based on gameController.GameField[][]
        for (int i = 0; i < board.height; i++)
        {
            for (Tile t: board.board[i])
            {
                Button cellButton = getButton(t);//Create and Set Up Button

                gameBoard.add(cellButton, t.y, t.x);
            }
        }
    }

    //Create and Set Up Button Actions and Sizes
    private Button getButton(Tile t)
    {
        Button cellButton = new Button();
        cellButton.setPrefWidth(width / gameController.game.width);
        cellButton.setPrefHeight(height / gameController.game.height);

        cellButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                t.Open();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                t.Mark();
            }
        });
        return cellButton;
    }

    public static void main(String[] args) {
        launch();
    }
}