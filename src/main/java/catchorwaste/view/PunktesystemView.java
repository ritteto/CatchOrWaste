package catchorwaste.view;

import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static catchorwaste.model.constants.Constants.FONT_SIZE;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static javafx.scene.text.FontWeight.BOLD;


public class PunktesystemView extends StackPane {
    private static Label scoreLabel;

    public PunktesystemView() {
        scoreLabel = new Label();
        scoreLabel.setStyle(FONT_SIZE);

        StackPane.setMargin(scoreLabel, new Insets(80, 0, 0, 20)); // up: 80, left: 20

        getChildren().add(scoreLabel);
    }

    public static void initPunkteSystemView(){
        scoreLabel = new Label();
        scoreLabel.setStyle(FONT_SIZE);

        scoreLabel.setLayoutX(20);
        scoreLabel.setLayoutY(80);
        updateScore(0);

        getGameScene().addChild(scoreLabel);
    }

    public static void updateScore(double score) {
        scoreLabel.setText((int) score +" Punkte");
    }

    public static void displayUpdate(double change, double x, double y){
        Label label = new Label();
        label.setFont(Font.font("Comic Sans", BOLD, 30));
        change = (int) change;

        if(change > 0){
            Color color = Color.rgb(28, 232, 35);
            label.setTextFill(color);
            label.setText("+"+(int)change);

        }else if(change < 0){
            label.setTextFill(Color.RED);
            label.setText(String.valueOf((int)change));
        }

        label.setLayoutX(x);
        label.setLayoutY(y);

        getGameScene().addChild(label);
        FXGL.getGameTimer().runOnceAfter(()-> getGameScene().removeChild(label), Duration.millis(350));

    }


}
