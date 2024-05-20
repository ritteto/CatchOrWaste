package catchorwaste.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static catchorwaste.CatchOrWasteApp.languageMap;

import static catchorwaste.model.SettingsModel.setSelectedLine;
import static catchorwaste.model.SettingsModel.getSelectedLine;
import static catchorwaste.model.SettingsModel.setSelectedColumn;
import static catchorwaste.model.SettingsModel.getSelectedColumn;
import static catchorwaste.model.constants.Constants.START_SCREEN_IMG;
import static catchorwaste.model.constants.Constants.FONT;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;

public class SettingsView {
    public static void initSettingsView(){
        var stackPane = new StackPane();

        stackPane.getChildren().addAll(initBackground());

        for (Rectangle rectangle :initBoxes()) {
            stackPane.getChildren().add(rectangle);
        }

        for (Label label :initLabels()) {
            stackPane.getChildren().add(label);
        }

        stackPane.setId("SettingScreenElements");


        getGameScene().addUINodes(stackPane);
        setSelectedLine(1);
        highlightSelectedLine();
        initChosenLabels();
    }

    public static void highlightSelectedLine(){
        int position = getSelectedLine();
        List<Node> uinodes = getGameScene().getUINodes().stream().toList();
        for (Node node: uinodes) {
            if(node instanceof Rectangle){
                if (((Rectangle) node).getFill()==null){
                    getGameScene().removeUINode(node);
                }
            }
        }
        var unit = 1F/5;
        var stroke = new Rectangle();
        if(position<4){
            stroke = new Rectangle(getAppWidth()*unit,getAppHeight()*unit*position,
                    getAppWidth()*0.6,getAppHeight()*0.15);
        }else{
            stroke = new Rectangle(getAppWidth()*1f/7*3,getAppHeight()*0.8,getAppWidth()*1F/7, getAppHeight()*0.1);
        }
        stroke.setFill(null);
        stroke.setStroke(Color.rgb(54,54,50));
        stroke.setStrokeWidth(5);
        getGameScene().addUINode(stroke);
    }

    private static ArrayList<Rectangle> initBoxes(){
        var rectangles = new ArrayList<Rectangle>();

        for (int i = 1; i < 4; i++) {
            var box = new Rectangle(0,0,
                    getAppWidth()*0.6,getAppHeight()*0.15);
            box.setFill(Color.rgb(183,100,54));
            box.setStroke(Color.rgb(126,56,28));
            StackPane.setAlignment(box,Pos.TOP_CENTER);
            box.setTranslateY(getAppHeight()*1F/5*i);
            rectangles.add(box);
        }

        var submitBtn = new Rectangle(0, 0, getAppWidth()*1F/7, getAppHeight()*0.1);
        submitBtn.setFill(Color.rgb(183,100,54));
        submitBtn.setStroke(Color.rgb(126,56,28));
        StackPane.setAlignment(submitBtn, Pos.BOTTOM_CENTER);
        submitBtn.setTranslateY(-getAppHeight()*0.1);
        rectangles.add(submitBtn);

        return rectangles;
    }

    private static ImageView initBackground(){
        var backgroundImageView = new ImageView(START_SCREEN_IMG);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        return backgroundImageView;
    }

