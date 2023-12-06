package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;


import java.util.Arrays;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.pi4j.example.CatchOrWaste.Variables.FALLING_OBJECT_AMOUNT;
import static com.pi4j.example.CatchOrWaste.Variables.STREET_RIGHT;

public class FallingObject {

    private Entity entity;


   public FallingObject(Entity entity) {
        this.entity = entity;
       entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(30,30)));
    }

    public void onUpdate(GameWorld gameWorld){

       int house1 = 100;
       int house2 = 200;
       int house3 = 300;
       int house4 = 400;
       int house5 = 500;
       int house6 = 600;


        int [] houseX = {house1, house2, house3, house4,house5,house6};

        Random random = new Random();
        int randomHouse = random.nextInt(houseX.length);


        if(gameWorld.getEntitiesByType(EntityType.OBJECT).size() < FALLING_OBJECT_AMOUNT){
            gameWorld.spawn("OBJECT",houseX[randomHouse],0);
        }
        if(!gameWorld.getEntitiesByType(EntityType.OBJECT).isEmpty()){
            for (Entity entity: gameWorld.getEntitiesByType(EntityType.OBJECT)) {
                if(entity.getY()>=getAppHeight()){
                    entity.removeFromWorld();
                    gameWorld.spawn("OBJECT",houseX[randomHouse],0);
                }
            }
        }


    }
}
