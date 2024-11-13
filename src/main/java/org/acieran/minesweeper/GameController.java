package org.acieran.minesweeper;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {

    @FXML
    private GridPane gameBoard;

    @FXML
    private Label timerLabel;
    @FXML
    private ChoiceBox<Mode.Difficulty> gameDifficulty;

    @FXML
    private Label mineCountLabel;

    private int rows = 9;
    private int cols = 9;
    private int mines = 10;
    private int[][] board;
    private boolean[][] revealed;
    private boolean gameOver;
    private int minesLeft;
    private Timer timer;
    private int seconds;

    @FXML
    public void initialize() {
        // Initialize the game state
        GameBoard game = new GameBoard();
        board = new int[Mode.height][Mode.width];
        revealed = new boolean[rows][cols];
        gameOver = false;
        minesLeft = mines;

        // Create the gameboard grid with buttons
        createGameBoard();

        // Place mines randomly and calculate adjacent mine counts
        placeMines();
        calculateAdjacentMines();

        // Populate ChoiceBoxes with options
        populateChoiceBoxes();

        // Set the initial selections in the ChoiceBoxes
        gameDifficulty.getSelectionModel().select(String.valueOf(game.mode.difficulty));

        // Set up the ChoiceBox action handlers
        widthChoiceBox.setOnAction(event -> handleWidthChange());
        heightChoiceBox.setOnAction(event -> handleHeightChange());
        mineCountChoiceBox.setOnAction(event -> handleMineCountChange());
    }

    private void createGameBoard() {
        for (int row = 0; row < Mode.height; row++) {
            for (int col = 0; col < Mode.width; col++) {
                Button cellButton = createCellButton(row, col);
                gameBoard.add(cellButton, col, row);
            }
        }
    }

    private void populateChoiceBoxes() {
        // Populate widthChoiceBox
        for (int i = 9; i <= 30; i++) {
            gameDifficulty.getItems().add(Mode.Difficulty.BEGINNER);
        }
    }

    private Button createCellButton(int row, int col) {
        Button button = new Button();
        button.setPrefSize(30, 30);
        button.setStyle("-fx-background-color: #ddd; -fx-border-color: #ccc; -fx-border-width: 1px;");
        button.setOnAction(event -> handleCellClick(row, col));
        return button;
    }

    private void handleCellClick(int row, int col) {
        if (gameOver) {
            return;
        }

        if (revealed[row][col]) {
            return;
        }

        if (board[row][col] == -1) {
            gameOver = true;
            revealAllMines();
            updateCell(row, col, "💣", "#FF0000"); // Red mine
            return;
        }

        revealCell(row, col);

        if (isGameWon()) {
            gameOver = true;
            // You could add a win message here
        }
    }

    private void revealCell(int row, int col) {
        revealed[row][col] = true;
        if (board[row][col] == 0) {
            revealAdjacentEmptyCells(row, col);
        } else if (board[row][col] > 0) {
            updateCell(row, col, String.valueOf(board[row][col]), "#000000"); // Black number
        }
    }

    private void revealAdjacentEmptyCells(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isValidCell(i, j) && !revealed[i][j]) {
                    revealCell(i, j);
                }
            }
        }
    }

    private void revealAllMines() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == -1) {
                    updateCell(row, col, "💣", "#FF0000");
                }
            }
        }
    }

    private void updateCell(int row, int col, String text, String color) {
        Button cellButton = (Button) gameBoard.getChildren().get(row * cols + col);
        cellButton.setText(text);
        cellButton.setTextFill(javafx.scene.paint.Color.web(color));
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private boolean isGameWon() {
        int cellsRevealed = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (revealed[row][col] && board[row][col] != -1) {
                    cellsRevealed++;
                }
            }
        }
        return cellsRevealed == (rows * cols - mines);
    }

    private void placeMines() {
        // ... (Same code as in the previous example)
    }

    private void calculateAdjacentMines() {
        // ... (Same code as in the previous example)
    }

    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    seconds++;
                    timerLabel.setText("Timer: " + String.valueOf(seconds));
                });
            }
        }, 0, 1000);
    }
}