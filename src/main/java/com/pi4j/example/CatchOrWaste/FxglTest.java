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
    private boolean weiche, distance;
    private Entity player, object, cart1, background_1, house, house2, house3, house4;
    private Entity[] houses;

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
                setImage(getPlayer(),"wegwerfpolizist_r.png");
            }
        });

        onKey(KeyCode.LEFT,"Move Left", ()->{
            if(getPlayer().getX()>0){
                getPlayer().translateX(SPEED*(-1));
                setImage(getPlayer(),"wegwerfpolizist_l.png");
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
        Texture background = new Texture(getAssetLoader().loadImage("background_bad.png"));
        Texture background2 = new Texture(getAssetLoader().loadImage("Streets.png"));
        background.setFitWidth(getAppWidth());
        background2.setFitWidth(getAppWidth());
        background.setFitHeight(getAppHeight());
        background2.setFitHeight(getAppHeight());
        Entity entity = new Entity();
        Entity entity2 = new Entity();
        entity.setType(EntityType.BACKGROUND);
        entity2.setType(EntityType.BACKGROUND);
        entity.getViewComponent().addChild(background);
        entity2.getViewComponent().addChild(background2);
        entity.setPosition(0,0);
        entity2.setPosition(0,0);

        //add Entities to GameWorld
        getGameWorld().addEntity(entity);
        getGameWorld().addEntity(entity2);
        getGameWorld().addEntityFactory(new SimpleFactory());

        //spawn the player from the factory
        player = spawn("PLAYER",100,getAppHeight()*0.76);
        //object = spawn("OBJECT",200,0);

        house = spawn("HOUSE",0,getAppHeight()*0.35);
        house2 = spawn("HOUSE",getAppWidth()*0.12,getAppHeight()*0.35);
        house3 = spawn("HOUSE",getAppWidth()*0.24,getAppHeight()*0.35);
        house4 = spawn("HOUSE",getAppWidth()*0.41,getAppHeight()*0.35);

        houses = new Entity[]{house,house2,house3,house4};
        String[] houseTextures = {"House1.png", "House2.png","House3.png"};

        for (Entity entity1 : houses) {
            int i = random(0,2);
            setImage(entity1,houseTextures[i]);
        }


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
                        cart.setX(cart.getX() + 1);
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
                    if(cart.getX()>getAppWidth()*0.885 || cart.getX()<getAppWidth()*0.79){
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
            setImage(getPlayer(),"wegwerfpolizist_d.png");
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

    public void setImage(Entity entity,String view){
        //ViewComponent viewComponent = entity.getViewComponent();
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage(view)));
    }
}
