package catchorwaste.view;

import catchorwaste.model.PunktesystemModel;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.util.Random;

import static catchorwaste.CatchOrWasteApp.textMap;
import static catchorwaste.model.constants.Constants.LEARNING_MESSAGES;
import static catchorwaste.model.constants.Constants.FONT;
public class EndScreenView extends StackPane {

    Label endLabel = new Label();

    public Node scoreEndscreen() {
        int score = PunktesystemModel.getPoints();

        Font font = Font.loadFont(getClass().getResourceAsStream(FONT), 20);
        endLabel.setFont(font);

        endLabel.setLayoutX(364);
        endLabel.setLayoutY(200);
        endLabel.setText("" + score);

        getChildren().add(endLabel);
        return endLabel;
    }

    public Node additionalText() {
        Label additionalTextLabel = new Label(textMap.get("EndScreen").get(0));
        Font font = Font.loadFont(getClass().getResourceAsStream(FONT), 20);
        additionalTextLabel.setFont(font);

        // Erst nach dem Setzen des Texts die Breite abfragen
        additionalTextLabel.applyCss();
        additionalTextLabel.layout();

        additionalTextLabel.setLayoutX(277);  // Zentriere den Text
        additionalTextLabel.setLayoutY(150); // Y-Position anpassen

        getChildren().addAll(additionalTextLabel);
        return additionalTextLabel;
    }

    public Node learningMessage() {
        Random random = new Random();
        int randomMessage = random.nextInt(2);

        System.out.println(textMap.get("Learning messages")+"/"+randomMessage);
        Label learningMessageLabel = new Label(textMap.get("Learning messages").get(randomMessage));
        Font font = Font.loadFont(getClass().getResourceAsStream(FONT), 16);
        learningMessageLabel.setFont(font);

        learningMessageLabel.setLayoutX(40);  // Zentriere den Text
        learningMessageLabel.setLayoutY(250); // Y-Position anpassen


        getChildren().add(learningMessageLabel);
        return learningMessageLabel;
    }

}
