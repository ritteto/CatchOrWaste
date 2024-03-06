package code.controller;

import code.model.components.ImageNameComponent;
import code.model.enums.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import static code.model.CartModel.*;
import static code.model.Constants.Constants.*;
import static code.view.CartView.changeCartImage;


public class CartController {


    public static void cartMovement(){
        for (Entity entity : FXGL.getGameWorld().getEntitiesByType(EntityType.CART)) {

            //Left Path

            //horizontal movement between Street end and bottom left curve
            if(entity.getX() >= CURVE_BL && entity.getY() >= STREET_HEIGHT){
                entity.setX(entity.getX() + getCartSpeed());
            }

            //switch cart direction at bottom left curve
            if(entity.getX() <= CURVE_BL && entity.getY() >= STREET_HEIGHT){
                changeCartImage(entity, entity.getComponent(ImageNameComponent.class).getImageName());
                entity.setX(CURVE_BL);
                entity.setY(entity.getY()-1);
            }

            // vertical movement between bottom left curve and recycling station
            if(entity.getX()< STREET_LEFT_END && entity.getY() < STREET_HEIGHT){
                entity.setY(entity.getY()- getCartSpeed());
            }

            //remove cart at recycle station
            if(entity.getX()<STREET_LEFT_END && entity.getY() < RECYCLE_HEIGHT){
                entity.removeFromWorld();            }


            //Right Path
            //horizontal movement to bottom right curve
            if(entity.getX() < CURVE_BR && entity.getX() >= STREET_RIGHT_END && entity.getY()>=STREET_HEIGHT){
                entity.setX(entity.getX() + getCartSpeed());
            }

            //switch cart direction at bottom right curve
            if(entity.getX()>= CURVE_BR && entity.getY()>= STREET_HEIGHT){
                changeCartImage(entity,entity.getComponent(ImageNameComponent.class).getImageName());
                entity.setX(CURVE_BR);
                entity.setY(entity.getY()-getCartSpeed());
            }


            //horizontal movement between bottom right curve and gate
            if(entity.getX()>= STREET_RIGHT_END && entity.getY() <= STREET_HEIGHT && entity.getX() >= GATE_HEIGHT){
                entity.setY(entity.getY()- getCartSpeed());
            }

            //movement at gate
            if(entity.getX() > STREET_RIGHT_END && entity.getY() <= GATE_HEIGHT) {
                changeCartImage(entity, entity.getComponent(ImageNameComponent.class).getImageName());
                entity.setY(GATE_HEIGHT);

                if (isGate()) {
                    entity.setX(entity.getX() - getCartSpeed());
                } else {
                    entity.setX(entity.getX() + getCartSpeed());
                }

                if (entity.getX() < CURVE_BR && entity.getX() > GATE_LEFT_END) {
                    entity.setX(entity.getX() - getCartSpeed());
                }
                if (entity.getX() > CURVE_BR && entity.getX() < GATE_RIGHT_END) {
                    entity.setX(entity.getX() + getCartSpeed());

                }
                if (entity.getX() <= GATE_LEFT_END || entity.getX() >= GATE_RIGHT_END) {
                    changeCartImage(entity, entity.getComponent(ImageNameComponent.class).getImageName());
                    entity.setY(entity.getY() - getCartSpeed());
                }

                //vertical movement between gate and upper rail
                if (entity.getX() > STREET_RIGHT_END && entity.getY() <= GATE_HEIGHT && entity.getY() > CURVE_TR) {
                    entity.setY(entity.getY() - getCartSpeed());
                }

                //change cart direction at top right curve
                if (entity.getX() > STREET_RIGHT_END && entity.getY() <= CURVE_TR) {
                    changeCartImage(entity, entity.getComponent(ImageNameComponent.class).getImageName());
                }

                //horizontal movement between top right curve and houses
                if (entity.getX() > HOUSE4_X && entity.getY() <= CURVE_TR) {
                    entity.setX(entity.getX() - getCartSpeed());
                }

                //remove cart at House
                if (entity.getX() <= HOUSE4_X && entity.getY()<= CURVE_TR){
                    entity.removeFromWorld();
                }

            }

        }
    }

    public void method(){

    }
}
