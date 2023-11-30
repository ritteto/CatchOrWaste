package CatchOrWaste;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;


import java.io.NotActiveException;

import static com.almasb.fxgl.dsl.FXGL.*;

public class FxglTest extends GameApplication {

    private static final int SPEED=5;
    private static final double PLAYERSIZE=3000*0.035;
    private boolean weiche, distance;
    private Entity player, object, cart1, background_1;

    @Override
    protected void initSettings(GameSettings settings) {
        //settings.setFullScreenAllowed(true);
        //settings.setFullScreenFromStart(true);
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

        onKeyDown(KeyCode.G,"G",()->{
            spawn("CART",getAppWidth()*0.77,getAppHeight()*0.78);
        });


        onKey(KeyCode.DIGIT1,"1", ()->{
            weiche = true;
        });

        onKey(KeyCode.DIGIT2,"2", ()->{
            weiche = false;
        });

    }

    @Override
    protected void initGame() {


        //Background
        //create Entity which displays the background image as a Texture
        Texture texture = new Texture(getAssetLoader().loadImage("background_bad.png"));
        Texture texture2 = new Texture(getAssetLoader().loadImage("Streets.png"));
        texture.setFitWidth(getAppWidth());
        texture2.setFitWidth(getAppWidth());
        texture.setFitHeight(getAppHeight());
        texture2.setFitHeight(getAppHeight());
        Entity entity = new Entity();
        Entity entity2 = new Entity();
        entity.setType(EntityType.BACKGROUND);
        entity2.setType(EntityType.BACKGROUND);
        entity.getViewComponent().addChild(texture);
        entity2.getViewComponent().addChild(texture2);
        entity.setPosition(0,0);
        entity2.setPosition(0,0);

        //add Entities to GameWorld
        getGameWorld().addEntity(entity);
        getGameWorld().addEntity(entity2);
        getGameWorld().addEntityFactory(new SimpleFactory());

        //spawn the player from the factory
        player = spawn("PLAYER",100,getAppHeight()*0.75);//0.78
        object = spawn("OBJECT",200,0);

        //cart = spawn("CART",getAppWidth()*0.77,getAppHeight()*0.78);



    }

    @Override
    protected void onUpdate(double tpf) {
        if(!getGameWorld().getEntitiesByType(EntityType.OBJECT).isEmpty()){
            for (Entity entity : getGameWorld().getEntitiesByType(EntityType.OBJECT)) {
                if(entity.getY()>=getAppHeight()){
                    entity.removeFromWorld();
                }
            }
        }

        if(!getGameWorld().getEntitiesByType(EntityType.CART).isEmpty()){
            for (Entity cart:getGameWorld().getEntitiesByType(EntityType.CART)){
                if (cart.getY() >= getAppHeight() * 0.785) {
                    if (cart.getX() < getAppWidth() * 0.836) {
                        cart.setX(cart.getX() + 0.4);
                    }else{
                        cart.setX(cart.getX()+1);
                    }
                    if(cart.getX() >= getAppWidth() * 0.836){
                        cart.getViewComponent().clearChildren();
                        cart.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_vertical.png")));
                        cart.setY(cart.getY()-1);
                    }

                } else if(cart.getY()<=getAppHeight()*0.785 && cart.getY()>getAppHeight()*0.59){
                    cart.setY(cart.getY()-1);

                }else if(cart.getY()==getAppHeight()*0.59){
                    cart.getViewComponent().clearChildren();
                    cart.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_horizontal.png")));
                    if(cart.getX()>getAppWidth()*0.78 && weiche){
                        cart.setX(cart.getX()-1);
                    }else if(cart.getX()<getAppWidth()*0.89 && !weiche){
                        cart.setX(cart.getX()+1);
                    }
                    if(cart.getX()>getAppWidth()*0.885 || cart.getX()<getAppWidth()*0.78){
                        cart.setY(cart.getY()-1);
                    }

                }else if(cart.getY()<getAppHeight()*0.59 && cart.getY()>getAppHeight()*0.16){
                    cart.getViewComponent().clearChildren();
                    cart.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_vertical.png")));
                    cart.setY(cart.getY()-1);

                }else if(cart.getY()==getAppHeight()*0.16 && cart.getX() > getAppWidth()*0.1){
                    cart.getViewComponent().clearChildren();
                    cart.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_horizontal.png")));
                    cart.setX(cart.getX()-1);
                }else if(cart.getY()==getAppHeight()*0.16 && cart.getX() <= getAppWidth()*0.1){
                    cart.removeFromWorld();
                }
            }
        }



        if(getPlayer().getX()>getAppWidth()*0.85-PLAYERSIZE){
            getPlayer().setX(getAppWidth()*0.85-PLAYERSIZE);
        }
        else if (getPlayer().getX()==getAppWidth()*0.85-PLAYERSIZE) {
            changeDirection("wegwerfpolizist_d.png");
            if(getGameWorld().getEntitiesByType(EntityType.CART).isEmpty()){
                spawn("CART",getAppWidth()*0.78,getAppHeight()*0.785);
            }else{
                for (Entity cart : getGameWorld().getEntitiesByType(EntityType.CART)) {
                    if(cart.getY()>=getAppHeight()*0.785 && cart.getX()<getAppWidth()*0.77+30){
                        distance=false;
                    }else{
                        distance=true;
                    }
                }
            }
            if(distance){
                spawn("CART",getAppWidth()*0.78,getAppHeight()*0.785);
            }
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
