package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.texture.Texture;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.pi4j.example.CatchOrWaste.FxglTest.*;
import static com.pi4j.example.CatchOrWaste.Variables.*;

public class Cart {

    private boolean distance;
    public Cart() {

    }


    //spawn new cart only if other cart is at least 40 pixel away
    public void spawn(GameWorld gameWorld){
        if(gameWorld.getEntitiesByType(EntityType.CART).isEmpty()){
            gameWorld.spawn("CART",getAppWidth()*0.78,getAppHeight()*0.785);
        }else {
            for (Entity entity: gameWorld.getEntitiesByType(EntityType.CART)) {
                if(entity.getX()<getAppWidth()*0.78+40 && entity.getY() >=getAppHeight()*0.785){
                    distance = false;
                }else{
                    distance = true;
                }
            }
            if(distance) {
                gameWorld.spawn("CART", getAppWidth() * 0.78, getAppHeight() * 0.785);
            }
        }
    }

    public void onUpdate(GameWorld gameWorld){
        for (Entity entity: gameWorld.getEntitiesByType(EntityType.CART)) {

            //horizontal movement after spawn
                if(entity.getY() >= getAppHeight() * 0.785){
                    if(entity.getX() < getAppWidth() * 0.836){
                        entity.setX(entity.getX()+CART_SPEED);
                    }
                    if(entity.getX() >= CART_CURVE_BR){
                        entity.getViewComponent().clearChildren();
                        entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_vertical.png")));
                        entity.setX(CART_CURVE_BR);
                        entity.setY(entity.getY()-1);
                        }

                //vertical movement between street height and gate
                }else if(entity.getY()<=getAppHeight()*0.785 && entity.getY()> CART_SWITCH_HEIGHT){
                    if(entity.getY()<= CART_SWITCH_HEIGHT){
                        entity.setY(CART_SWITCH_HEIGHT);
                    }else{
                        entity.setY(entity.getY()-CART_SPEED);
                    }

                //set new image at gate
                }else if(entity.getY()== CART_SWITCH_HEIGHT){
                    entity.getViewComponent().clearChildren();
                    entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_horizontal.png")));
                    entity.setY(CART_SWITCH_HEIGHT -0.1);

                //horizontal movement at gate height
                } else if(entity.getY()== CART_SWITCH_HEIGHT -0.1) {
                    if(gate && entity.getX() == CART_CURVE_BR){
                        entity.setX(entity.getX()-CART_SPEED);
                    }else{
                        entity.setX(entity.getX()+1);
                    }if(entity.getX()>CART_SWITCH_LENGTH_LEFT && entity.getX()<CART_CURVE_BR){
                        entity.setX(entity.getX()-CART_SPEED);
                    }else if(entity.getX()<CART_SWITCH_LENGTH_RIGHT && entity.getX()>CART_CURVE_BR){
                        entity.setX(entity.getX()+CART_SPEED);
                    }if(entity.getX()>CART_SWITCH_LENGTH_RIGHT || entity.getX()<CART_SWITCH_LENGTH_LEFT){
                        entity.getViewComponent().clearChildren();
                        entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_vertical.png")));
                        entity.setY(CART_SWITCH_HEIGHT-1);
                    }

                //vertical movement between gate and upper rail
                }else if(entity.getY()<= CART_SWITCH_HEIGHT -1 && entity.getY()>getAppHeight()*0.159+CART_SPEED){
                    entity.setY(entity.getY()-CART_SPEED);

                //set new image if upper rail is reached
                }else if(entity.getY()<getAppHeight()*0.159+CART_SPEED && entity.getY()>getAppHeight()*0.16){
                    entity.getViewComponent().clearChildren();
                    entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_horizontal.png")));
                    entity.setY(getAppHeight()*0.16);

                //horizontal movement on upper rail
                }else if(entity.getY()<= getAppHeight()*0.16&&entity.getX()>getAppWidth()*0.1){
                    entity.setX(entity.getX()-CART_SPEED);

                //remove cart at the end
                }else{
                entity.removeFromWorld();
                }
            }
        }
}
