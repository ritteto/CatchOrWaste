package catchorwaste.view;

import catchorwaste.model.PunktesystemModel;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Random;

import static catchorwaste.CatchOrWasteApp.textMap;
import static catchorwaste.model.constants.Constants.FONT;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class EndScreenView extends StackPane {


    public static void initEndscreenView(){
        spawn("ENDSCREEN");
        getGameScene().addUINodes(scoreEndscreen(),additionalText(),learningMessage());
    }

    public static Node scoreEndscreen() {
        int score = PunktesystemModel.getPoints();
        Label endLabel = new Label();

        Font font = Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 20);
        endLabel.setFont(font);

        endLabel.setLayoutX(364);
        endLabel.setLayoutY(200);
        endLabel.setText("" + score);

        //stackpane.getChildren().add(endLabel);
        return endLabel;
    }

    public static Node additionalText() {

        Label additionalTextLabel = new Label(textMap.get("EndScreen").get(0));
        Font font = Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 20);
        additionalTextLabel.setFont(font);

        // Erst nach dem Setzen des Texts die Breite abfragen
        additionalTextLabel.applyCss();
        additionalTextLabel.layout();

        additionalTextLabel.setLayoutX(277);  // Zentriere den Text
        additionalTextLabel.setLayoutY(150); // Y-Position anpassen

        return additionalTextLabel;
    }

    public static Node learningMessage() {
        Random random = new Random();
        int randomIndex = random.nextInt(textMap.get("LearningMessages").size());
        String randomTitle;
        if(randomIndex < textMap.get("LearningMessages").size()/2){
            randomTitle = textMap.get("LearningMessageTitle").get(0);
        }else{
            randomTitle = textMap.get("LearningMessageTitle").get(1);
        }

        String randomMessage = textMap.get("LearningMessages").get(randomIndex);

        Label titleLabel = new Label(randomTitle);
        titleLabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 25));

        Label messageLabel = new Label(randomMessage);
        messageLabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 16));
        messageLabel.setWrapText(true); // Automatisches Umbruch des Textes aktivieren
        messageLabel.setPrefWidth(FXGL.getAppWidth() - 50); // Breite des Labels einstellen, je nach Bedarf
        messageLabel.setAlignment(Pos.CENTER);

        VBox container = new VBox(10); // VBox für die Anordnung von Labels
        container.getChildren().addAll(titleLabel, messageLabel);

        container.setLayoutX(30);
        container.setLayoutY(250);

        //stackpane.getChildren().add(container);
        return container;
    }
}
