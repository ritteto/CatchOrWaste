package catchorwaste.view.entities;

import catchorwaste.model.components.CargoComponent;
import catchorwaste.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import javafx.scene.image.ImageView;


import static catchorwaste.CatchOrWasteApp.imageMap;
import static catchorwaste.controller.entities.CartController.cartDistance;
import static catchorwaste.model.variables.Constants.STREET_RIGHT_END;
import static catchorwaste.model.variables.Constants.STREET_LEFT_END;
import static catchorwaste.model.variables.Constants.CART_HEIGHT_AT_STREET;
import static catchorwaste.view.entities.CartView.spawnCart;
public class PlayerView {

    public static void isAtStreetEnd(GameWorld gameWorld) {
        for (Entity player : gameWorld.getEntitiesByType(EntityType.PLAYER)) {
            if (player.getX() == STREET_RIGHT_END) {
                if(player.getComponent(CargoComponent.class).isFull() && cartDistance(false)){
                    changePlayerImage("Down_Right", gameWorld);
                    spawnCart(gameWorld,STREET_RIGHT_END+50, CART_HEIGHT_AT_STREET,
                            player.getComponent(CargoComponent.class).getCatchedEntity());
                    player.getComponent(CargoComponent.class).getCatchedEntity().removeFromWorld();
                    player.getComponent(CargoComponent.class).setCatchedEntity(null);
                }else if(!player.getComponent(CargoComponent.class).isFull()){
                    changePlayerImage("Down_Right", gameWorld);
                }else{
                    changePlayerImage("", gameWorld);
                }
            }
            if (player.getX() == STREET_LEFT_END) {
                if(player.getComponent(CargoComponent.class).isFull() && cartDistance(true)){
                    changePlayerImage("Down_Left", gameWorld);
                    spawnCart(gameWorld,
                            STREET_LEFT_END-player.getBoundingBoxComponent().getWidth()*0.2, CART_HEIGHT_AT_STREET,
                            player.getComponent(CargoComponent.class).getCatchedEntity());
                    player.getComponent(CargoComponent.class).getCatchedEntity().removeFromWorld();
                    player.getComponent(CargoComponent.class).setCatchedEntity(null);
                }else if(!player.getComponent(CargoComponent.class).isFull()){
                    changePlayerImage("Down_Left", gameWorld);
                }else{
                    changePlayerImage("Left", gameWorld);
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

