package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;

import static com.pi4j.example.CatchOrWaste.Variables.*;

public class Wegwerfpolizist {

    private final Entity entity;
    private Entity catchedEntity;
    private boolean full, direction; //left = true, right = false

    public Wegwerfpolizist(Entity entity) {
        this.entity = entity;
        this.full = false;
        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(2400*0.035,1951*0.035)));
    }

    public void playerOnUpdate(Cart cart, GameWorld gameWorld, FallingObject[] fallingObjects){
        boundaries();
        detectCollision(fallingObjects);
        isAtRightEnd(fallingObjects, gameWorld, cart);
        isAtLeftEnd(fallingObjects);
    }

    public void move(String move){
        setImage(move);
        movePlayer(move);
    }

    public double getX(){
        return entity.getX();
    }

    public double getY(){
        return entity.getX();
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

    private void detectCollision(FallingObject[] fallingObjects){
        if(!this.full){
            for (FallingObject object: fallingObjects) {
                if(object != null && !object.isCatched()  && !this.full
                        && object.getEntity().getY()>this.entity.getY()
                        && object.getEntity().getY() < this.entity.getY() + PLAYERSIZE
                        && object.getEntity().getX() > this.entity.getX()
                        && object.getEntity().getX() < this.entity.getX() + PLAYERSIZE
                ){
                    object.getEntity().removeComponent(ProjectileComponent.class);
                    object.setCatched(true);

                }else if(object!= null && object.isCatched() ){
                    if(this.direction){
                        object.getEntity().setX(this.entity.getX());
                        object.getEntity().setY(this.entity.getY());
                    }else{
                        object.getEntity().setX(this.entity.getX());
                        object.getEntity().setY(this.entity.getY());
                    }
                    this.catchedEntity = object.getEntity();
                    this.full = true;
                }
            }
        } else{
            if(this.catchedEntity!=null){
                if(this.direction){
                    this.catchedEntity.setX(this.entity.getX()+PLAYERSIZE/2);
                    this.catchedEntity.setY(this.entity.getY());
                }else{
                    this.catchedEntity.setX(this.entity.getX()+PLAYERSIZE);
                    this.catchedEntity.setY(this.entity.getY());
                }
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

    private void isAtRightEnd(FallingObject[] fallingObjects, GameWorld gameWorld, Cart cart){
        if(this.entity.getX() == PLAYER_RIGHT){
            setImage("Down_R");
            if(this.full){
                for (FallingObject object : fallingObjects) {
                   if(object != null && object.isCatched()){
                       object.setCatched(false);
                   }
                }
                this.catchedEntity.removeFromWorld();
                this.catchedEntity = null;
                this.full = false;
                cart.spawn(gameWorld);
            }

        }
    }

    private boolean isAtLeftEnd(FallingObject[] fallingObjects){
        if(this.entity.getX() <= PLAYER_LEFT){
            setImage("Down_L");
            if(this.full){
                for (FallingObject object : fallingObjects) {
                    if(object != null && object.isCatched()){
                        object.setCatched(false);
                    }
                }
                this.catchedEntity.removeFromWorld();
                this.catchedEntity = null;
                this.full = false;
                System.out.println("set rightEnd");
            }
            return true;
        }else{
            return false;
        }
    }

    //move Player to the given direction
    private void movePlayer(String direction){
        switch (direction){
            case "Left":
                this.entity.translateX(-PLAYER_SPEED);
                break;
            case "Right":
                this.entity.translateX(PLAYER_SPEED);
                break;
        }
    }

    //change the image of the Player according to the activity it is doing
    private void setImage(String move){
        switch (move){
            case "Left":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("wegwerfpolizist_l_resized.png")));
                this.direction = true;
                break;
            case "Right":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("wegwerfpolizist_r_resized.png")));
                this.direction = false;
                break;
            case "Down_R":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("wegwerfpolizist_d_r_resized.png")));
                break;
            case "Down_L":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("wegwerfpolizist_d_l_resized.png")));
                break;
        }
    }

}
