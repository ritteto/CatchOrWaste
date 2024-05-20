package catchorwaste.view;

import com.almasb.fxgl.entity.GameWorld;

import java.util.Random;

import static catchorwaste.model.FallingObjectModel.getGameStartTime;
import static catchorwaste.model.SettingsModel.*;
import static catchorwaste.model.constants.Constants.lastSpawnTime;
import static catchorwaste.model.constants.Constants.HOUSES;
import static catchorwaste.model.constants.Constants.HOUSE_Y;

public class FallingObjectView {

    public static void spawnObjects(GameWorld gameWorld) {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastSpawn = currentTime - lastSpawnTime;
        double secondsSinceLastSpawn = timeSinceLastSpawn / 1000.0;

        //start slow
        if(currentTime - getGameStartTime() < 15_000){
            setItemsPerSecond(0.3);
        }else if(currentTime - getGameStartTime() < 25_000){
            setItemsPerSecond(0.4);
        }else{
            updateDifficulty();
        }

        if (secondsSinceLastSpawn >= 1.0 / getItemsPerSecond()) {
            Random random = new Random();
            gameWorld.spawn("OBJECT",HOUSES[random.nextInt(HOUSES.length)]-32+50,HOUSE_Y + 70);
            lastSpawnTime = currentTime; // Aktualisiere die letzte Erzeugungszeit
        }
    }

}
