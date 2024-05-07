package catchorwaste.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import static catchorwaste.model.constants.Constants.FONT_SIZE;
import static catchorwaste.model.constants.Constants.START_SCREEN_IMG;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class StartScreenView extends StackPane {

    public StartScreenView() {
        Image backgroundImage = new Image(START_SCREEN_IMG);
        Label startLabel = new Label("Klick zum Starten");
        startLabel.setStyle(FONT_SIZE);
        setAlignment(Pos.CENTER);

        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        getChildren().addAll(backgroundImageView, startLabel);
    }

}
