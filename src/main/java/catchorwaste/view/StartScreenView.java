package catchorwaste.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import static catchorwaste.CatchOrWasteApp.imageMap;

import static catchorwaste.model.NameGeneratorModel.getPlayerName;
import static catchorwaste.model.constants.Constants.START_SCREEN_IMG;
import static catchorwaste.model.constants.Constants.TUTORIAL_SCREEN_IMG;
import static catchorwaste.model.constants.Constants.FONT;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class StartScreenView extends StackPane {

    private static Pane pane;
    private static ImageView backgroundImageView;


    public static void initStartScreenView() {
        pane = new Pane();
        pane.setPrefWidth(getAppWidth());
        pane.setPrefHeight(getAppHeight());

        var titlePane = initTitle();

        var boxPane = initBoxes();


        backgroundImageView = new ImageView(START_SCREEN_IMG);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        pane.getChildren().setAll(backgroundImageView, boxPane, titlePane);
        pane.setId("startScreenPane");
        getGameScene().addUINodes(pane);

        choseOption(1);
    }

    public static Pane initBoxes(){
        var gamePane = new Pane();

        String[] imageNames = {"gamescreen","settingsscreen"};
        for (int i=0; i<2; i++) {
            var imageView = new ImageView(imageMap.get(imageNames[i]));
            double unitX = getAppWidth()/12.0;
            imageView.setFitWidth(unitX*4);
            imageView.setFitHeight(getAppHeight()/3.0);
            imageView.setX(unitX*1.5+i*unitX*4+i*unitX*1);
            imageView.setY(getAppHeight()/2.0-(imageView.getFitHeight()/2));

            imageView.setId(imageNames[i]+"_image");

            var outline = new Rectangle(imageView.getX(), imageView.getY(),
                    imageView.getFitWidth(), imageView.getFitHeight());
            outline.setFill(null);
            outline.setStroke(Color.BLACK);
            outline.setStrokeWidth(4);

            outline.setId(imageNames[i]+"_outline");

            gamePane.getChildren().addAll(imageView, outline);
            gamePane.setId("optionBoxes");
        }

        return gamePane;
    }

    public static void choseOption(int position){
        Pane optionBoxes = null;
        ImageView settingsscreenImage = null;
        ImageView gamescreenImage = null;
        for (Node UINode: getGameScene().getUINodes()) {
            optionBoxes = (Pane) UINode.lookup("#optionBoxes");
            settingsscreenImage = (ImageView) UINode.lookup("#optionBoxes").lookup("#settingsscreen_image");
            gamescreenImage = (ImageView) UINode.lookup("#optionBoxes").lookup("#gamescreen_image");
        }

        assert optionBoxes != null;
        optionBoxes.getChildren().removeIf(node ->
                node.getId() != null && (node.getId().equals("cover") || node.getId().equals("cover_label"))
        );

        if(position == 1){
            addCoverAndLabel(optionBoxes, gamescreenImage, position);
        }else{
            addCoverAndLabel(optionBoxes, settingsscreenImage, position);
        }
    }

    public static void addCoverAndLabel(Pane optionBoxes, ImageView imageView, int position){
        String[] imageNames = {"gamescreen","settingsscreen"};
        if (imageView != null) {
            var cover = new Rectangle(imageView.getX(), imageView.getY(),
                    imageView.getFitWidth(), imageView.getFitHeight());
            cover.setFill(Color.BLACK);
            cover.setOpacity(0.6);
            cover.setId("cover");

            var name = imageNames[position-1].replace("screen", "");
            Label label = new Label(name.substring(0,1).toUpperCase()+name.substring(1));
            label.setAlignment(Pos.TOP_LEFT);
            label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 27));
            label.setTranslateX(imageView.getX()+imageView.getFitWidth()/2
                    -label.getFont().getSize()*name.length()/2);
            label.setTranslateY(imageView.getY()+imageView.getFitHeight()/2
                    -label.getFont().getSize()/2);
            label.setTextFill(Color.rgb(203,120,74));
            label.setId("cover_label");

            optionBoxes.getChildren().addAll(cover, label);

        }
    }

    public static Pane initTitle(){
        Pane titlePane = new Pane();
        Label titlelabel = new Label("Hello"+" "+getPlayerName());
        titlelabel.setAlignment(Pos.TOP_LEFT);
        titlelabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 35));
        titlelabel.setTranslateX(getAppWidth()*0.5-
                titlelabel.getText().length()/2.0*titlelabel.getFont().getSize());
        titlelabel.setTranslateY(getAppHeight()*0.2);
        titlelabel.setId("title_label");

        titlePane.getChildren().add(titlelabel);

        return titlePane;
    }

    public void getTutorial(){
        Image TutorialImage = new Image(TUTORIAL_SCREEN_IMG);
        getTutorial();
        ImageView tutorialImageView = new ImageView(TutorialImage);
        tutorialImageView.setFitWidth(getAppWidth());
        tutorialImageView.setFitHeight(getAppHeight());
        getChildren().add(tutorialImageView);
    }

}
