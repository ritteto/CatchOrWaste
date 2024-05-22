package catchorwaste.view;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static catchorwaste.model.constants.Constants.FONT;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class NewPlayerView {
    public static void initNewPlayerScreenView(){
        Pane pane = new Pane();
        pane.getChildren().addAll(initLabel(), initBoxes());
        pane.setId("NewPlayerPane");

        getGameScene().addUINode(pane);
    }

    private static Pane initLabel(){
        Pane pane = new Pane();

        Label label= new Label("New Player?");
        var fontsize = 20;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setTranslateX(getAppWidth()*0.5-label.getText().length()/2.0*fontsize);
        label.setTranslateY(getAppHeight()*0.35-fontsize);
        label.setId("QuestionLabel");

        Line line = new Line(0, getAppHeight()/2.0, getAppWidth(), getAppHeight()/2.0);
        Line line2 = new Line(getAppWidth()/2.0, 0, getAppWidth()/2.0, getAppHeight());


        pane.getChildren().addAll(line, line2);
        pane.getChildren().add(label);

        return pane;
    }

    private static Pane initBoxes(){
        Pane pane = new Pane();

        Rectangle rectangle = null;
        for (int i=0; i<2; i++) {
            rectangle = new Rectangle(getAppWidth()*0.3+getAppWidth()*0.15*i+getAppWidth()*0.1*i, getAppHeight()*0.45,
                    getAppWidth()*0.15, getAppHeight()*0.1);
            rectangle.setFill(null);
            rectangle.setStroke(Color.BLACK);
            rectangle.setId("Option"+(i+1));
            pane.getChildren().add(rectangle);
        }

        return pane;
    }
}
