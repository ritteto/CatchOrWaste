package CatchOrWaste;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;

public class SimpleFactory implements EntityFactory {

    @Spawns("WP")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("Wegwerfpolizist_R.png")
                .scale(0.035,0.035)
                .with(new ProjectileComponent(new Point2D(1, 0), 150))
                .build();
    }

}
