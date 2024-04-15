package CatchOrWaste.view;

import com.almasb.fxgl.entity.GameWorld;

import java.util.Random;

import static CatchOrWaste.model.Constants.Constants.*;

public class FallingObjectView {

    public static void spawnObjects(GameWorld gameWorld) {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastSpawn = currentTime - lastSpawnTime;
        double secondsSinceLastSpawn = timeSinceLastSpawn / 1000.0;

        if (secondsSinceLastSpawn >= 1.0 / itemsPerSecond) {
                Random random = new Random();
                gameWorld.spawn("OBJECT",HOUSES[random.nextInt(HOUSES.length)]+100,HOUSE_Y + 70);
            lastSpawnTime = currentTime; // Aktualisiere die letzte Erzeugungszeit
        }
    }

    public static void setItemsPerSecond(double newRate) {
        itemsPerSecond = newRate;
    }
}
