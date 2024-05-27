package catchorwaste.view;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import static catchorwaste.model.variables.Constants.FONT_SIZE;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class TimerView extends StackPane {

    private static StackPane stackPane;
    private static Label timerLabel;

    public static void initTimerView() {
        stackPane = new StackPane();
        timerLabel = new Label();
        timerLabel.setStyle(FONT_SIZE);
        StackPane.setMargin(timerLabel, new Insets(20, 0, 0, 20));

        stackPane.getChildren().add(timerLabel);
        getGameScene().addUINode(stackPane);
    }

    public static void updateTimer(int minutes, int seconds) {
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));

    }
}

