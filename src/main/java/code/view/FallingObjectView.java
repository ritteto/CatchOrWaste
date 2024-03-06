package code.view;

import code.FallingObject;
import code.model.enums.EntityType;
import com.almasb.fxgl.entity.GameWorld;

import java.util.Random;

import static code.model.Constants.Constants.*;
import static code.model.FallingObjectModel.amount_FO;

public class FallingObjectView {

    public static void spawnObjects(GameWorld gameWorld){
        System.out.println("Test: "+gameWorld.getEntitiesByType(EntityType.OBJECT).size()+"/"+amount_FO);
        if (gameWorld.getEntitiesByType(EntityType.OBJECT).size() < amount_FO) {
            Random random = new Random();
            var range = STREET_RIGHT_END - random.nextDouble(0, STREET_RIGHT_END-STREET_LEFT_END);
            gameWorld.spawn("OBJECT", range, 0);
            System.out.println("Test: "+gameWorld.getEntitiesByType(EntityType.OBJECT).size()+"/"+amount_FO);

        }
    }
}
