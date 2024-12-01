package org.acieran.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MineSweeperApplication extends Application {

    @FXML
    private GridPane gridPane;
    @FXML
    private Label timerLabel;
    @FXML
    private ChoiceBox<Difficulty> gameDifficulty;
    @FXML
    private Button restartButton;
    @FXML
    private VBox root;
    @FXML
    private Label mineCountLabel;

    private final Map<Tile.MarkItem, String> MARKMAP = new HashMap<>();
    private final ArrayList<String> COLORPICKER = new ArrayList<>();
    
    private double height;
    private double width;

    private GameBoard gameBoard;

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
        initFinals(); //Add icons for every MarkItem
        newGame(); //Initial Start
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
                (_, _, newValue) -> updateDifficulty(newValue)
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
        if (gridPane.getScene()!=null)
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = ge.getScreenDevices();

            int monitorWidth = devices[0].getDisplayMode().getWidth();
            int monitorHeight = devices[0].getDisplayMode().getHeight();

            gridPane.getScene().getWindow().setWidth(width+20);
            gridPane.getScene().getWindow().setHeight(height+95);
            //Additional Window Centering
            gridPane.getScene().getWindow().setX((monitorWidth-(width+20))/2);
            gridPane.getScene().getWindow().setY((monitorHeight-(height+95))/2);
        }
    }

    public void updateDifficulty(Difficulty newDifficulty)
    {
        GameController.updateDifficulty(newDifficulty);
    }

    protected void newGame() {
        GameController.initialize(); //Create Field and init GameControllers
        gameBoard = GameController.getGame();
        gridPane.setDisable(false); //Allow user to click on Grid
        GameController.stopTimer(); // Stop the timer
        mineCountLabel.setText("Mines: " + gameBoard.getMineCount()); // Update the mine count label
        gridPane.getChildren().clear(); // Clear the game board
        width = gameBoard.getWidth()*60; //Set Width based on Field Width (60 is size of element in X)
        height = gameBoard.getHeight()*40; //Set Height based on Field Height (40 is size of element in Y)
        createGrid(gameBoard); //Create GridField Based on Field from GameController.gameField
        sizeHandler(width,height); //Resize All Elements based on current size of Grid
    }

    private void createGrid(GameBoard board) {

        //Matrix cell creation based on gameController.GameField[][]
        for (int i = 0; i < board.getHeight(); i++)
        {
            for (Tile t: board.getBoard()[i])
            {
                Button cellButton = getButton(t);//Create and Set Up Button

                gridPane.add(cellButton, t.getX(), t.getY());
            }
        }
    }

    //Create and Set Up Button Actions and Sizes
    private Button getButton(Tile t)
    {
        Button cellButton = new Button(); //Create new Button
        cellButton.setPrefWidth(width / gameBoard.getWidth()); //Set Width and Height based on num of Tiles
        cellButton.setPrefHeight(height / gameBoard.getHeight());

        cellButton.setOnMouseClicked(event -> { //Events after the Mouse is clicked
            if (GameController.getSeconds() == 0) //Start timer if it wasn't started
                GameController.startTimer(timerLabel);
            if (event.getButton() == MouseButton.PRIMARY) { //If pressed Left Mouse Button
                ArrayList<Tile> tiles = t.open(gameBoard); //Get List of Tiles than needs to be Opened
                for (Tile tile: tiles) {    //Tile Opening for all Tiles in List
                    if (tile instanceof CleanTile) {    //Choice is Clean Tile Opened
                        //TODO Window Centering
                        openTile(tile);
                        if (gameBoard.getCleanTileList().isEmpty()) { //If the game has no Clean Tiles in List it is Won
                            gridPane.setDisable(true);
                            GameController.stopGame();    //Stop game
                            mineCountLabel.setText("YOU WIN!!");
                        }
                    } else if (tile instanceof Mine){   //Choice if Mine Opened
                        cellButton.setText("\uD83D\uDCA5"); //Set text to Mine icon
                        revealAllMines();
                        gridPane.setDisable(true);
                        GameController.stopGame();    //Stop game
                    }
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {    //If pressed Right Mouse Button
                Tile.MarkItem markItem = t.mark();  //Get Next Mark (None -> Mine -> Question -> None)
                cellButton.setText(MARKMAP.get(markItem));  //Set text according to new mark
                if (markItem.equals(Tile.MarkItem.MINE)) {  //If Mark is Mine -> reduce number of mines and update text
                    gameBoard.setMineCount(gameBoard.getMineCount() - 1);
                    mineCountLabel.setText("Mines: " + gameBoard.getMineCount());
                } else if (markItem.equals(Tile.MarkItem.QUESTION)) {   //If Mark is no longer Mine ->
                    gameBoard.setMineCount(gameBoard.getMineCount() + 1);                              //undo mine reduction and update text
                    mineCountLabel.setText("Mines: " + gameBoard.getMineList());
                }
            }
        });
        return cellButton;  //return fully configured mine to be inserted in GridPane
    }

    //Method for setting up icons and colors
    private void initFinals()
    {
        MARKMAP.put(Tile.MarkItem.NONE,"");
        MARKMAP.put(Tile.MarkItem.MINE,"\uD83D\uDCA3");
        MARKMAP.put(Tile.MarkItem.QUESTION,"?");
        COLORPICKER.add(0,"white");
        COLORPICKER.add(1,"rgba(1, 50, 1, 1)");
        COLORPICKER.add(2,"rgba(255, 200, 0, 1)");
        COLORPICKER.add(3,"rgba(255, 255, 20, 1)");
        COLORPICKER.add(4,"#0d637a");
        COLORPICKER.add(5,"#7a0d78");
        COLORPICKER.add(6,"black");
    }

    //Set text in all Tiles in List of Mines to Mine icon
    private void revealAllMines()
    {
        for (Mine mine: gameBoard.getMineList()) {
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getColumnIndex(node) == mine.getX() && GridPane.getRowIndex(node) == mine.getY()) {
                    Button cellButton = (Button) node;
                    cellButton.setText("\uD83D\uDCA3");
                }
            }
        }
    }

    protected void openTile(Tile tile)
    {
        for (Node node : gridPane.getChildren()) {  //Check all gridPane elements to find element that needs to be opened
            if (GridPane.getColumnIndex(node) == tile.getX() && GridPane.getRowIndex(node) == tile.getY()) {
                Button cellButton = (Button) node;
                CleanTile t = (CleanTile) tile;
                cellButton.setDisable(true);    //Make it not clickable
                cellButton.setStyle("-fx-opacity: 0.6; " +  //Set background color and opacity for better text readability
                                    "-fx-background-color: rgba(212, 226, 240, 0.6)");
                if (t.getProximityMineCount() > 0) { //If there are nearby mines set text to Number of Mines nearby
                    cellButton.setText(t.getProximityMineCount() + "");  //Recolor it, increase font
                    cellButton.setStyle("-fx-text-fill: " + COLORPICKER.get(t.getProximityMineCount()) + ";" +
                                        "-fx-font-size: 16;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-opacity: 0.6;" +   //Repeat of above actions, because Style if replaced completely
                                        "-fx-background-color: rgba(212, 226, 240, 0.6)");
                }
                gameBoard.getCleanTileList().remove(tile);   //Additionally remove this Tile from list of CleanTile,
            }                                           //for appropriate win condition check
        }
    }

    public static void main(String[] args) {
        launch();
    }
}