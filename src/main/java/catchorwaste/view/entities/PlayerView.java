package catchorwaste.view.entities;

import catchorwaste.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import javafx.scene.image.ImageView;

import static catchorwaste.CatchOrWasteApp.imageMap;
public class PlayerView {

    public void changePlayerImage(String s, GameWorld gameWorld){
        for (Entity player: gameWorld.getEntitiesByType(EntityType.PLAYER)) {
            String url = switch (s) {
                case "Left" -> "wegwerfpolizist_l_resized";
                case "Down_Right" -> "wegwerfpolizist_d_r_resized";
                case "Down_Left" -> "wegwerfpolizist_d_l_resized";
                default -> "wegwerfpolizist_r_resized";
            };
            player.getViewComponent().clearChildren();
            player.getViewComponent().addChild(new ImageView(imageMap.get(url)));
        }
    }

}

