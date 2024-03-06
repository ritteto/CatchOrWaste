package code;

import code.model.enums.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;

import static code.FxglTest.fallingObjects;
import static code.Variables.*;
import static code.model.Constants.Constants.*;
import static code.model.PlayerModel.playerSpeed;
import static code.view.CartView.spawnCart;


public class Wegwerfpolizist {

    private final Entity entity;
    private Entity catchedEntity;
    private boolean full, direction; //left = true, right = false

    public Wegwerfpolizist(Entity entity) {
        this.entity = entity;
        this.full = false;
        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(2400*0.035,1951*0.035)));
    }

    public void playerOnUpdate(Cart cart, GameWorld gameWorld){
        boundaries();
        catchObject();
        isAtRightEnd(cart, gameWorld);
        isAtLeftEnd(cart, gameWorld);
    }

    public void move(String move){
        setImage(move);
        movePlayer(move);
    }

    public double getX(){
        return this.entity.getX();
    }

    public double getY(){
        return this.entity.getY();
    }

    public void setX(double x){
        entity.setX(x);
    }

    public void setY(double y){
        entity.setY(y);
    }

    public double getWidth(){
        return entity.getWidth();
    }
    public double getHeight(){
        return entity.getHeight();
    }

    public boolean isFull(){
        return this.full;
    }

    public void setFull(Boolean full){
        this.full = full;
    }

    public boolean getDirection(){
        return this.direction;
    }

    public void setDirection(Boolean direction){
        this.direction = direction;
    }

    private void catchObject(){
        if(this.catchedEntity == null){
            for(FallingObject object: fallingObjects) {
                if(object != null && !object.isCatched()
                        && object.getEntity().getY()+60 >this.entity.getY()
                        && object.getEntity().getY() < this.entity.getY() + PLAYERSIZE
                        && object.getEntity().getX()-30 > this.entity.getX()
                        && object.getEntity().getX()-30 < this.entity.getX() + PLAYERSIZE
                ){
                    object.getEntity().removeComponent(ProjectileComponent.class);
                    this.catchedEntity = object.getEntity();
                    object.setCatched(true);
                    break;


                }else if(object!= null && object.isCatched() ){
                    if(this.direction){
                        object.getEntity().setX(this.entity.getX());
                        object.getEntity().setY(this.entity.getY());
                    }else{
                        object.getEntity().setX(this.entity.getX()*0.75);
                        object.getEntity().setY(this.entity.getY());
                    }
                }
            }
        } else{
                if(this.direction){
                    this.catchedEntity.setX(this.entity.getX()+PLAYERSIZE/5*3);
                    this.catchedEntity.setY(this.entity.getY());
                }else{
                    this.catchedEntity.setX(this.entity.getX()+PLAYERSIZE*1.2);
                    this.catchedEntity.setY(this.entity.getY());
                }
        }
    }

    private void boundaries(){
        //Boundary right
        if(this.entity.getX() > PLAYER_RIGHT){
            this.entity.setX(PLAYER_RIGHT);

        //Boundary left
        }else if(this.entity.getX()<PLAYER_LEFT){
            this.entity.setX(PLAYER_LEFT);
        }
    }

    private void isAtRightEnd(Cart cart, GameWorld gameWorld){
        if(this.entity.getX() == PLAYER_RIGHT){
            setImage("Down_R");
            releaseEntity(cart, gameWorld, false);
        }
    }

    private void isAtLeftEnd(Cart cart, GameWorld gameWorld){
        if(this.entity.getX() <= PLAYER_LEFT){
            setImage("Down_L");
            releaseEntity(cart, gameWorld, true);
        }
    }

    //move Player to the given direction
    private void movePlayer(String direction){
        switch (direction){
            case "Left":
                this.entity.translateX(-playerSpeed);
                break;
            case "Right":
                this.entity.translateX(playerSpeed);
                break;
        }
    }

    //change the image of the Player according to the activity it is doing
    private void setImage(String move){
        switch (move){
            case "Left":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("player/wegwerfpolizist_l_resized.png")));
                this.direction = true;
                break;
            case "Right":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("player/wegwerfpolizist_r_resized.png")));
                this.direction = false;
                break;
            case "Down_R":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("player/wegwerfpolizist_d_r_resized.png")));
                break;
            case "Down_L":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("player/wegwerfpolizist_d_l_resized.png")));
                break;
        }
    }

    public void releaseEntity(Cart cart, GameWorld gameWorld, Boolean left){
        if(this.catchedEntity != null){
            for (FallingObject object : fallingObjects) {
                if(object != null && object.isCatched()){
                    object.setCatched(false);
                }
            }
            for (int i=0; i<fallingObjects.length; i++) {
                if( fallingObjects[i] != null && this.catchedEntity.equals(fallingObjects[i].getEntity())){
                    fallingObjects[i] = null;
                }
            }

            spawnCart(gameWorld,STREET_RIGHT_END, STREET_HEIGHT,catchedEntity);
            //cart.spawn(gameWorld, left, catchedEntity);

            this.catchedEntity.setType(EntityType.OBJECT_CART);
            this.catchedEntity.setVisible(false);
            this.catchedEntity = null;

        }
    }
}
