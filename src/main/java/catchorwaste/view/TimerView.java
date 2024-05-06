package catchorwaste.view;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import static catchorwaste.model.constants.Constants.FONT_SIZE;
public class TimerView extends StackPane {
    private final Label timerLabel;

    public TimerView() {
        timerLabel = new Label();
        timerLabel.setStyle(FONT_SIZE);
        StackPane.setMargin(timerLabel, new Insets(20, 0, 0, 20));

        getChildren().add(timerLabel);
    }

    public void updateTimer(int minutes, int seconds) {
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}

