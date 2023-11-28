package CatchOrWaste;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;

import static com.almasb.fxgl.dsl.FXGL.*;

public class FxglTest extends GameApplication {

    private static final int SPEED=20;
    private static final double PLAYERSIZE=3000*0.035;

    @Override
    protected void initSettings(GameSettings settings) {

    }

    @Override
    protected void initInput() {
        onKey(KeyCode.D,"Move Right", ()-> {
            if(getPlayer().getX()<getAppWidth()-PLAYERSIZE){
                getPlayer().translateX(SPEED);
            }else{
                getPlayer().setX(getAppWidth()-PLAYERSIZE);
            }
        });
        onKey(KeyCode.A,"Move Left", ()->{
            if(getPlayer().getX()>0){
                getPlayer().translateX(SPEED*(-1));
            }else{
                getPlayer().setX(0);
            }
        });
    }

    @Override
    protected void initGame() {

        getGameWorld().addEntityFactory(new SimpleFactory());

        spawn("WP",100,getAppHeight()*0.8);

    }

    public static void main(String[] args) {
        launch(args);
    }

    private static Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER);
    }
}
