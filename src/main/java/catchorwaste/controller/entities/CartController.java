package catchorwaste.controller.entities;

import catchorwaste.controller.PunktesystemController;
import catchorwaste.model.components.CartDirectionComponent;
import catchorwaste.model.components.ImageNameComponent;
import catchorwaste.model.enums.EntityType;
import catchorwaste.view.entities.CartView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;

import static catchorwaste.model.variables.Constants.CART_HEIGHT_AT_STREET;
import static catchorwaste.model.variables.Constants.CURVE_BL;
import static catchorwaste.model.variables.Constants.CURVE_BR;
import static catchorwaste.model.variables.Constants.CURVE_TR;
import static catchorwaste.model.variables.Constants.GATE_HEIGHT;
import static catchorwaste.model.variables.Constants.GATE_LEFT_END;
import static catchorwaste.model.variables.Constants.GATE_RIGHT_END;
import static catchorwaste.model.variables.Constants.RECYCLE_HEIGHT;
import static catchorwaste.model.variables.Constants.STREET_LEFT_END;
import static catchorwaste.model.variables.Constants.STREET_RIGHT_END;


import static catchorwaste.model.entities.CartModel.getCartSpeed;
import static catchorwaste.model.entities.CartModel.isGate;
import static catchorwaste.view.entities.CartView.changeCartImage;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;


public class CartController {

    private PunktesystemController punktesystemController;
    private CartView cartView;

    public CartController(PunktesystemController punktesystemController){
        this.punktesystemController = punktesystemController;
        this.cartView = new CartView();
    }

    public void cartMovement(GameWorld gameWorld){
        for (Entity entity : gameWorld.getEntitiesByType(EntityType.CART)) {

            //----------------Left Path----------------
            //horizontal movement between Street end and bottom left curve
            if(entity.getX() >= CURVE_BL &&
                    entity.getX() <= STREET_LEFT_END  && entity.getY() >= CART_HEIGHT_AT_STREET){
                entity.translateX(-getCartSpeed());

            }

            //switch cart direction at bottom left curve
            if(entity.getX() <= CURVE_BL && entity.getY() >= CART_HEIGHT_AT_STREET){
                changeCartImage(entity);
                entity.setX(CURVE_BL);
                entity.translateY(-1);
            }

            // vertical movement between bottom left curve and recycling station
            if(entity.getX()< STREET_LEFT_END && entity.getY() < CART_HEIGHT_AT_STREET){
                entity.translateY(-getCartSpeed());
            }

            //remove cart at recycle station
            if(entity.getX()<STREET_LEFT_END && entity.getY() < RECYCLE_HEIGHT){
                entity.removeFromWorld();            }


            //----------------Right Path----------------
            //horizontal movement to bottom right curve
            if(entity.getX() < CURVE_BR && entity.getX() >= STREET_RIGHT_END && entity.getY()>=CART_HEIGHT_AT_STREET){
                entity.translateX(getCartSpeed());
            }

            //switch cart direction at bottom right curve
            if(entity.getX()>= CURVE_BR && entity.getY()>= CART_HEIGHT_AT_STREET){
                changeCartImage(entity);
                entity.setX(CURVE_BR);
                entity.translateY(-1);
            }


            //vertical movement between bottom right curve and gate
            if(entity.getX() >= STREET_RIGHT_END &&
                    entity.getY() < CART_HEIGHT_AT_STREET && entity.getY() >= GATE_HEIGHT){
                entity.translateY(-getCartSpeed());
            }

            //turn at gate
            if(entity.getX() > STREET_RIGHT_END && entity.getY() == GATE_HEIGHT) {
                changeCartImage(entity);
                entity.setX(CURVE_BR);
                entity.setY(GATE_HEIGHT-1);

            }

            //horizontal movement at gate
            if(entity.getX()>getAppWidth()*0.5 && entity.getY()==GATE_HEIGHT-1){

                if (entity.getX() == CURVE_BR && isGate()) {
                    entity.translateX(-1);
                } else if(entity.getX() == CURVE_BR && !isGate()){
                    entity.translateX(+1);
                }


                if (entity.getX() < CURVE_BR && entity.getX() > GATE_LEFT_END) {
                    entity.translateX(-getCartSpeed());
                }
                if (entity.getX() > CURVE_BR && entity.getX() < GATE_RIGHT_END) {
                    entity.translateX(getCartSpeed());
                }
                if (entity.getX() <= GATE_LEFT_END){
                    entity.setX(GATE_LEFT_END);
                    changeCartImage(entity);
                    entity.translateY(-1);
                }
                if (entity.getX() >= GATE_RIGHT_END) {
                    entity.setX(GATE_RIGHT_END);
                    changeCartImage(entity);
                    entity.translateY(-1);
                }
            }



            //vertical movement between gate and upper rail
            if (entity.getX() > getAppWidth()*0.5 && entity.getY() <= GATE_HEIGHT-2 && entity.getY() > CURVE_TR) {
                entity.translateY(-getCartSpeed());
            }

            //change cart direction at top right curve
            if (entity.getX() > getAppWidth()*0.5 && entity.getY() <= CURVE_TR
                    && !entity.getComponent(CartDirectionComponent.class).getDirection()) {
                changeCartImage(entity);
                entity.setY(CURVE_TR-1);
            }



            //horizontal movement between top right curve and houses
            if (entity.getX() > getAppWidth()*0.7 && entity.getY() <= CURVE_TR
                    && entity.getComponent(CartDirectionComponent.class).getDirection()) {
                entity.translateX(-getCartSpeed());
            }

            //remove cart at House
            if (entity.getX() <= getAppWidth()*0.7 && entity.getY()<= CURVE_TR){
                entity.removeFromWorld();
            }


        }
    }
    
