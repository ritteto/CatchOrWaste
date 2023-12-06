package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;


import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.pi4j.example.CatchOrWaste.Variables.FALLING_OBJECT_AMOUNT;
import static com.pi4j.example.CatchOrWaste.Variables.STREET_RIGHT;

public class FallingObject {
    public FallingObject(Entity entity) {
        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(30,30)));
    }

    public void onUpdate(GameWorld gameWorld){
        if(gameWorld.getEntitiesByType(EntityType.OBJECT).size() < FALLING_OBJECT_AMOUNT){
            gameWorld.spawn("OBJECT",random(0,STREET_RIGHT-30),0);
        }
        if(!gameWorld.getEntitiesByType(EntityType.OBJECT).isEmpty()){
            for (Entity entity: gameWorld.getEntitiesByType(EntityType.OBJECT)) {
                if(entity.getY()>getAppHeight()){
                    entity.removeFromWorld();
                }
            }
        }

    }
}
