package catchorwaste.view.screens;

import catchorwaste.model.screens.NameGeneratorModel;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;


import static catchorwaste.CatchOrWasteApp.imageMap;
import static catchorwaste.CatchOrWasteApp.languageMap;
import static catchorwaste.model.variables.Constants.FONT;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class NameGeneratorView {

    private final NameGeneratorModel nameGeneratorModel;
    public NameGeneratorView(NameGeneratorModel nameGeneratorModel){
        this.nameGeneratorModel = nameGeneratorModel;
    }

    public void initNameGeneratorView(){
        Pane pane = new Pane();


        var backgroundImageView = new ImageView(imageMap.get("background_bad_crop"));
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());

        pane.getChildren().addAll(backgroundImageView, initBoxes(), initSubmit(), initTitleLabel());
        pane.setId("NameGenerator");
        getGameScene().addUINode(pane);

        modifyLane();

    }

    private Node initTitleLabel(){

        Label label= new Label(languageMap.get("NameGenerator").get(0));
        var fontsize = 33;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setTranslateX(getAppWidth()*0.5-label.getText().length()/2.0*fontsize);
        label.setTranslateY(getAppHeight()*0.15-fontsize);
        label.setId("QuestionLabel");

        return label;
    }

    private Pane initSubmit(){
        Pane pane = new Pane();

        var submitBtn = new Rectangle(getAppWidth()*0.225, getAppHeight()*0.75, getAppWidth()*0.55, getAppHeight()*0.1);
        submitBtn.setFill(Color.rgb(183,100,54));
        submitBtn.setStroke(Color.BLACK);
        submitBtn.setStrokeWidth(5);
        submitBtn.setArcWidth(20);
        submitBtn.setArcHeight(20);
        submitBtn.setId("submitBtn");

        pane.getChildren().add(submitBtn);

        var label = new Label(languageMap.get("NameGenerator").get(1));
        var fontsize = 30;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setTextFill(Color.rgb(126,56,28));
        label.setTranslateX(submitBtn.getX()+submitBtn.getWidth()/2.0-fontsize*label.getText().length()/2.0);
        label.setTranslateY(submitBtn.getY()+submitBtn.getHeight()/2.0-fontsize/2.0);
        label.setId("submitLabel");

        pane.setId("submitPane");
        pane.getChildren().add(label);

        return pane;
    }

    private Pane initBoxes(){
        Pane pane = new Pane();

        for (int i = 0; i < 3; i++) {
            var rectangle = new Rectangle(getAppWidth()*0.1+getAppWidth()*0.25*i+getAppWidth()*0.025*i,
                    getAppHeight()*0.25, getAppWidth()*0.25, getAppHeight()*0.45);
            rectangle.setFill(Color.rgb(183,100,54));
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(5);
            rectangle.setId("box"+(i+1));
            pane.getChildren().add(rectangle);

            var label = new Label("A");
            var fontsize = 120;
            label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
            label.setTextFill(Color.rgb(116,46,18));
            label.setTranslateX(rectangle.getX()+rectangle.getWidth()/2-fontsize/2.3);
            label.setTranslateY(rectangle.getY()+rectangle.getHeight()/2-fontsize/1.7);
            label.setId("boxLabel"+(i+1));

            pane.getChildren().addAll(drawUpArrow(rectangle), drawDownArrow(rectangle));
            pane.getChildren().add(drawLine(rectangle, fontsize));
            pane.setId("boxPane");

            pane.getChildren().add(label);
        }

        return pane;
    }


    private Label drawUpArrow(Rectangle rectangle){
        var label = new Label("^");
        var fontsize = 100;
        label.setFont(Font.loadFont(NameGeneratorView.class.getResourceAsStream("/fonts/Jacquard.ttf"), fontsize));
        label.setTextFill(Color.rgb(116,46,18));
        label.setTranslateX(rectangle.getX()+rectangle.getWidth()/2-fontsize/4.2);
        label.setTranslateY(rectangle.getY()-rectangle.getHeight()*0.09);
        label.setId("arrowUp");

        return label;
    }

    private Label drawDownArrow(Rectangle rectangle){
        var label = new Label("^");
        var fontsize = 100;
        label.setFont(Font.loadFont(NameGeneratorView.class.getResourceAsStream("/fonts/Jacquard.ttf"), fontsize));
        label.setTextFill(Color.rgb(116,46,18));
        label.setTranslateX(rectangle.getX()+rectangle.getWidth()/2-fontsize/5.1);
        label.setTranslateY(rectangle.getY()+rectangle.getHeight()*0.65);
        label.setRotate(180);
        label.setId("arrowDown");

        return label;
    }

    private Line drawLine(Rectangle rectangle, int fontsize){
        Line line = new Line(
                rectangle.getX()+rectangle.getWidth()/2.0-fontsize/2.0,
                rectangle.getY()+rectangle.getHeight()*0.75,
                rectangle.getX()+rectangle.getWidth()/2.0+fontsize/2.0,
                rectangle.getY()+rectangle.getHeight()*0.75);

        line.setStrokeWidth(10);
        line.setStroke(Color.rgb(126,56,28));
        line.setId("line");

        return line;
    }

    public void modifyLane(){
        Pane boxPane = null;
        var position = nameGeneratorModel.getActiveLane();
        Rectangle[] rectangles = new Rectangle[4];
        String[] rectangleNames = {"#box1", "#box2", "#box3", "#submitBtn"};
        Label[] labels = new Label[4];
        String[] labelNames = {"#boxLabel1", "#boxLabel2", "#boxLabel3", "#submitLabel"};

        for (Node node : getGameScene().getUINodes()){

            boxPane = (Pane) node.lookup("#NameGenerator").lookup("#boxPane");
            for (int i = 0; i < rectangles.length-1; i++) {
                rectangles[i] = (Rectangle) node.lookup("#NameGenerator")
                        .lookup("#boxPane").lookup(rectangleNames[i]);
            }
            rectangles[rectangles.length-1] = (Rectangle) node.lookup("#NameGenerator")
                    .lookup("#submitPane").lookup(rectangleNames[rectangles.length-1]);
            for (int i = 0; i < labels.length-1; i++) {
                labels[i] = (Label) node.lookup("#NameGenerator").lookup("#boxPane").lookup(labelNames[i]);
            }
            labels[labels.length-1] = (Label) node.lookup("#NameGenerator")
                    .lookup("#submitPane").lookup(labelNames[labels.length-1]);

        }

        assert boxPane != null;
        boxPane.getChildren().removeIf(node ->
                node.getId() != null && (node.getId().equals("arrowUp") || node.getId().equals("arrowDown") ||
                        node.getId().equals("line"))
        );

        if (position<4) {
            rectangles[3].setStrokeWidth(5);
            labels[labels.length-1].setTextFill(Color.rgb(126,56,28));
            for (int i = 0; i < rectangles.length-1; i++) {
                if(i==position-1){
                    boxPane.getChildren().addAll(drawUpArrow(rectangles[i]), drawDownArrow(rectangles[i]));
                    rectangles[i].setStrokeWidth(9);
                }else{
                    boxPane.getChildren().add(drawLine(rectangles[i], (int) labels[i].getFont().getSize()));
                    rectangles[i].setStrokeWidth(5);
                }
            }
        } else {
            rectangles[position-1].setStrokeWidth(7);
            labels[labels.length-1].setTextFill(Color.BLACK);
            for (int i = 0; i < rectangles.length-1; i++) {
                boxPane.getChildren().add(drawLine(rectangles[i], (int) labels[i].getFont().getSize()));
                rectangles[i].setStrokeWidth(5);
            }
        }
    }

    public void highlightArrowUp(){
        Label arrow = null;
        for (Node node : getGameScene().getUINodes()){
            arrow = (Label) node.lookup("#NameGenerator").lookup("#boxPane")
                    .lookup("#arrowUp");
        }
        assert arrow != null;
        arrow.setTextFill(Color.BLACK);
        Label finalArrow = arrow;
        FXGL.getGameTimer().runOnceAfter(() -> finalArrow.setTextFill(Color.rgb(126,56,28)), Duration.millis(150));
    }

    public void highlightArrowDown(){
        Label arrow = null;
        for (Node node : getGameScene().getUINodes()){
            arrow = (Label) node.lookup("#NameGenerator").lookup("#boxPane")
                    .lookup("#arrowDown");
        }
        assert arrow != null;
        arrow.setTextFill(Color.BLACK);
        Label finalArrow = arrow;
        FXGL.getGameTimer().runOnceAfter(() -> finalArrow.setTextFill(Color.rgb(126,56,28)), Duration.millis(150));
    }

    public void changeLetterView(){
        String[] labelNames = {"#boxLabel1", "#boxLabel2", "#boxLabel3"};
        var lane = nameGeneratorModel.getActiveLane()-1;
        Label label = null;
        for (Node node : getGameScene().getUINodes()){
            label = (Label) node.lookup("#NameGenerator").lookup("#boxPane").lookup(labelNames[lane]);
        }
        assert label != null;
        label.setText(String.valueOf((char) nameGeneratorModel.getLetter()));
    }
}
