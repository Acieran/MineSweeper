package org.acieran.minesweeper;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    private Label timerLabel;

    private int time = 0;

    private Timer timer;

    @FXML
    protected void onHelloButtonClick() {
        // Start the timer only if it's not already running
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // Safely update the UI on the FX Application Thread
                    Platform.runLater(() -> {
                        time++;
                        timerLabel.setText(time + "");
                    });
                }
            }, 0, 1000); // Update every 1000 milliseconds (1 second)
        }
    }
}