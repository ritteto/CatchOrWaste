package code.controller;

import code.model.components.CargoComponent;
import code.model.components.ImageNameComponent;
import code.model.enums.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;

import static code.model.CartModel.CART_SPEED;
import static code.view.CartView.changeCartImage;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;


public class CartController {

    private static final double STREET_RIGHT_END = getAppWidth();
    private static final double CURVE_BR = getAppWidth() * 0.8175; // First curve to vertical on right side of street
    private static final double STREET_HEIGHT = getAppHeight() * 0.775;
    private static final double CART_CURVE_BL = getAppWidth() * 0.01; // First curve to vertical on left side of street
    private static final double CART_CURVE_TR = getAppHeight() * 0.2; // Height of Curve on top to the village

    public static void cartMovement(){
        for (Entity entity : FXGL.getGameWorld().getEntitiesByType(EntityType.TEST)) {

            if(entity.getX()>= CURVE_BR && entity.getY()>= STREET_HEIGHT){
                changeCartImage(entity,entity.getComponent(ImageNameComponent.class).getImageName());
                entity.getComponent(ProjectileComponent.class).setDirection(new Point2D(0,-1));
                /*if(entity.hasComponent(ProjectileComponent.class)){
                    entity.getComponent(ProjectileComponent.class).allowRotation(true);
                    entity.getComponent(ProjectileComponent.class).setDirection(new Point2D(0,-1));
                    //entity.addComponent(new ProjectileComponent(new Point2D(0,-1),CART_SPEED));
                    //entity.getComponent(ProjectileComponent.class).setDirection(new Point2D(0,-1));
                }

                 */
            }


        }
    }
}
