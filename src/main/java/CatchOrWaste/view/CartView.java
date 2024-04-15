package CatchOrWaste.view;

import CatchOrWaste.model.CartModel;
import CatchOrWaste.model.components.CartDirectionComponent;
import CatchOrWaste.model.enums.EntityType;
import CatchOrWaste.model.components.ImageNameComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static CatchOrWaste.CatchOrWasteApp.imageMap;
import static com.almasb.fxgl.dsl.FXGL.*;


public class CartView {

    public static void spawnCart(GameWorld gameWorld, double x, double y, Entity cargo){
        boolean distance = false;
        if(gameWorld.getEntitiesByType(EntityType.CART).isEmpty()){
            gameWorld.spawn("CART", new SpawnData(x,y).put("Cargo", cargo).put("CargoName", cargoName(cargo)).put("Speed", (double) CartModel.getCartSpeed()));
        }else{
            for (Entity entity: gameWorld.getEntitiesByType(EntityType.CART)) {
                distance = !(entity.getX() < getAppWidth() * 0.78 + 40) || !(entity.getY() >= getAppHeight() * 0.775);
            }
            if(distance){
                gameWorld.spawn("CART", new SpawnData(x,y).put("Cargo", cargo).put("CargoName", cargoName(cargo)).put("Speed", (double) CartModel.getCartSpeed()));
            }
        }
    }

    public static void changeCartImage(Entity entity){
        Image img;
        String name = entity.getComponent(ImageNameComponent.class).getImageName();
        if(entity.getComponent(CartDirectionComponent.class).getDirection()) {
            img = imageMap.get(name+"_cart_vertical");
            entity.getComponent(CartDirectionComponent.class).setDirection(false);
        }else{
            img = imageMap.get(name+"_cart_horizontal");
            entity.getComponent(CartDirectionComponent.class).setDirection(true);
        }
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(new ImageView(img));
    }

    private static String cargoName(Entity entity){
        return entity.getComponent(ImageNameComponent.class).getImageName();
    }
}
