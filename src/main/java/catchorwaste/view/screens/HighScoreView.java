package catchorwaste.view.screens;

import catchorwaste.model.screens.HighScoreModel;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static catchorwaste.CatchOrWasteApp.imageMap;
import static catchorwaste.CatchOrWasteApp.languageMap;
import static catchorwaste.model.variables.Constants.FONT;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class HighScoreView {

    private final HighScoreModel highScoreModel;
    public HighScoreView(HighScoreModel highScoreModel){
        this.highScoreModel = highScoreModel;
    }

    public void initHighScoreView(){
        Pane pane = new Pane();

        pane.getChildren().addAll(initBackground(),initBigBox(),initTitelLabel(),initList(), button());
        getGameScene().addUINode(pane);

    }

    public Node initBackground(){

        ImageView backgroundImageView = new ImageView(imageMap.get("endscreen"));
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());

        return backgroundImageView;
    }

    private Node initBigBox(){
        var bigBox = new Rectangle(getAppWidth()*0.275,getAppHeight()*0.075,
                getAppWidth()*0.45, getAppHeight()*0.7);
        bigBox.setFill(Color.rgb(203,120,74));
        bigBox.setStroke(Color.rgb(126,56,28));
        bigBox.setStrokeWidth(5);

        return bigBox;

    }

    public Node initList(){
        Pane pane = new Pane();

        var fullList = highScoreModel.getFullList();
        var sortedFullList = fullList;
        sortedFullList.sort(Comparator.comparingInt((String[] a) -> Integer.parseInt(a[1])).reversed());
        var list = highScoreModel.getTopTen();
        var lastEntry = highScoreModel.getLastEntry();
        var lastNumber = 10;

        if(lastEntry != null && !list.contains(lastEntry)){
            fullList.sort(Comparator.comparingInt((String[] a) -> Integer.parseInt(a[1])).reversed());
            lastNumber = fullList.indexOf(lastEntry);
            list = list.stream().limit(9).collect(Collectors.toCollection(ArrayList::new));
            list.add(lastEntry);
        }

        for (int i = 0; i < 10; i++) {
            var rectangle = new Rectangle(getAppWidth()*0.3, getAppHeight()*0.3+getAppHeight()*0.5/10.0*i,
                    getAppWidth()*0.4, getAppHeight()*0.5/10.0);

            rectangle.setFill(null);
            pane.getChildren().add(rectangle);

            String name;
            String score;

            if(i < list.size() && list.get(i) != null){
                score = list.get(i)[1];
                name = list.get(i)[2];
            } else{
                name = "-";
                score = "-";
            }
            var fontsize = 20;

            Label numberLabel = null;
            if(i<9){
                numberLabel = new Label(String.valueOf(i+1));
            }else{
                numberLabel = new Label(String.valueOf(lastNumber));
            }
            numberLabel.setId("numberLabel"+(i+1));
            numberLabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
            numberLabel.setTranslateX(rectangle.getX());
            numberLabel.setTranslateY(rectangle.getY()-rectangle.getHeight()/2.0-fontsize/2.0);

            Label scoreLabel = new Label(score);
            scoreLabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
            scoreLabel.setId("scoreLabel"+(i+1));
            scoreLabel.setTranslateX(rectangle.getX()+rectangle.getWidth()-scoreLabel.getText().length()*fontsize);
            scoreLabel.setTranslateY(rectangle.getY()-rectangle.getHeight()/2.0-fontsize/2.0);

            Label nameLabel = new Label(name);
            nameLabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
            nameLabel.setId("nameLabel"+(i+1));

            var endOfNumber = rectangle.getX()+String.valueOf(fullList.size()).length()*fontsize;
            var startOfScore = rectangle.getX()+rectangle.getWidth()-sortedFullList.get(0)[1].length()*fontsize;

            nameLabel.setTranslateX(endOfNumber + (startOfScore - endOfNumber)/2.0
                    -(nameLabel.getText().length()/2.0*fontsize));

            nameLabel.setTranslateY(rectangle.getY()-rectangle.getHeight()/2.0-fontsize/2.0);

            pane.getChildren().addAll(numberLabel,nameLabel,scoreLabel);

        }

        int position;
        if(list.contains(highScoreModel.getLastEntry())){
            position = list.indexOf(lastEntry)+1;
        }else{
            position = 10;

        }
        ((Label) pane.lookup("#numberLabel"+position)).setTextFill(Color.rgb(28, 232, 35));
        ((Label) pane.lookup("#nameLabel"+position)).setTextFill(Color.rgb(28, 232, 35));
        ((Label) pane.lookup("#scoreLabel"+position)).setTextFill(Color.rgb(28, 232, 35));


        return pane;
    }

    private Pane initTitelLabel(){
        Pane pane = new Pane();
        Label label = new Label("Highscore");
        var fontsize = 30;
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), fontsize));
        label.setTranslateX(getAppWidth()*0.5-label.getText().length()/2.0*fontsize);
        label.setTranslateY(getAppHeight()*0.13);

        Line line = new Line(label.getTranslateX(), label.getTranslateY()+fontsize*1.2,
                label.getTranslateX()+label.getText().length()*fontsize,label.getTranslateY()+fontsize*1.2);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);

        pane.getChildren().addAll(label, line);
        return pane;
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
