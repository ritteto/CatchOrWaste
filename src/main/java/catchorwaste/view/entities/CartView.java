package catchorwaste.view.entities;

import catchorwaste.model.entities.CartModel;
import catchorwaste.model.components.CartDirectionComponent;
import catchorwaste.model.components.ImageNameComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static catchorwaste.CatchOrWasteApp.imageMap;


public class CartView {

    public static void spawnCart(GameWorld gameWorld, double x, double y, Entity cargo){
        gameWorld.spawn("CART", new SpawnData(x,y)
                .put("Cargo", cargo)
                .put("CargoName", cargoName(cargo))
                .put("Speed", (double) CartModel.getCartSpeed()));
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
