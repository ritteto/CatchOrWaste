package catchorwaste.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;
import static catchorwaste.model.constants.Constants.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class StartScreenView extends StackPane {

    public StartScreenView() {
        Image backgroundImage = new Image(START_SCREEN_IMG);
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        getChildren().addAll(backgroundImageView);
    }
}
