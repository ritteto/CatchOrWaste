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
    private Entity playerEntity, cart1, background_1, house, house2, house3, house4;
    private Entity[] houses;

    public static double AppWidth;
    public static double AppHeight;

    Player player;
    Cart cart;

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
                player.move("Right");
            }
        });

        onKey(KeyCode.LEFT,"Move Left", ()->{
            if(getPlayer().getX()>0){
                player.move("Left");
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

        onKeyDown(KeyCode.F,"F", ()->{
            cart.spawn(getGameWorld());
        });

    }

    @Override
    protected void initGame() {

        AppWidth = getAppWidth();
        AppHeight = getAppHeight();

        getGameWorld().addEntityFactory(new SimpleFactory());

        Entity background1 = spawn("BACKGROUND",0,0);
        Entity background2 = spawn("BACKGROUND",0,0);
        setBackground(background1, "background_bad.png");
        setBackground(background2, "streets.png");

        //spawn the player from the factory
        Entity playerEntity = spawn("PLAYER",100,getAppHeight()*0.76);
        player = new Player(playerEntity);
        cart = new Cart();

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

        if(player != null){
            if(player.playerOnUpdate()){
                cart.spawn(getGameWorld());
            }
        }


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


        /*
         if (getPlayer().getX()==getAppWidth()*0.85-PLAYERSIZE) {

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

         */
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static Entity getPlayer() {
        return getGameWorld().getSingleton(EntityType.PLAYER);
    }

    public void setImage(Entity entity,String view){
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(new Texture(getAssetLoader().loadImage(view)));
    }

    public void setBackground(Entity entity, String view){
        Texture textureFromView = new Texture(getAssetLoader().loadImage(view));
        textureFromView.setFitWidth(AppWidth);
        textureFromView.setFitHeight(AppHeight);
        entity.getViewComponent().clearChildren();
        entity.getViewComponent().addChild(textureFromView);
    }
}
