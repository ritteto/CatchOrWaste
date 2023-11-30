package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

public class FxglTest extends GameApplication {

    private static final int SPEED=5;
    private static final double PLAYERSIZE=3000*0.035;
    private boolean weiche;
    private Entity player, object, cart, background_1;

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
            cart = spawn("CART",getAppWidth()*0.77,getAppHeight()*0.78);
            getGameWorld().addEntity(cart);

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
        if(cart != null) {
            System.out.println("Cart: "+cart.getX()+"/"+cart.getY());
            System.out.println(getAppHeight()*0.15);
            if (cart.getY() >= getAppHeight() * 0.78) {
                if (cart.getX() < getAppWidth() * 0.835) {
                    cart.setX(cart.getX() + 0.4);
                }else{
                    cart.setX(cart.getX()+1);
                }
                if(cart.getX() >= getAppWidth() * 0.835){
                    cart.getViewComponent().clearChildren();
                    cart.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_vertical.png")));
                    cart.setY(cart.getY()-1);
                }

            } else if(cart.getY()<=getAppHeight()*0.78 && cart.getY()>getAppHeight()*0.6){
                cart.setY(cart.getY()-1);

            }else if(cart.getY()==getAppHeight()*0.6){
                cart.getViewComponent().clearChildren();
                cart.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_horizontal.png")));
                if(cart.getX()>getAppWidth()*0.78 && weiche){
                    cart.setX(cart.getX()-1);
                }else if(cart.getX()<getAppWidth()*0.89 && !weiche){
                    cart.setX(cart.getX()+1);
                }
                if(cart.getX()>getAppWidth()*0.89 || cart.getX()<getAppWidth()*0.78){
                    System.out.println("Here");
                    cart.setY(cart.getY()-1);
                }

            }else if(cart.getY()<getAppHeight()*0.6 && cart.getY()>getAppHeight()*0.16){
                cart.getViewComponent().clearChildren();
                cart.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_vertical.png")));
                cart.setY(cart.getY()-1);
            }else if(cart.getY()==getAppHeight()*0.16 && cart.getX() > getAppWidth()*0.1){
                cart.getViewComponent().clearChildren();
                cart.getViewComponent().addChild(new Texture(getAssetLoader().loadImage("cart_horizontal.png")));
                cart.setX(cart.getX()-1);
            }
        }


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
