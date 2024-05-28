package catchorwaste.view.screens;

import catchorwaste.model.variables.globalVariables;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.Random;

import static catchorwaste.CatchOrWasteApp.imageMap;
import static catchorwaste.CatchOrWasteApp.languageMap;
import static catchorwaste.model.variables.Constants.FONT;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class EndScreenView extends StackPane {


    public void initEndscreenView(){

        ImageView backgroundImageView = new ImageView(imageMap.get("endscreen"));
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());

        getGameScene().addUINodes(backgroundImageView,titleLabel(),scoreEndscreen(),additionalText(),learningMessage(),
                button());
    }

    private Node titleLabel(){
        Label label = new Label(languageMap.get("EndScreen").get(1));
        var fontsize = 30;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setTranslateX(getAppWidth()/2.0-label.getText().length()/2.0*fontsize);
        label.setTranslateY(getAppHeight()*0.1);
        return label;
    }

    private Node scoreEndscreen() {
        Label endLabel = new Label();

        var fontsize = 25;
        endLabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        endLabel.setText(String.valueOf(globalVariables.score));

        endLabel.setLayoutX(getAppWidth()*0.5-endLabel.getText().length()/2.0*fontsize);
        endLabel.setLayoutY(getAppHeight()*0.3);

        return endLabel;
    }

    public Node additionalText() {

        Label additionalTextLabel = new Label(languageMap.get("EndScreen").get(0));

        var fontsize = 20;
        additionalTextLabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));


        additionalTextLabel.setTranslateX(getAppWidth()/2.0-additionalTextLabel.getText().length()/2.0*fontsize);
        additionalTextLabel.setTranslateY(getAppHeight()*0.2);

        return additionalTextLabel;
    }

    public Node learningMessage() {
        Random random = new Random();
        int randomIndex = random.nextInt(languageMap.get("LearningMessages").size());
        String randomTitle;
        if(randomIndex < languageMap.get("LearningMessages").size()/2){
            randomTitle = languageMap.get("LearningMessageTitle").get(0);
        }else{
            randomTitle = languageMap.get("LearningMessageTitle").get(1);
        }

        String randomMessage = languageMap.get("LearningMessages").get(randomIndex);

        Label titleLabel = new Label(randomTitle);
        titleLabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 25));

        Label messageLabel = new Label(randomMessage);
        messageLabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 16));
        messageLabel.setWrapText(true); // Automatisches Umbruch des Textes aktivieren
        messageLabel.setPrefWidth(FXGL.getAppWidth() - 50); // Breite des Labels einstellen, je nach Bedarf
        messageLabel.setAlignment(Pos.CENTER);

        VBox container = new VBox(10); // VBox für die Anordnung von Labels
        container.getChildren().addAll(titleLabel, messageLabel);

        container.setLayoutX(getAppWidth()*0.04);
        container.setLayoutY(getAppHeight()*0.42);

        return container;
    }

    private Pane button(){
        Pane pane = new Pane();
        Rectangle rectangle = new Rectangle(getAppWidth()*0.5-getAppWidth()*0.1, getAppHeight()*0.85,
                getAppWidth()*0.2, getAppHeight()*0.1);

        rectangle.setFill(Color.rgb(0,219,2));
        rectangle.setArcWidth(15);
        rectangle.setArcHeight(15);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(5);
        pane.getChildren().add(rectangle);

        Label label = new Label(languageMap.get("EndScreen").get(2));
        var fontsize = 15;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setTranslateX(rectangle.getX()+rectangle.getWidth()*0.5-label.getText().length()/2.0*fontsize);
        label.setTranslateY(rectangle.getY()+rectangle.getHeight()*0.5-fontsize/2.0);
        pane.getChildren().add(label);

        return pane;
    }
}