    public void onWorkstationCollision(Entity cart, Entity workstation){
            if(cart.getY()== workstation.getY()+ workstation.getHeight()*0.75) {
                if (cart.getX() == GATE_LEFT_END &&
                        workstation.getComponent(ImageNameComponent.class).getImageName().contains("markt")) {
                    var points = calculatePoints(workstation, cart);
                    punktesystemController.addPoints(points);
                    punktesystemController.displayUpdate(points, cart.getX(), cart.getY()+cart.getHeight()*0.3);
                } else if (cart.getX() == GATE_RIGHT_END &&
                        workstation.getComponent(ImageNameComponent.class).getImageName().contains("reparieren")) {
                    var points = calculatePoints(workstation, cart);
                    punktesystemController.addPoints(points);
                    punktesystemController.displayUpdate(points, cart.getX(), cart.getY()+cart.getHeight()*0.3);
                } else if (cart.getX() == CURVE_BL) {
                    var points = calculatePoints(workstation, cart);
                    punktesystemController.addPoints(points);
                    punktesystemController.displayUpdate(points, cart.getX(), cart.getY()+cart.getHeight()*0.3);
                }

            }
    }


    private int calculatePoints(Entity station, Entity cart){
        var cargoNameSplit = cart.getComponent(ImageNameComponent.class).getImageName().split("_");
        var state = cargoNameSplit[1];
        var cargo = cargoNameSplit[0];
        var workstation = station.getComponent(ImageNameComponent.class).getImageName().split("_")[0];
        return punktesystemController.getPointsMap().get(workstation).get(cargo).get(state);
    }

    public static boolean cartDistance(boolean left){
        boolean return_bol = true;
        for (Entity en : getGameWorld().getEntitiesByType(EntityType.CART)) {
            if(en.getY()== CART_HEIGHT_AT_STREET){
                if(left && en.getX() <= STREET_LEFT_END-13-en.getWidth()){
                    return_bol = true;
                }else if (!left && en.getX() >= STREET_RIGHT_END+50+en.getWidth()){
                    return_bol = true;
                }else{
                    return_bol = false;
                }
            }
        }
        return return_bol;
    }

    public void spawnCart(double x, double y, Entity cargo){
        cartView.spawnCart(x,y,cargo);
    }

}
