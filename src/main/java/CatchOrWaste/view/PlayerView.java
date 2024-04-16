package CatchOrWaste.view;

import CatchOrWaste.model.components.CargoComponent;
import CatchOrWaste.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import javafx.scene.image.ImageView;

import static CatchOrWaste.CatchOrWasteApp.imageMap;
import static CatchOrWaste.model.constants.Constants.STREET_RIGHT_END;
import static CatchOrWaste.model.constants.Constants.STREET_LEFT_END;
import static CatchOrWaste.model.constants.Constants.STREET_HEIGHT;
import static CatchOrWaste.model.FallingObjectModel.amount_FO;
import static CatchOrWaste.view.CartView.spawnCart;
public class PlayerView {

    public static void isAtStreetEnd(GameWorld gameWorld) {
        for (Entity player : gameWorld.getEntitiesByType(EntityType.PLAYER)) {
            if (player.getX() == STREET_RIGHT_END) {
                changePlayerImage("Down_Right", gameWorld);
                if(player.getComponent(CargoComponent.class).isFull()){
                    spawnCart(gameWorld,STREET_RIGHT_END+50, STREET_HEIGHT,
                            player.getComponent(CargoComponent.class).getCatchedEntity());
                    player.getComponent(CargoComponent.class).getCatchedEntity().removeFromWorld();
                    player.getComponent(CargoComponent.class).setCatchedEntity(null);
                    amount_FO--;
                }
            }
            if (player.getX() == STREET_LEFT_END) {
                changePlayerImage("Down_Left", gameWorld);
                if(player.getComponent(CargoComponent.class).isFull()){
                    spawnCart(gameWorld, STREET_LEFT_END-30, STREET_HEIGHT,
                            player.getComponent(CargoComponent.class).getCatchedEntity());
                    player.getComponent(CargoComponent.class).getCatchedEntity().removeFromWorld();
                    player.getComponent(CargoComponent.class).setCatchedEntity(null);
                    amount_FO--;
                }
            }
        }
    }

    public static void changePlayerImage(String s, GameWorld gameWorld){
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

