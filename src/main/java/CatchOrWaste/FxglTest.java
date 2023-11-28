package CatchOrWaste;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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

    private static final int SPEED=5;
    private static final double PLAYERSIZE=3000*0.035;
    private Entity player, object;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
    }

    @Override
    protected void initInput() {

        //check for Key Inputs
        onKey(KeyCode.RIGHT,"Move Right", ()-> {
            if(getPlayer().getX()<getAppWidth()*0.85-PLAYERSIZE){
                getPlayer().translateX(SPEED);
                changeDirection("Wegwerfpolizist_R.png");
            }
        });

        onKey(KeyCode.LEFT,"Move Left", ()->{
            if(getPlayer().getX()>0){
                getPlayer().translateX(SPEED*(-1));
                changeDirection("Wegwerfpolizist_L.png");
            }
        });

        onKey(KeyCode.F,"F", ()->{
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            System.out.println(screenBounds);
        });


    }

    @Override
    protected void initGame() {

        //Background
        //create Entity which displays the background image as a Texture
        Texture texture = new Texture(getAssetLoader().loadImage("Streets.png"));
        texture.setFitWidth(getAppWidth());
        texture.setFitHeight(getAppHeight());
        Entity entity = new Entity();
        entity.getViewComponent().addChild(texture);
        entity.setPosition(0,0);

        //add Entities to GameWorld
        getGameWorld().addEntity(entity);
        getGameWorld().addEntityFactory(new SimpleFactory());

        //spawn the player from the factory
        player = spawn("PLAYER",100,getAppHeight()*0.75);//0.78
        object = spawn("OBJECT",200,0);



    }

    @Override
    protected void onUpdate(double tpf) {
        if(getPlayer().getX()>getAppWidth()*0.85-PLAYERSIZE){
            getPlayer().setX(getAppWidth()*0.85-PLAYERSIZE);
        }
        else if (getPlayer().getX()==getAppWidth()*0.85-PLAYERSIZE) {
            changeDirection("Wegwerfpolizist_D.png");
        }
        else if(getPlayer().getX()<0){
            getPlayer().setX(0);
        }
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
