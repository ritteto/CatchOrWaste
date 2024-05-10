package catchorwaste.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import static catchorwaste.CatchOrWasteApp.textMap;
import static catchorwaste.model.constants.Constants.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class StartScreenView extends StackPane {

    public StartScreenView() {
        Image backgroundImage = new Image(START_SCREEN_IMG);
        Label startLabel = new Label(textMap.get("StartScreen").get(0));
        startLabel.setStyle(FONT_SIZE);
        setAlignment(Pos.CENTER);

        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        getChildren().addAll(backgroundImageView, startLabel);
    }
    public void getTutorial(){
        Image TutorialImage = new Image(TUTORIAL_SCREEN_IMG);
        getTutorial();
        ImageView tutorialImageView = new ImageView(TutorialImage);
        tutorialImageView.setFitWidth(getAppWidth());
        tutorialImageView.setFitHeight(getAppHeight());
        getChildren().add(tutorialImageView);
    }

}
