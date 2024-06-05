package catchorwaste.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;


import static catchorwaste.CatchOrWasteApp.textMap;

import static catchorwaste.model.constants.Constants.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class StartScreenView extends StackPane {

    private static StackPane stackPane;
    private static Label startLabel;
    private static ImageView backgroundImageView;

    public static void initStartScreenView() {
        stackPane = new StackPane();
        startLabel = new Label(textMap.get("StartScreen").get(0));
        startLabel.setStyle(FONT_SIZE);
        Font font = Font.loadFont(StartScreenView.class.getResourceAsStream(FONT), 20);
        startLabel.setFont(font);
        stackPane.setAlignment(Pos.CENTER);

        backgroundImageView = new ImageView(START_SCREEN_IMG);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        stackPane.getChildren().setAll(backgroundImageView, startLabel);
        getGameScene().addUINodes(stackPane);
    }
}
