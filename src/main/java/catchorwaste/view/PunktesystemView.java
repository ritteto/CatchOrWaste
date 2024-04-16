package catchorwaste.view;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;

import static catchorwaste.model.constants.Constants.FONT_SIZE;


public class PunktesystemView extends StackPane {
    private final Label scoreLabel;

    public PunktesystemView() {
        scoreLabel = new Label();
        scoreLabel.setStyle(FONT_SIZE);

        StackPane.setMargin(scoreLabel, new Insets(80, 0, 0, 20)); // up: 80, left: 20

        getChildren().add(scoreLabel);
    }

    public void updateScore(double score) {
        scoreLabel.setText(score +" Punkte");
    }
}
