package catchorwaste.view.screens;

import javafx.scene.image.ImageView;

import static catchorwaste.CatchOrWasteApp.imageMap;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;


public class TutorialView {

    public void initTutorialView(){
        ImageView backgroundImageView = new ImageView(imageMap.get("tutorial-test-1"));
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        getGameScene().addUINodes(backgroundImageView);
    }
}
