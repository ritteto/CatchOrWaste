package catchorwaste.view.screens;

import catchorwaste.model.screens.NewPlayerModel;
import javafx.scene.Node;
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

public class NewPlayerView {
    NewPlayerModel newPlayerModel;

    public NewPlayerView(NewPlayerModel newPlayerModel){
        this.newPlayerModel = newPlayerModel;
    }
    public void initNewPlayerScreenView(){
        Pane pane = new Pane();
        pane.setId("NewPlayerPane");

        var backgroundImageView = new ImageView(imageMap.get("background_bad_crop"));
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());

        pane.getChildren().addAll(backgroundImageView, initLabel(), initBoxes());

        getGameScene().addUINode(pane);

        highlightChosenLabel();
    }

    private Pane initLabel(){
        Pane pane = new Pane();

        Label label= new Label(languageMap.get("NewPlayer").get(0));
        var fontsize = 35;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setTranslateX(getAppWidth()*0.5-label.getText().length()/2.0*fontsize);
        label.setTranslateY(getAppHeight()*0.4-fontsize);
        label.setId("QuestionLabel");

        pane.getChildren().add(label);

        return pane;
    }

    private Pane initBoxes(){
        Pane pane = new Pane();
        var labelTitles = new String[]{languageMap.get("NewPlayer").get(1), languageMap.get("NewPlayer").get(2)};

        Rectangle rectangle = null;
        for (int i=0; i<2; i++) {
            rectangle = new Rectangle(getAppWidth()*0.275+getAppWidth()*0.2*i+getAppWidth()*0.05*i,
                    getAppHeight()*0.45, getAppWidth()*0.2, getAppHeight()*0.15);
            rectangle.setFill(Color.rgb(219,131,81));
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(4);
            rectangle.setId("Option"+(i+1));


            Label label = new Label(labelTitles[i]);
            var fontsize = 30;
            label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
            label.setTranslateX(rectangle.getX()+rectangle.getWidth()/2.0-label.getText().length()/2.0*fontsize);
            label.setTranslateY(rectangle.getY()+rectangle.getHeight()/2.0-fontsize/2.0);
            label.setId("OptionLabel"+(i+1));

            pane.getChildren().addAll(rectangle,label);
        }
        pane.setId("boxPane");

        return pane;
    }

    public void highlightChosenLabel(){
        var chosenLabel = newPlayerModel.getChosenLabel();
        Pane newPlayerPane = null;
        Rectangle option1 = null;
        Rectangle option2 = null;

        for(Node UINode: getGameScene().getUINodes()){
            newPlayerPane = (Pane) UINode.lookup("#NewPlayerPane");
            assert newPlayerPane != null;
            option1 = (Rectangle) newPlayerPane.lookup("#boxPane").lookup("#Option1");
            option2 = (Rectangle) newPlayerPane.lookup("#boxPane").lookup("#Option2");

        }


        assert option1 !=null && option2 != null;
        if(chosenLabel == 1){
            option1.setStrokeWidth(7);
            option2.setStrokeWidth(4);

        }else{
            option1.setStrokeWidth(4);
            option2.setStrokeWidth(7);
        }
    }

}