    private static ArrayList<Label> initLabels(){
        var labels = new ArrayList<Label>();
        var title = new Label(languageMap.get("Settings").get(0));
        var submitLabel = new Label(languageMap.get("Settings").get(4));

        title.setAlignment(Pos.CENTER);
        title.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 25));
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        title.setTranslateY(getAppHeight()*0.1-title.getFont().getSize());
        labels.add(title);

        for (int i = 1; i < 4; i++) {
            labels.add(initBoxLabel(new Label(languageMap.get("Settings").get(i)), i));
        }

        String[] languages = {"DE", "EN", "FR"};
        for (int i = 1; i < 4; i++) {
            labels.add(initLangLabel(new Label(languages[i-1]), i));
        }

        for (int i = 1; i < 4; i++) {
            labels.add(initDiffLabel(new Label(String.valueOf(i)), i));
        }

        Label[] tutorialLabels =
                {new Label(languageMap.get("Settings").get(5)), new Label(languageMap.get("Settings").get(6))};
        labels.addAll(Arrays.asList(initTutorialLabels(tutorialLabels)));


        /*
        for (int i = 1; i < 3; i++) {
            labels.add(initTutorialLabel(new Label(languageMap.get("Settings").get(i+4)), i));
        }

         */

        submitLabel.setAlignment(Pos.CENTER);
        submitLabel.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 15));
        StackPane.setAlignment(submitLabel, Pos.TOP_CENTER);
        submitLabel.setTranslateY(getAppHeight()*0.85-submitLabel.getFont().getSize()/2);
        labels.add(submitLabel);

        return labels;
    }

    private static Label initBoxLabel(Label label, int position){
        StackPane.setAlignment(label, Pos.TOP_LEFT);
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 20));
        label.setTranslateX(getAppWidth()*0.225);
        label.setTranslateY(getAppHeight()*0.2*position+label.getFont().getSize()*1.75);
        label.setId("BoxLabel"+ position);
        return label;
    }

    private static Label initLangLabel(Label label, int position){
        StackPane.setAlignment(label, Pos.TOP_LEFT);
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 15));
        label.setTranslateX(getAppWidth()*0.59+getAppWidth()*0.05*position);
        label.setTranslateY(getAppHeight()*0.275-label.getFont().getSize()/2);
        label.setId("LangLabel"+ position);
        return label;
    }

    private static Label initDiffLabel(Label label, int position){
        StackPane.setAlignment(label, Pos.TOP_LEFT);
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 20));
        label.setTranslateX(getAppWidth()*0.63+getAppWidth()*0.04*position);
        label.setTranslateY(getAppHeight()*0.475-label.getFont().getSize()/2);
        label.setId("DiffLabel"+ position);
        return label;
    }

    private static Label[] initTutorialLabels(Label[] labels){
        var fontSize = getAppWidth()*0.02125;
        for (int i = 0; i < labels.length; i++) {
            StackPane.setAlignment(labels[i], Pos.TOP_LEFT);
            labels[i].setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), (int) fontSize));
            labels[i].setId("TutorialLabel"+ (i+1));
            labels[i].setTranslateY(getAppHeight()*0.675-labels[i].getFont().getSize()/2);
        }
        labels[1].setTranslateX(getAppWidth()*0.8-(labels[1].getText().length()+1)*fontSize);
        labels[0].setTranslateX(getAppWidth()*0.8-(labels[1].getText().length()+1)*fontSize-
                fontSize-labels[0].getText().length()*fontSize);
        return labels;
    }

    public static void highlightSelectedColumn(){
        String[][] ids = {
                {"LangLabel1", "LangLabel2", "LangLabel3"},
                {"DiffLabel1", "DiffLabel2", "DiffLabel3"},
                {"TutorialLabel1","TutorialLabel2"}};
        for(Node node: getGameScene().getUINodes()){
            if (node instanceof StackPane && node.getId().equals("SettingScreenElements")){
                for(Node spNode: (((StackPane) node).getChildren())){
                    if(spNode instanceof Label && getSelectedLine() < 4 && spNode.getId()!= null
                            && spNode.getId().equals(ids[getSelectedLine()-1][getSelectedColumn()-1])){
                        ((Label) spNode).setTextFill(Color.rgb(28, 232, 35));
                    } else if (spNode instanceof Label && getSelectedLine() < 4 && spNode.getId()!= null
                    && !spNode.getId().equals(ids[getSelectedLine()-1][getSelectedColumn()-1])
                            && Arrays.asList(ids[getSelectedLine()-1]).contains(spNode.getId())) {
                        ((Label) spNode).setTextFill(Color.BLACK);
                    }
                }

            }
        }
    }

    private static void initChosenLabels(){
        for (int i = 1; i < 4; i++) {
            setSelectedLine(i);
            setSelectedColumn(getSelectedLine(),getSelectedColumn());
            highlightSelectedColumn();
        }
        setSelectedLine(1);
    }
}