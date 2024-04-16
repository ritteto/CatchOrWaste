package catchOrWaste.controller;

import catchOrWaste.model.components.CargoComponent;
import catchOrWaste.model.components.IsCatchedComponent;
import catchOrWaste.model.components.PlayerDirectionComponent;
import catchOrWaste.model.enums.EntityType;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import static catchOrWaste.model.constants.Constants.PLAYERSIZE;
import static catchOrWaste.model.constants.Constants.STREET_RIGHT_END;
import static catchOrWaste.model.constants.Constants.STREET_LEFT_END;
import static catchOrWaste.model.FallingObjectModel.amount_FO;
import static catchOrWaste.model.PlayerModel.playerSpeed;
import static catchOrWaste.view.PlayerView.changePlayerImage;

public class PlayerController {

    public static void catchObject(GameWorld gameWorld){
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
