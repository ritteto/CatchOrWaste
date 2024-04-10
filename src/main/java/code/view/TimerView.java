package code.view;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;


public class TimerView extends StackPane {
    private final Label timerLabel;

    public TimerView() {
        timerLabel = new Label();
        timerLabel.setStyle("-fx-font-size: 50px;");

        StackPane.setMargin(timerLabel, new Insets(20, 0, 0, 20)); // up: 20, left: 20

        getChildren().add(timerLabel);
    }

    public void updateTimer(int minutes, int seconds) {
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}

