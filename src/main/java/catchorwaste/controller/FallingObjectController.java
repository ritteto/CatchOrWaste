package catchorwaste.controller;

import catchorwaste.model.components.IsCatchedComponent;
import catchorwaste.model.components.CargoComponent;
import catchorwaste.model.components.PlayerDirectionComponent;
import catchorwaste.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;

public class FallingObjectController {


    public static void dropObjects(GameWorld gameWorld) {
        var entities = gameWorld.getEntitiesByType(EntityType.OBJECT);
        if (!entities.isEmpty()) {
            for (Entity entity : entities) {
                if (entity.getY() >= getAppHeight()) {
                    entity.removeFromWorld();
                }
            }
        }

    }

    public static void stickToPlayer(GameWorld gameWorld) {
        var entities = gameWorld.getEntitiesByType(EntityType.OBJECT);
        var players = gameWorld.getEntitiesByType(EntityType.PLAYER);
        for (Entity object : entities) {
            var isCatched = object.getComponent(IsCatchedComponent.class).isCatched();
            for (Entity player : players) {
                if (!player.getComponent(CargoComponent.class).isFull()
                        && isCatched) {
                    player.getComponent(CargoComponent.class).setCatchedEntity(object);
                        visuals(player, object);
                } else if (player.getComponent(CargoComponent.class).isFull()
                        && isCatched && player.getComponent(CargoComponent.class).getCatchedEntity().equals(object)) {
                        visuals(player, object);
                }
            }
        }
    }

    private static void visuals(Entity player, Entity object){
        var width = player.getBoundingBoxComponent().getWidth();
        if (player.getComponent(PlayerDirectionComponent.class).getDirection()) {
            object.setX(player.getX()+width*0.5);
            object.setY(player.getY());
        } else {
            object.setX(player.getX() - width*0.5);
            object.setY(player.getY());
        }
    }

}
