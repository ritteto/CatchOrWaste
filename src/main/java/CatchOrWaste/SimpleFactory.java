package CatchOrWaste;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SimpleFactory implements EntityFactory {

    public static double scale=0.035; //scale=0.035

    @Spawns("PLAYER")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("wegwerfpolizist_l.png")
                .scale(scale,scale)
                .type(EntityType.PLAYER)
                .build();
    }

    @Spawns("OBJECT")
    public Entity newObject(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("apple.png")
                .type(EntityType.OBJECT)
                .with(new ProjectileComponent(new Point2D(0,1),50))
                .build();
    }

    @Spawns("CART")
    public Entity newCart(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("cart_horizontal.png")
                .scale(0.03,0.03)
                .type(EntityType.CART)
                .build();
    }





}
