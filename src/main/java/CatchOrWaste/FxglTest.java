package CatchOrWaste;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.application.Platform;
import javafx.geometry.Point2D;
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
    private Entity player, object, cart;

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
                changeDirection("wegwerfpolizist_r.png");
            }
        });

        onKey(KeyCode.LEFT,"Move Left", ()->{
            if(getPlayer().getX()>0){
                getPlayer().translateX(SPEED*(-1));
                changeDirection("wegwerfpolizist_l.png");
            }
        });

        onKey(KeyCode.F,"F", ()->{
            cart.addComponent(new ProjectileComponent(new Point2D(1,0),30));
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
        cart = spawn("CART",getAppWidth()*0.77,getAppHeight()*0.78);



    }

    @Override
    protected void onUpdate(double tpf) {
        /*System.out.println("Cart: "+cart.getX()+"/"+cart.getY());
        System.out.println("Width: "+cart);
        while(cart.getX()<=getAppWidth()*0.835){
            cart.translateX(30);
        }

        /*if(cart.getY()>=getAppHeight()*0.78){
            if(cart.getX()<=getAppWidth()*0.835){
                cart.addComponent(new ProjectileComponent(new Point2D(1,0),20));
            }else{
                cart.translateX(30);

            }
        }
        /*else if(cart.getY()<getAppHeight()*0.78 && cart.getY()>getAppHeight()*0.65){
            cart.rotateBy(90);
            cart.getViewComponent().clearChildren();
            cart.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_vertical.png")));
            cart.removeComponent(ProjectileComponent.class);
            cart.addComponent(new ProjectileComponent(new Point2D(0,-1),20));
        }

         */


        if(getPlayer().getX()>getAppWidth()*0.85-PLAYERSIZE){
            getPlayer().setX(getAppWidth()*0.85-PLAYERSIZE);
        }
        else if (getPlayer().getX()==getAppWidth()*0.85-PLAYERSIZE) {
            changeDirection("wegwerfpolizist_d.png");
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
