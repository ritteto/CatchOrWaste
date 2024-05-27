package catchorwaste.controller.entities;

import catchorwaste.model.components.CargoComponent;
import catchorwaste.model.components.IsCatchedComponent;
import catchorwaste.model.components.PlayerDirectionComponent;
import catchorwaste.model.enums.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;

import static catchorwaste.model.variables.Constants.STREET_RIGHT_END;
import static catchorwaste.model.variables.Constants.STREET_LEFT_END;
import static catchorwaste.model.entities.PlayerModel.playerSpeed;
import static catchorwaste.view.entities.PlayerView.changePlayerImage;

public class PlayerController {

    public static void catchObject(){

        FXGL.onCollision(EntityType.PLAYER, EntityType.OBJECT, (player,object)->{
            if(!player.getComponent(CargoComponent.class).isFull()){
                player.getComponent(CargoComponent.class).setCatchedEntity(object);
                object.getComponent(IsCatchedComponent.class).setCatched(true);
                object.removeComponent(ProjectileComponent.class);
            }
        });
    }

    public static void boundaries(GameWorld gameWorld){
        //Boundary right
        for (Entity player : gameWorld.getEntitiesByType(EntityType.PLAYER)) {
            if(player.getX() > STREET_RIGHT_END){
                player.setX(STREET_RIGHT_END);

                //Boundary left
            }else if(player.getX() < STREET_LEFT_END){
                player.setX(STREET_LEFT_END);
            }
        }
    }

    public static void movePlayer(boolean direction, GameWorld gameWorld){
        for(Entity player: gameWorld.getEntitiesByType(EntityType.PLAYER)) {
            if(direction){
                changePlayerImage("Right",gameWorld);
                player.getComponent(PlayerDirectionComponent.class).setDirection(true);
                player.translateX(playerSpeed);
            }else{
                player.getComponent(PlayerDirectionComponent.class).setDirection(false);
                changePlayerImage("Left",gameWorld);
                player.translateX(-playerSpeed);
            }
        }
    }
}
