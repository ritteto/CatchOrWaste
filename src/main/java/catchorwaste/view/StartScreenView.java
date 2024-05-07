package catchorwaste.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import static catchorwaste.model.constants.Constants.FONT_SIZE;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class StartScreenView extends StackPane {

    public StartScreenView() {
        //TODO: add images for requirements
        // Image backgroundImage = new Image("");
        // ImageView backgroundImageView = new ImageView(backgroundImage);
        // backgroundImageView.setFitWidth(getAppWidth());
        // backgroundImageView.setFitHeight(getAppHeight());
        //  getChildren().addAll(backgroundImageView, startLabel);
        Label startLabel = new Label("Klick zum Starten");
        startLabel.setStyle(FONT_SIZE);
        setAlignment(Pos.CENTER);
        getChildren().addAll(startLabel);
    }

}
