package Teaching;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;

public class SimpleGame extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(100);
        gameSettings.setHeight(100);
    }

    public static void main(String[] args) {
        launch(args);
    }
}