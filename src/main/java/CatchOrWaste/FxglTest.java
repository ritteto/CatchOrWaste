package CatchOrWaste;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import com.pi4j.platform.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Screen;

import static com.almasb.fxgl.dsl.FXGL.*;

public class FxglTest extends GameApplication {

    private static final int SPEED=20;
    private static final double PLAYERSIZE=3000*0.035;
    private Entity player;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setHeight(720);
        settings.setWidth(720/4*5);
    }

    @Override
    protected void initGame() {

        getGameWorld().addEntityFactory(new SimpleFactory());
        getGameScene().setBackgroundRepeat("Streets.png");
        player = spawn("PLAYER",100,getAppHeight()*0.78);

    }

    @Override
    protected void initInput() {
        onKey(KeyCode.RIGHT,"Move Right", ()-> {
            if(getPlayer().getX()<getAppWidth()*0.85-PLAYERSIZE){
                getPlayer().translateX(SPEED);
                changeDirection("Wegwerfpolizist_R.png");
                System.out.println(player.getX());
            }else{
                getPlayer().setX(getAppWidth()*0.85-PLAYERSIZE);
            }
        });
        onKey(KeyCode.LEFT,"Move Left", ()->{
            if(getPlayer().getX()>0){
                getPlayer().translateX(SPEED*(-1));
                changeDirection("Wegwerfpolizist_L.png");
            }else{
                getPlayer().setX(0);
            }
        });

        onKey(KeyCode.F,"F", ()->{
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            System.out.println(screenBounds);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    private static Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER);
    }

    public void changeDirection(String view){
        ViewComponent viewComponent = getPlayer().getComponent(ViewComponent.class);
        Texture texture = new Texture(getAssetLoader().loadImage(view));
        viewComponent.clearChildren();
        viewComponent.addChild(texture);
    }
}
