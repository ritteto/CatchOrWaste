package catchorwaste.controller;

import catchorwaste.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;


import static catchorwaste.model.CartModel.getCartSpeed;
import static catchorwaste.model.CartModel.isGate;
import static catchorwaste.model.constants.Constants.CURVE_BL;
import static catchorwaste.model.constants.Constants.STREET_LEFT_END;
import static catchorwaste.model.constants.Constants.STREET_RIGHT_END;
import static catchorwaste.model.constants.Constants.STREET_HEIGHT;
import static catchorwaste.model.constants.Constants.GATE_HEIGHT;
import static catchorwaste.model.constants.Constants.GATE_LEFT_END;
import static catchorwaste.model.constants.Constants.GATE_RIGHT_END;
import static catchorwaste.model.constants.Constants.RECYCLE_HEIGHT;
import static catchorwaste.model.constants.Constants.CURVE_BR;
import static catchorwaste.model.constants.Constants.CURVE_TR;
import static catchorwaste.view.CartView.changeCartImage;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;


public class CartController {

    public static void cartMovement(GameWorld gameWorld){
        for (Entity entity : gameWorld.getEntitiesByType(EntityType.CART)) {

            //Left Path

            //horizontal movement between Street end and bottom left curve
            if(entity.getX() >= CURVE_BL && entity.getX() <= STREET_LEFT_END  && entity.getY() >= STREET_HEIGHT){
                entity.translateX(-getCartSpeed());

            }

            //switch cart direction at bottom left curve
            if(entity.getX() <= CURVE_BL && entity.getY() >= STREET_HEIGHT){
                changeCartImage(entity);
                entity.setX(CURVE_BL);
                entity.translateY(-1);
            }

            // vertical movement between bottom left curve and recycling station
            if(entity.getX()< STREET_LEFT_END && entity.getY() < STREET_HEIGHT){
                entity.translateY(-getCartSpeed());
            }

            //remove cart at recycle station
            if(entity.getX()<STREET_LEFT_END && entity.getY() < RECYCLE_HEIGHT){
                entity.removeFromWorld();            }


            //Right Path
            //horizontal movement to bottom right curve
            if(entity.getX() < CURVE_BR && entity.getX() >= STREET_RIGHT_END && entity.getY()>=STREET_HEIGHT){
                entity.translateX(getCartSpeed());
            }

            //switch cart direction at bottom right curve
            if(entity.getX()>= CURVE_BR && entity.getY()>= STREET_HEIGHT){
                changeCartImage(entity);
                entity.setX(CURVE_BR);
                entity.translateY(-1);
            }


            //vertical movement between bottom right curve and gate
            if(entity.getX() >= STREET_RIGHT_END && entity.getY() < STREET_HEIGHT && entity.getY() >= GATE_HEIGHT){
                entity.translateY(-getCartSpeed());
            }

            //turn at gate
            if(entity.getX() > STREET_RIGHT_END && entity.getY() == GATE_HEIGHT) {
                changeCartImage(entity);
                entity.setY(GATE_HEIGHT-1);

            }

            //horizontal movement at gate
            if(entity.getX()>STREET_RIGHT_END && entity.getY()==GATE_HEIGHT-1){

                if (isGate()) {
                    entity.translateX(-1);
                } else {
                    entity.translateX(+1);
                }

                if (entity.getX() < CURVE_BR && entity.getX() > GATE_LEFT_END) {
                    entity.translateX(-getCartSpeed());
                }
                if (entity.getX() > CURVE_BR && entity.getX() < GATE_RIGHT_END) {
                    entity.translateX(getCartSpeed());
                }
                if (entity.getX() <= GATE_LEFT_END || entity.getX() >= GATE_RIGHT_END) {
                    changeCartImage(entity);
                    entity.translateY(-1);
                }
            }



            //vertical movement between gate and upper rail
            if (entity.getX() > STREET_RIGHT_END && entity.getY() <= GATE_HEIGHT-2 && entity.getY() > CURVE_TR) {
                entity.translateY(-getCartSpeed());
            }

            //change cart direction at top right curve
            if (entity.getX() > STREET_RIGHT_END && entity.getY() == CURVE_TR) {
                changeCartImage(entity);
                entity.setY(CURVE_TR-1);
            }

            //horizontal movement between top right curve and houses
            if (entity.getX() > getAppWidth()*0.7 && entity.getY() <= CURVE_TR) {
                entity.translateX(-getCartSpeed());
            }

            //remove cart at House
            if (entity.getX() <= getAppWidth()*0.7 && entity.getY()<= CURVE_TR){
                entity.removeFromWorld();
            }


        }
    }


}
