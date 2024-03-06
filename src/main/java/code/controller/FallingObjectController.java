package code.controller;

import code.model.components.IsCatchedComponent;
import code.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;

import static code.model.FallingObjectModel.amount_FO;
import static code.view.FallingObjectView.spawnObjects;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class FallingObjectController {

    public static void dropObjects(GameWorld gameWorld){
        var entities = gameWorld.getEntitiesByType(EntityType.OBJECT);
        if(!entities.isEmpty()){
            for(Entity entity : entities){
                if(entity.getY()>=getAppHeight()){
                   entity.removeFromWorld();
                }
            }
        }
    }

}
