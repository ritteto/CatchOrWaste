package code.view;

import code.model.CartModel;
import code.model.enums.EntityType;
import code.model.components.ImageNameComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.texture.Texture;

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

    public static void changeCartImage(Entity entity, String string){
        String url;
        if(string.contains("horizontal")) {
            url= "carts/" + entity.getComponent(ImageNameComponent.class).getImageName() + "_cart_horizontal.png";
        }else{
            url= "carts/" + entity.getComponent(ImageNameComponent.class).getImageName() + "_cart_vertical.png";
        }
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage(url)));
    }

    private static String cargoName(Entity entity){
        return entity.getComponent(ImageNameComponent.class).getImageName();
    }
}
