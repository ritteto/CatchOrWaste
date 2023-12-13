package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionResult;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.physics.SAT.isColliding;
import static com.pi4j.example.CatchOrWaste.Variables.*;

public class Wegwerfpolizist {

    private final Entity entity;
    private String direction;

    public Wegwerfpolizist(Entity entity) {
        this.entity = entity;
        this.direction = "Right";
        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(2400*0.035,1951*0.035)));
    }

    public void playerOnUpdate(Cart cart, GameWorld gameWorld, FallingObject[] fallingObjects){
        boundaries();
        detectCollision(fallingObjects);
        if (isAtRightEnd()){
            cart.spawn(gameWorld);
        }
        isAtLeftEnd();
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

    public String getDirection(){
        return this.direction;
    }

    public void setDirection(String direction){
        this.direction = direction;
    }

    private boolean detectCollision(FallingObject[] fallingObjects){
        //System.out.println("Player: "+this.entity.getX()+this.entity.getY());
        for (FallingObject object: fallingObjects) {
            //System.out.println("Entity: "+en.getX()+"/"+en.getY());
            if(this.direction.equals("Right")){
                if(object != null && object.getEntity().getY()>this.entity.getY()
                        && object.getEntity().getY() < this.entity.getY() + PLAYERSIZE
                        && object.getEntity().getX() > this.entity.getX()
                        && object.getEntity().getX() < this.entity.getX() + PLAYERSIZE
                ){
                    object.getEntity().removeComponent(ProjectileComponent.class);
                    object.isCatched(true);

                }
            }
        }

        return true;
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

    private boolean isAtRightEnd(){
        if(this.entity.getX() == PLAYER_RIGHT){
            setImage("Down_R");
            return true;
        }else{
            return false;
        }
    }

    private boolean isAtLeftEnd(){
        if(this.entity.getX() <= PLAYER_LEFT){
            setImage("Down_L");
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
                break;
            case "Right":
                this.entity.getViewComponent().clearChildren();
                this.entity.getViewComponent().addChild(new Texture(FXGL.getAssetLoader().loadImage("wegwerfpolizist_r_resized.png")));
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
