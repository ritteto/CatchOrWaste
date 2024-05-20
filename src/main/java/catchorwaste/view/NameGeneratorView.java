package catchorwaste.view;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


import static catchorwaste.model.constants.Constants.FONT;
import static catchorwaste.model.constants.Constants.START_SCREEN_IMG;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class NameGeneratorView {
    public static void initNameGeneratorView(){
        Pane pane = new Pane();


        var backgroundImageView = new ImageView(START_SCREEN_IMG);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());

        pane.getChildren().addAll(backgroundImageView, initBoxes(), initSubmit());
        getGameScene().addUINode(pane);

    }

    private static Pane initSubmit(){
        Pane pane = new Pane();

        var submitBtn = new Rectangle(getAppWidth()*0.225, getAppHeight()*0.75, getAppWidth()*0.55, getAppHeight()*0.1);
        submitBtn.setFill(Color.rgb(183,100,54));
        submitBtn.setStroke(Color.BLACK);
        submitBtn.setStrokeWidth(5);
        submitBtn.setArcWidth(20);
        submitBtn.setArcHeight(20);

        pane.getChildren().add(submitBtn);

        var label = new Label("Submit");
        var fontsize = 30;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setTextFill(Color.rgb(126,56,28));
        label.setTranslateX(submitBtn.getX()+submitBtn.getWidth()/2.0-fontsize*label.getText().length()/2.0);
        label.setTranslateY(submitBtn.getY()+submitBtn.getHeight()/2.0-fontsize/2.0);

        pane.getChildren().add(label);

        return pane;
    }

    private static Pane initBoxes(){
        Pane pane = new Pane();

        for (int i = 0; i < 3; i++) {
            var rectangle = new Rectangle(getAppWidth()*0.1+getAppWidth()*0.25*i+getAppWidth()*0.025*i,
                    getAppHeight()*0.25, getAppWidth()*0.25, getAppHeight()*0.45);
            rectangle.setFill(Color.rgb(183,100,54));
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(5);
            rectangle.setId("box"+i);
            pane.getChildren().add(rectangle);

            var label = new Label("A");
            var fontsize = 120;
            label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
            label.setTextFill(Color.rgb(116,46,18));
            label.setTranslateX(rectangle.getX()+rectangle.getWidth()/2-fontsize/2.3);
            label.setTranslateY(rectangle.getY()+rectangle.getHeight()/2-fontsize/1.7);
            label.setId("box_label"+i);

            pane.getChildren().add(initUpArrow(rectangle));
            pane.getChildren().add(initDownArrow(rectangle));

            pane.getChildren().add(label);
        }

        return pane;
    }

    private static Label initUpArrow(Rectangle rectangle){
        var label = new Label("^");
        var fontsize = 100;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setTextFill(Color.rgb(126,56,28));
        label.setTranslateX(rectangle.getX()+rectangle.getWidth()/2-fontsize/2.3);
        label.setTranslateY(rectangle.getY()+rectangle.getHeight()*0.025);

        return label;
    }

    private static Label initDownArrow(Rectangle rectangle){
        var label = new Label("^");
        var fontsize = 100;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setTextFill(Color.rgb(126,56,28));
        label.setTranslateX(rectangle.getX()+rectangle.getWidth()/2-fontsize/1.7);
        label.setTranslateY(rectangle.getY()+rectangle.getHeight()*0.55);
        label.setRotate(180);

        return label;
    }
}
