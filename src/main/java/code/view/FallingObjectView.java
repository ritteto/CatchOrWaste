package code.view;

import code.model.components.CargoComponent;
import code.model.components.ImageNameComponent;
import code.model.components.IsCatchedComponent;
import code.model.components.PlayerDirectionComponent;
import code.model.enums.EntityType;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;

import java.util.Random;

import static code.model.Constants.Constants.*;
import static code.model.FallingObjectModel.amount_FO;

public class FallingObjectView {

    public static void spawnObjects(GameWorld gameWorld){
        if (gameWorld.getEntitiesByType(EntityType.OBJECT).size() < amount_FO) {
            Random random = new Random();
            var range = STREET_RIGHT_END - random.nextDouble(0, STREET_RIGHT_END-STREET_LEFT_END);
            gameWorld.spawn("OBJECT",range,0);
        }

    }

}
