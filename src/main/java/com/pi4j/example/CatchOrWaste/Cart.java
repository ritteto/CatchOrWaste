package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.texture.Texture;

import java.util.Arrays;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.pi4j.example.CatchOrWaste.FxglTest.*;
import static com.pi4j.example.CatchOrWaste.Variables.*;

public class Cart {

    private boolean distance, direction;
    private Entity cargo, entity;

    //idk why this is here
    public Cart() {}

    public Cart(Entity entity) {
        this.entity = entity;
    }


    public void setCargo(Entity cargo) {
        this.cargo = cargo;
    }

    public Entity getCargo() {
        return cargo;
    }

    //spawn new cart only if other cart is at least 40 pixel away
    public void spawn(GameWorld gameWorld, Boolean left, Entity cargo){
        if(gameWorld.getEntitiesByType(EntityType.CART).isEmpty()){
            if(left){
                Cart cart = new Cart(gameWorld.spawn("CART",getAppWidth()*0.06,getAppHeight()*0.785));
                cart.setCargo(cargo);
                addCartToArray(cart);
            }else{
                Cart cart = new Cart(gameWorld.spawn("CART",getAppWidth()*0.78,getAppHeight()*0.785));
                cart.setCargo(cargo);
                addCartToArray(cart);
            }

        }else {
            for (Entity entity: gameWorld.getEntitiesByType(EntityType.CART)) {
                if(entity.getX()<getAppWidth()*0.78+40 && entity.getY() >=getAppHeight()*0.785){
                    distance = false;
                }else{
                    distance = true;
                }
            }
            if(distance) {
                if(left){
                    Cart cart = new Cart(gameWorld.spawn("CART",getAppWidth()*0.06,getAppHeight()*0.785));
                    cart.setCargo(cargo);
                    addCartToArray(cart);
                }else{
                    Cart cart = new Cart(gameWorld.spawn("CART",getAppWidth()*0.78,getAppHeight()*0.785));
                    cart.setCargo(cargo);
                    addCartToArray(cart);
                }
            }
        }
    }

    public void onUpdate(GameWorld gameWorld){

        attachCargo();

        for (Entity entity: gameWorld.getEntitiesByType(EntityType.CART)) {

            //cart movement left rail
            if(entity.getY() >= getAppHeight()*0.785 && entity.getX()<=getAppWidth()*0.2){
                entity.setX(entity.getX()-CART_SPEED);
                direction = true;
            }
            if(entity.getY() >= getAppHeight()*0.785 && entity.getX() <= CART_CURVE_BL){
                entity.getViewComponent().clearChildren();
                entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_vertical.png")));
                direction = false;
                entity.setX(CART_CURVE_BL);
                entity.setY(entity.getY()-1);
            }
            if(entity.getY() < getAppHeight()*0.785 && entity.getX()<getAppWidth()*0.5){
                entity.setY(entity.getY()-CART_SPEED);
            }
            if(entity.getY() < getAppHeight()*0.6 && entity.getX()<getAppWidth()*0.5){
                removeCart(entity);
            }

            //cart movement right rail
            //horizontal movement after spawn
                if(entity.getY() >= getAppHeight() * 0.785){

                    if(entity.getX() < getAppWidth() * 0.836 && entity.getX()> getAppWidth()*0.5){
                        entity.setX(entity.getX()+CART_SPEED);
                        direction = true;
                    }
                    if(entity.getX() >= CART_CURVE_BR){
                        entity.getViewComponent().clearChildren();
                        entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_vertical.png")));
                        direction = false;
                        entity.setX(CART_CURVE_BR);
                        entity.setY(entity.getY()-1);
                        }

                //vertical movement between street height and gate
                }else if(entity.getY()<=getAppHeight()*0.785 && entity.getY()> CART_SWITCH_HEIGHT && entity.getX()>getAppWidth()*0.5){
                    if(entity.getY()<= CART_SWITCH_HEIGHT){
                        entity.setY(CART_SWITCH_HEIGHT);
                    }else{
                        entity.setY(entity.getY()-CART_SPEED);
                    }

                //set new image at gate
                }else if(entity.getY()== CART_SWITCH_HEIGHT && entity.getX() > getAppWidth()*0.5){
                    entity.getViewComponent().clearChildren();
                    entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_horizontal.png")));
                    direction = true;
                    entity.setY(CART_SWITCH_HEIGHT -0.1);

                //horizontal movement at gate height
                } else if(entity.getY()== CART_SWITCH_HEIGHT -0.1 && entity.getX() > getAppWidth()*0.5) {
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
                        direction = false;
                        entity.setY(CART_SWITCH_HEIGHT-1);
                    }

                //vertical movement between gate and upper rail
                }else if(entity.getY()<= CART_SWITCH_HEIGHT -1 && entity.getY()>CART_CURVE_RIGHT_HEIGHT+CART_SPEED && entity.getX()>getAppWidth()*0.5){
                    entity.setY(entity.getY()-CART_SPEED);

                //set new image if upper rail is reached
                }else if(entity.getY()<CART_CURVE_RIGHT_HEIGHT+CART_SPEED && entity.getY()>CART_CURVE_RIGHT_HEIGHT && entity.getX()>getAppWidth()*0.5){
                    entity.getViewComponent().clearChildren();
                    entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_horizontal.png")));
                    direction = true;
                    entity.setY(CART_CURVE_RIGHT_HEIGHT);

                //horizontal movement on upper rail
                }else if(entity.getY()<= CART_CURVE_RIGHT_HEIGHT &&entity.getX()>getAppWidth()*0.7){
                    entity.setX(entity.getX()-CART_SPEED);

                //remove cart at the end
                }else if(entity.getY()<= CART_CURVE_RIGHT_HEIGHT &&entity.getX()<getAppWidth()*0.7&& entity.getX()>getAppWidth()*0.5){
                    removeCart(entity);
                }
            }


            if(this.cargo != null && this.entity != null){
                this.cargo.setX(this.entity.getX());
            }
        }

        private void addCartToArray(Cart cart){
            if(Arrays.asList(carts).contains(null)){
                for (int i=0; i< carts.length; i++){
                    if(carts[i] == null){
                        carts[i]=cart;
                        break;
                    }
                }
            }else{
                carts = Arrays.copyOf(carts, carts.length+1);
                carts[carts.length-1] = cart;
            }
        }
        private void removeCart(Entity en){
            en.removeFromWorld();
            //cargo.removeFromWorld();
            for(int i=0; i<carts.length; i++) {
                if (carts[i] != null && carts[i].entity.equals(en)) {
                    carts[i].cargo.removeFromWorld();
                    carts[i] = null;
                }
            }
        }

        private void attachCargo(){
            for (Cart cart : carts) {
                if(direction){
                    if(cart!= null && cart.cargo != null){
                        cart.cargo.setX(cart.entity.getX()+FO_SIZE+(CART_SIZE/4));
                        cart.cargo.setY(cart.entity.getY());

                    }
                }else{
                    if(cart!= null && cart.cargo != null){
                        cart.cargo.setX(cart.entity.getX()+FO_SIZE+(CART_SIZE/4));
                        cart.cargo.setY(cart.entity.getY());

                    }
                }

            }
        }

}
