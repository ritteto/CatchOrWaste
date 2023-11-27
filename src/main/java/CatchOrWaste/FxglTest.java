package CatchOrWaste;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

public class FxglTest extends GameApplication {

    @Override
    protected void initSettings(GameSettings settings) {

    }

    @Override
    protected void initInput() {
        onKeyDown(KeyCode.F, () -> {
            getNotificationService().pushNotification("Hello world");
        });
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new SimpleFactory());

        spawn("WP",100,100);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
