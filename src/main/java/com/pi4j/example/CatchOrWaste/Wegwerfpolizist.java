package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.pi4j.example.CatchOrWaste.Variables.*;

public class Wegwerfpolizist {

    private final Entity entity;
    public Wegwerfpolizist(Entity entity) {
        this.entity = entity;
        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(3000*0.035,3000*0.035)));
    }

    public void playerOnUpdate(Cart cart, GameWorld gameWorld){
        collide(gameWorld);
        boundaries();
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

    private void boundaries(){

        //Boundary right
        if(this.entity.getX() > PLAYER_RIGHT){
            this.entity.setX(PLAYER_RIGHT);

        //Boundary left
        }else if(this.entity.getX()<PLAYER_LEFT){
            this.entity.setX(PLAYER_LEFT);
        }
    }

    public void collide(GameWorld gameWorld){
        for (Entity collision_entity :gameWorld.getEntitiesByType(EntityType.OBJECT)) {
            if(this.entity.isColliding(collision_entity)){
                System.out.println("HeyHo");
            }
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
