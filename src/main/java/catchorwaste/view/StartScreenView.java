package catchorwaste.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;

import static catchorwaste.model.constants.Constants.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class StartScreenView extends StackPane {

    private boolean tutorialVisible = false;
    // adding a boolean to help with toggleTutorial



    public StartScreenView() {
        Image backgroundImage = new Image(START_SCREEN_IMG);
        Label startLabel = new Label("Catch or Waste");
        startLabel.setStyle(FONT_SIZE);
        setAlignment(Pos.CENTER);

        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        getChildren().addAll(backgroundImageView, startLabel);
    }

  /*  public void toggleTutorial(){
        if(tutorialVisible){
            //remove Tutorial
            getChildren().remove(getChildren().size()-1);
        }else{
            //Add tutorial
            getChildren().add(getTutorial());
        }
        tutorialVisible = !tutorialVisible;
    }*/



    public Node getTutorial() {
        Image TutorialImage = new Image(TUTORIAL_SCREEN_IMG);
        ImageView tutorialImageView = new ImageView(TutorialImage);
        Label tutorialStart = new Label("Sort out the clothes");
        tutorialStart.setStyle(FONT_SIZE);
        setAlignment(Pos.TOP_CENTER);

        tutorialImageView.setFitWidth(getAppWidth());
        tutorialImageView.setFitHeight(getAppHeight());

        getChildren().add(tutorialStart);


        return tutorialImageView;
    }


}
