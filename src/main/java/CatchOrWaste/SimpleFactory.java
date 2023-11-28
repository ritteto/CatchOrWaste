package CatchOrWaste;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;

public class SimpleFactory implements EntityFactory {

    public double scale=0.035;

    @Spawns("WP")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("Wegwerfpolizist_R.png")
                .scale(scale,scale)
                .type(EntityType.PLAYER)
                .build();
    }

    @Spawns("Background")
    public Entity Background(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("cool.png")
                .scale(5,5)
                .build();
    }


}
