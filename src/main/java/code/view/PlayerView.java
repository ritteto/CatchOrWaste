package code.view;

import code.model.components.CargoComponent;
import code.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.texture.Texture;

import static code.model.Constants.Constants.*;
import static code.model.FallingObjectModel.amount_FO;
import static code.view.CartView.spawnCart;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAssetLoader;

public class PlayerView {

    public static void isAtStreetEnd(GameWorld gameWorld) {
        for (Entity player : gameWorld.getEntitiesByType(EntityType.PLAYER)) {
            if (player.getX() == STREET_RIGHT_END) {
                changePlayerImage("Down_Right", gameWorld);
                if(player.getComponent(CargoComponent.class).isFull()){
                    spawnCart(gameWorld,STREET_RIGHT_END, STREET_HEIGHT, player.getComponent(CargoComponent.class).getCatchedEntity());
                    player.getComponent(CargoComponent.class).getCatchedEntity().removeFromWorld();
                    player.getComponent(CargoComponent.class).setCatchedEntity(null);
                    amount_FO--;
                }
            }
            if (player.getX() == STREET_LEFT_END) {
                changePlayerImage("Down_Left", gameWorld);
                if(player.getComponent(CargoComponent.class).isFull()){
                    spawnCart(gameWorld,STREET_LEFT_END, STREET_HEIGHT, player.getComponent(CargoComponent.class).getCatchedEntity());
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
                case "Left" -> "player/wegwerfpolizist_l_resized.png";
                case "Down_Right" -> "player/wegwerfpolizist_d_r_resized.png";
                case "Down_Left" -> "player/wegwerfpolizist_d_l_resized.png";
                default -> "player/wegwerfpolizist_r_resized.png";
            };
            player.getViewComponent().clearChildren();
            player.getViewComponent().addChild(new Texture(getAssetLoader().loadImage(url)));
        }
    }

}

