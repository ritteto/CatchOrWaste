package catchorwaste.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import static catchorwaste.model.constants.Constants.FONT;
import static catchorwaste.model.constants.Constants.START_SCREEN_IMG;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class StartScreenView extends StackPane {
    private boolean isGameStarted = false;

    public StartScreenView() {
        Label startLabel = new Label("Catch or waste");
        startLabel.setFont(Font.loadFont(getClass().getResourceAsStream(FONT), 20));
        Image backgroundImage = new Image(START_SCREEN_IMG);
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        getChildren().addAll(backgroundImageView, startLabel);
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

}
