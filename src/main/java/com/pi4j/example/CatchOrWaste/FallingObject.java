package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;

public class FallingObject {
    public FallingObject() {
    }

    public void onUpdate(GameWorld gameWorld){
        if(!gameWorld.getEntitiesByType(EntityType.OBJECT).isEmpty()){
            for (Entity entity: gameWorld.getEntitiesByType(EntityType.OBJECT)) {
                if(entity.getY()>getAppHeight()){
                    entity.removeFromWorld();
                }
            }
        }
    }
}
