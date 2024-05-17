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
import java.util.List;

import static catchorwaste.CatchOrWasteApp.textMap;
import static catchorwaste.model.SettingsModel.getSelectedColumn;
import static catchorwaste.model.SettingsModel.getSelectedLine;
import static catchorwaste.model.constants.Constants.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class SettingsView {
    public static void initSelectionScreenView(){
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
        changeSelectedLine();

        highLightChosenLabel();
    }

    public static void changeSelectedLine(){
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
        highLightChosenLabel();
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
        var title = new Label(textMap.get("Settings").get(0));
        var submitLabel = new Label(textMap.get("Settings").get(4));

        title.setAlignment(Pos.CENTER);
        title.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 25));
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        title.setTranslateY(getAppHeight()*0.1-title.getFont().getSize());
        labels.add(title);

        for (int i = 1; i < 4; i++) {
            labels.add(initBoxLabel(new Label(textMap.get("Settings").get(i)), i));
        }

        String[] languages = {"DE", "EN", "FR"};
        for (int i = 1; i < 4; i++) {
            labels.add(initLangLabel(new Label(languages[i-1]), i));
        }

        for (int i = 1; i < 4; i++) {
            labels.add(initDiffLabel(new Label(String.valueOf(i)), i));
        }

        String[] tutorials = {"YES", "NO"};
        for (int i = 1; i < 3; i++) {
            labels.add(initTutorialLabel(new Label(tutorials[i-1]), i));
        }

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

    private static Label initTutorialLabel(Label label, int position){
        StackPane.setAlignment(label, Pos.TOP_LEFT);
        label.setFont(Font.loadFont(EndScreenView.class.getResourceAsStream(FONT), 17));
        label.setTranslateX(getAppWidth()*0.57+getAppWidth()*0.08*position);
        label.setTranslateY(getAppHeight()*0.675-label.getFont().getSize()/2);
        label.setId("TutorialLabel"+ position);
        return label;
    }

    private static void highLightChosenLabel(){
        String[][] ids = {{"LangLabel1", "LangLabel2", "LangLabel3"},
                {"DiffLabel1", "DiffLabel2", "DiffLabel3"},
                {"TutorialLabel1","TutorialLabel2"}};
        for(Node node: getGameScene().getUINodes()){
            if (node instanceof StackPane && node.getId().equals("SettingScreenElements")){
                for(Node spNode: (((StackPane) node).getChildren())){
                    if(spNode instanceof Label){
                        System.out.println(spNode.getId());
                    }
                }
                /*
            }
                for(Node spNode: ((StackPane) node).getChildren()){
                    if(spNode instanceof Label){
                        if(getSelectedLine()<3){
                            System.out.println(spNode.lookup(ids[getSelectedLine()-1][getSelectedColumn()-1]).getId());
                        } else if (getSelectedLine()<4) {
                            System.out.println(spNode.lookup(ids[getSelectedLine()-1][(getSelectedColumn()+1)%2]).getId());
                        }
                    }
                    /*
                    if(getSelectedLine()<3){
                        System.out.println(spNode.lookup(ids[getSelectedLine()-1][getSelectedColumn()-1]).getId());
                    } else if (getSelectedLine()<4) {
                        System.out.println(spNode.lookup(ids[getSelectedLine()-1][(getSelectedColumn()+1)%2]).getId());
                    }
                    /*
                    if(spNode instanceof Label && spNode.getId()!= null){
                        if(getSelectedLine()<3){

                            System.out.println(ids[getSelectedLine()-1][getSelectedColumn()-1]);
                        }else if(getSelectedLine()<4){
                            System.out.println(ids[getSelectedLine()-1][(getSelectedColumn()+1)%2]);
                        }
                    }

                     */
                //}
            }
        }
    }
}
