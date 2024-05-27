package catchorwaste.view;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static catchorwaste.CatchOrWasteApp.languageMap;
import static catchorwaste.model.variables.Constants.FONT_SIZE;
import static catchorwaste.model.variables.globalVariables.score;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static javafx.scene.text.FontWeight.BOLD;


public class PunktesystemView extends StackPane {

    private static StackPane stackPane;
    private static Label scoreLabel;

    public void initPunktesystemView() {
        stackPane = new StackPane();
        scoreLabel = new Label();
        scoreLabel.setStyle(FONT_SIZE);
        StackPane.setMargin(scoreLabel, new Insets(80, 0, 0, 20));

        stackPane.getChildren().add(scoreLabel);
        getGameScene().addUINode(stackPane);
    }

    public void updateScore() {
        scoreLabel.setText(score +" "+ languageMap.get("Game").get(0));
    }

    public void displayUpdate(int change, double x, double y) {
        Label label = new Label();
        label.setFont(Font.font("Comic Sans", BOLD, 30));

        if (change > 0) {
            Color color = Color.rgb(28, 232, 35);
            label.setTextFill(color);
            label.setText("+" + change);

        } else if (change < 0) {
            label.setTextFill(Color.RED);
            label.setText(String.valueOf(change));
        }

        label.setLayoutX(x);
        label.setLayoutY(y);

        getGameScene().addChild(label);
        FXGL.getGameTimer().runOnceAfter(() -> getGameScene().removeChild(label), Duration.millis(350));

    }

}
