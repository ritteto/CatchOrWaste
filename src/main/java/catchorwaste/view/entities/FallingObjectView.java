package catchorwaste.view.entities;

import catchorwaste.controller.screens.SettingsController;
import catchorwaste.model.entities.FallingObjectModel;
import com.almasb.fxgl.entity.SpawnData;


import java.util.Random;

import static catchorwaste.model.variables.Constants.HOUSES;
import static catchorwaste.model.variables.Constants.HOUSE_Y;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class FallingObjectView {

    private long lastSpawnTime = System.currentTimeMillis();
    private final SettingsController settingsController;
    private final FallingObjectModel fallingObjectModel;

    public FallingObjectView(FallingObjectModel fallingObjectModel,SettingsController settingsController){
        this.fallingObjectModel = fallingObjectModel;
        this.settingsController = settingsController;
    }

    public void spawnObjects() {
        long currentTime = System.currentTimeMillis();
        long timeSinceLastSpawn = currentTime - lastSpawnTime;
        double secondsSinceLastSpawn = timeSinceLastSpawn / 1000.0;

        //start slow
        if(currentTime - fallingObjectModel.getGameStartTime() < 15_000){
            settingsController.setItemsPerSecond(0.3);
        }else if(currentTime - fallingObjectModel.getGameStartTime() < 25_000){
            settingsController.setItemsPerSecond(0.4);
        }else{
            settingsController.updateDifficulty();
        }

        if (secondsSinceLastSpawn >= 1.0 / settingsController.getItemsPerSecond()) {
            Random random = new Random();
            getGameWorld().spawn("OBJECT",
                    new SpawnData(HOUSES[random.nextInt(HOUSES.length)]-32+50,HOUSE_Y + 70)
                            .put("fallingSpeedRange", settingsController.getFallingSpeedRange()));
            lastSpawnTime = currentTime; // Aktualisiere die letzte Erzeugungszeit
        }
    }

}
