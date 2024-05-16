package catchorwaste.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;

import static catchorwaste.CatchOrWasteApp.textMap;
import static catchorwaste.model.SelectionScreenModel.getSelected;
import static catchorwaste.model.constants.Constants.FONT_SIZE;
import static catchorwaste.model.constants.Constants.START_SCREEN_IMG;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class SelectionScreenView {
    public static void initSelectionScreenView(){
        var stackPane = new StackPane();

        var backgroundImageView = new ImageView(START_SCREEN_IMG);
        backgroundImageView.setFitWidth(getAppWidth());
        backgroundImageView.setFitHeight(getAppHeight());
        stackPane.getChildren().setAll(backgroundImageView);
        getGameScene().addUINodes(stackPane);
        changeSelected();
    }

    public static void changeSelected(){
        int position = getSelected();
        int x=0;
        int y=0;
        switch (position){
            case 1:
                x=1;
                y=1;
                break;
            case 2:
                x=5;
                y=1;
                break;
            case 3:
                x=1;
                y=5;
                break;
            case 4:
                x=5;
                y=5;
                break;
        }
        List<Node> uinodes = getGameScene().getUINodes().stream().toList();
        for (Node node: uinodes) {
            if(node instanceof Rectangle){
                getGameScene().removeUINode(node);
            }
        }
        var unit = 1F/9;
        var stroke = new Rectangle(getAppWidth()*unit*x,getAppHeight()*unit*y,
                getAppWidth()*3*unit,getAppHeight()*3*unit);
        stroke.setFill(null);
        stroke.setStroke(Color.RED);
        getGameScene().addUINode(stroke);
    }
}
