package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;

import java.util.List;
import java.util.Objects;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGL.spawn;
import static com.pi4j.example.CatchOrWaste.FxglTest.AppHeight;
import static com.pi4j.example.CatchOrWaste.FxglTest.AppWidth;
import static com.pi4j.example.CatchOrWaste.Variables.*;

public class Cart {

    private boolean distance;
    public Cart() {

    }

    public void spawn(GameWorld gameWorld){
        Entity[] entities = gameWorld.getEntitiesByType(EntityType.CART).toArray(new Entity[0]);

        if(gameWorld.getEntitiesByType(EntityType.CART).isEmpty()){
            gameWorld.spawn("CART",AppWidth*0.78,AppHeight*0.785);
        }else {
            for (Entity entity: entities) {
                if(entity.getX() >=AppWidth*0.785 && entity.getX()<AppHeight*0.77+30){
                    distance = false;
                }else{
                    distance = true;
                }
            }
            if(distance) {
                gameWorld.spawn("CART", AppWidth * 0.78, AppHeight * 0.785);

            }
        }
    }
}
