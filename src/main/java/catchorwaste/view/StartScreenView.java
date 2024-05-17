package catchorwaste.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


import static catchorwaste.CatchOrWasteApp.textMap;

import static catchorwaste.model.constants.Constants.TUTORIAL_SCREEN_IMG;
import static catchorwaste.model.constants.Constants.START_SCREEN_IMG;
import static catchorwaste.model.constants.Constants.FONT_SIZE;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class StartScreenView extends StackPane {

    private static StackPane stackPane;
    private static Label startLabel;
    private static ImageView backgroundImageView;

    public static void initStartScreenView() {
        //backgroundImage = new Image(START_SCREEN_IMG);
        stackPane = new StackPane();
        startLabel = new Label(textMap.get("StartScreen").get(0));
        startLabel.setStyle(FONT_SIZE);
        stackPane.setAlignment(Pos.CENTER);

        backgroundImageView = new ImageView(START_SCREEN_IMG);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        stackPane.getChildren().setAll(backgroundImageView, startLabel);
        getGameScene().addUINodes(stackPane);
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
