package catchorwaste.view.screens;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static catchorwaste.CatchOrWasteApp.imageMap;
import static catchorwaste.CatchOrWasteApp.languageMap;
import static catchorwaste.model.variables.Constants.FONT;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;


public class TutorialView {

    public void initTutorialView(){

        ImageView backgroundImageView = new ImageView(imageMap.get("tutorial-test-1"));
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        getGameScene().addUINodes(backgroundImageView, initText());
    }

    private Pane initText(){
        Pane pane = new Pane();

        Rectangle rectangle = new Rectangle(getAppWidth()*0.075, getAppHeight()*0.15,
                getAppWidth()*0.85, getAppHeight()*0.175);
        rectangle.setFill(Color.rgb(179,76,28));
        pane.getChildren().add(rectangle);

        Label label = new Label(languageMap.get("Tutorial").get(0));
        var fontsize = 17;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setPrefWidth(rectangle.getWidth()*0.9);
        label.setWrapText(true);
        label.setTextFill(Color.BLACK);
        label.setTranslateX(rectangle.getX()+rectangle.getWidth()*0.05);
        label.setTranslateY(rectangle.getY()+rectangle.getHeight()*0.1);
        label.setId("text1");
        pane.getChildren().add(label);

        Rectangle sideRectangle = new Rectangle(getAppHeight()*0.88, getAppHeight()*0.325,
                getAppWidth()*0.25, getAppHeight()*0.5);
        sideRectangle.setFill(Color.rgb(179,76,28));
        pane.getChildren().add(sideRectangle);

        for (int i = 0; i < 3; i++) {
            Label label1 = new Label(languageMap.get("Tutorial").get(i+1));
            label1.setFont(Font.loadFont(this.getClass().getResourceAsStream("/fonts/joystix.ttf"),
                    fontsize));
            label1.setTextFill(Color.BLACK);
            label1.setTranslateX(getAppWidth()*0.675);
            label1.setTranslateY(getAppWidth()*0.29+getAppWidth()*0.125*i);
            pane.getChildren().add(label1);
        }

        return pane;

    }
}
