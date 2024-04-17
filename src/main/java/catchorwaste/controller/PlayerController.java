package catchorwaste.controller;

import catchorwaste.model.components.CargoComponent;
import catchorwaste.model.components.IsCatchedComponent;
import catchorwaste.model.components.PlayerDirectionComponent;
import catchorwaste.model.enums.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;

import static catchorwaste.model.constants.Constants.PLAYERSIZE;
import static catchorwaste.model.constants.Constants.STREET_RIGHT_END;
import static catchorwaste.model.constants.Constants.STREET_LEFT_END;
import static catchorwaste.model.FallingObjectModel.amount_FO;
import static catchorwaste.model.PlayerModel.playerSpeed;
import static catchorwaste.view.PlayerView.changePlayerImage;

public class PlayerController {

    public static void catchObject(GameWorld gameWorld){

        FXGL.onCollision(EntityType.PLAYER, EntityType.OBJECT, (player,object)->{
            if(!player.getComponent(CargoComponent.class).isFull()){
                System.out.println(player.getComponent(BoundingBoxComponent.class));
                player.getComponent(CargoComponent.class).setCatchedEntity(object);
                object.getComponent(IsCatchedComponent.class).setCatched(true);
                object.removeComponent(ProjectileComponent.class);
            }
        });
        /*
        for (Entity player : gameWorld.getEntitiesByType(EntityType.PLAYER)) {
            if(!player.getComponent(CargoComponent.class).isFull()){
                for (Entity fallingObject: gameWorld.getEntitiesByType(EntityType.OBJECT)) {
                    if(!fallingObject.getComponent(IsCatchedComponent.class).isCatched()
                            && fallingObject.getY() + 60 > player.getY()
                            && fallingObject.getY() < player.getY() + PLAYERSIZE
                            && fallingObject.getX()-30 > player.getX()
                            && fallingObject.getX()-30 < player.getX() + PLAYERSIZE){
                        fallingObject.getComponent(IsCatchedComponent.class).setCatched(true);
                        fallingObject.removeComponent(ProjectileComponent.class);
                        amount_FO++;
                    }

                }
            }
        }

         */
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
