package catchorwaste.view;

import catchorwaste.model.PunktesystemModel;
import catchorwaste.model.constants.Constants;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.util.Random;

public class EndScreenView extends StackPane {



    public Node scoreEndscreen() {
        int score = PunktesystemModel.getPoints();
        Label endLabel = new Label();

        //endLabel.setStyle(FONT_SIZE);

        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/ArcadeFont.ttf"), 20);
        endLabel.setFont(font);

        endLabel.setLayoutX(364);
        endLabel.setLayoutY(200);
        endLabel.setText("" + score);

        getChildren().add(endLabel);
        return endLabel;
    }

    public Node additionalText() {
        Label additionalTextLabel = new Label("Deine Punkte: ");  // Text jetzt beim Erstellen des Labels setzen
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/ArcadeFont.ttf"), 20);
        additionalTextLabel.setFont(font);

        // Erst nach dem Setzen des Texts die Breite abfragen
        additionalTextLabel.applyCss();  // Stellt sicher, dass CSS-Eigenschaften, inklusive der Schriftart, angewandt werden
        additionalTextLabel.layout();    // Führt ein Layout-Update durch, um sicherzustellen, dass Größen berechnet werden
        double width = additionalTextLabel.getLayoutBounds().getWidth();

        additionalTextLabel.setLayoutX(277);  // Zentriere den Text
        additionalTextLabel.setLayoutY(150); // Y-Position anpassen


        getChildren().add(additionalTextLabel);
        return additionalTextLabel;
    }

    public Node learningMessage() {

        String [] messages = {Constants.MESSAGE1, Constants.MESSAGE2, Constants.MESSAGE3};

        Random random = new Random();
        int randomMessage = random.nextInt(3);

        Label learningMessageLabel = new Label(messages[randomMessage]);  // Text jetzt beim Erstellen des Labels setzen
        Font font = Font.loadFont(getClass().getResourceAsStream("/fonts/ArcadeFont.ttf"), 16);
        learningMessageLabel.setFont(font);

        learningMessageLabel.setLayoutX(40);  // Zentriere den Text
        learningMessageLabel.setLayoutY(250); // Y-Position anpassen


        getChildren().add(learningMessageLabel);
        return learningMessageLabel;
    }
}
