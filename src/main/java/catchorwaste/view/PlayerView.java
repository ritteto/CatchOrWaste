package catchorwaste.view;

import catchorwaste.model.components.CargoComponent;
import catchorwaste.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static catchorwaste.CatchOrWasteApp.imageMap;
import static catchorwaste.model.constants.Constants.*;
import static catchorwaste.view.CartView.spawnCart;
public class PlayerView {

    public static void isAtStreetEnd(GameWorld gameWorld) {
        for (Entity player : gameWorld.getEntitiesByType(EntityType.PLAYER)) {
            if (player.getX() == STREET_RIGHT_END) {
                changePlayerImage("Down_Right", gameWorld);
                if(player.getComponent(CargoComponent.class).isFull()){
                    spawnCart(gameWorld,STREET_RIGHT_END+50, CART_HEIGHT_AT_STREET,
                            player.getComponent(CargoComponent.class).getCatchedEntity());
                    player.getComponent(CargoComponent.class).getCatchedEntity().removeFromWorld();
                    player.getComponent(CargoComponent.class).setCatchedEntity(null);
                }
            }
            if (player.getX() == STREET_LEFT_END) {
                changePlayerImage("Down_Left", gameWorld);
                if(player.getComponent(CargoComponent.class).isFull()){
                    spawnCart(gameWorld, STREET_LEFT_END-player.getBoundingBoxComponent().getWidth()*0.2, CART_HEIGHT_AT_STREET,
                            player.getComponent(CargoComponent.class).getCatchedEntity());
                    player.getComponent(CargoComponent.class).getCatchedEntity().removeFromWorld();
                    player.getComponent(CargoComponent.class).setCatchedEntity(null);
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

