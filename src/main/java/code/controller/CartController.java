package code.controller;

import code.model.components.ImageNameComponent;
import code.model.enums.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import static code.model.CartModel.gate;
import static code.model.CartModel.cartSpeed;
import static code.model.PlayerModel.PLAYERSIZE;
import static code.view.BackgroundView.HOUSE4_X;
import static code.view.CartView.changeCartImage;
import static com.almasb.fxgl.dsl.FXGL.*;


public class CartController {

    private static final double STREET_RIGHT_END = getAppWidth()*0.85-PLAYERSIZE;
    private static final double STREET_LEFT_END = getAppWidth() * 0.08;
    private static final double STREET_HEIGHT = getAppHeight() * 0.775;

    private static final double CURVE_BR = getAppWidth() * 0.8175; // First curve to vertical on right side of street
    private static final double CURVE_BL = getAppWidth() * 0.01; // First curve to vertical on left side of street
    private static final double CURVE_TR = getAppHeight() * 0.2; // Height of Curve on top to the village

    private static final double RECYCLE_HEIGHT = getAppHeight()*0.6;

    private static final double GATE_HEIGHT = getAppHeight()*0.32;
    private static final double GATE_LEFT_END = getAppWidth() * 0.76;
    private static final double GATE_RIGHT_END = getAppWidth() * 0.8675;



    public static void cartMovement(){
        for (Entity entity : FXGL.getGameWorld().getEntitiesByType(EntityType.CART)) {

            //Left Path

            //horizontal movement between Street end and bottom left curve
            if(entity.getX() >= CURVE_BL && entity.getY() >= STREET_HEIGHT){
                entity.setX(entity.getX()- cartSpeed);
            }

            //switch cart direction at bottom left curve
            if(entity.getX() <= CURVE_BL && entity.getY() >= STREET_HEIGHT){
                changeCartImage(entity, entity.getComponent(ImageNameComponent.class).getImageName());
                entity.setX(CURVE_BL);
                entity.setY(entity.getY()-1);
            }

            // vertical movement between bottom left curve and recycling station
            if(entity.getX()< STREET_LEFT_END && entity.getY() < STREET_HEIGHT){
                entity.setY(entity.getY()- cartSpeed);
            }

            //remove cart at recycle station
            if(entity.getX()<STREET_LEFT_END && entity.getY() < RECYCLE_HEIGHT){
                entity.removeFromWorld();            }


            //Right Path
            //horizontal movement to bottom right curve
            if(entity.getX() < CURVE_BR && entity.getX() >= STREET_RIGHT_END && entity.getY()>=STREET_HEIGHT){
                entity.setX(entity.getX() + cartSpeed);
            }

            //switch cart direction at bottom right curve
            if(entity.getX()>= CURVE_BR && entity.getY()>= STREET_HEIGHT){
                changeCartImage(entity,entity.getComponent(ImageNameComponent.class).getImageName());
                entity.setX(CURVE_BR);
                entity.setY(entity.getY()-cartSpeed);
            }


            //horizontal movement between bottom right curve and gate
            if(entity.getX()>= STREET_RIGHT_END && entity.getY() <= STREET_HEIGHT && entity.getX() >= GATE_HEIGHT){
                entity.setY(entity.getY()- cartSpeed);
            }

            //movement at gate
            if(entity.getX() > STREET_RIGHT_END && entity.getY() <= GATE_HEIGHT) {
                changeCartImage(entity, entity.getComponent(ImageNameComponent.class).getImageName());
                entity.setY(GATE_HEIGHT);

                if (gate) {
                    entity.setX(entity.getX() - cartSpeed);
                } else {
                    entity.setX(entity.getX() + cartSpeed);
                }

                if (entity.getX() < CURVE_BR && entity.getX() > GATE_LEFT_END) {
                    entity.setX(entity.getX() - cartSpeed);
                }
                if (entity.getX() > CURVE_BR && entity.getX() < GATE_RIGHT_END) {
                    entity.setX(entity.getX() + cartSpeed);

                }
                if (entity.getX() <= GATE_LEFT_END || entity.getX() >= GATE_RIGHT_END) {
                    changeCartImage(entity, entity.getComponent(ImageNameComponent.class).getImageName());
                    entity.setY(entity.getY() - cartSpeed);
                }

                //vertical movement between gate and upper rail
                if (entity.getX() > STREET_RIGHT_END && entity.getY() <= GATE_HEIGHT && entity.getY() > CURVE_TR) {
                    entity.setY(entity.getY() - cartSpeed);
                }

                //change cart direction at top right curve
                if (entity.getX() > STREET_RIGHT_END && entity.getY() <= CURVE_TR) {
                    changeCartImage(entity, entity.getComponent(ImageNameComponent.class).getImageName());
                }

                //horizontal movement between top right curve and houses
                if (entity.getX() > HOUSE4_X && entity.getY() <= CURVE_TR) {
                    entity.setX(entity.getX() - cartSpeed);
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
