package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class SimpleFactory implements EntityFactory {

    public static double scale=0.035; //scale=0.035


    @Spawns("PLAYER")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("wegwerfpolizist_l.png")
                .scale(scale,scale)
                .type(EntityType.PLAYER)
                .build();
    }

    @Spawns("OBJECT")
    public Entity newObject(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("apple.png")
                .type(EntityType.OBJECT)
                .scale(0.01,0.01)
                .with(new ProjectileComponent(new Point2D(0,1),50))
                .build();
    }

    @Spawns("Apfel")
    public Entity newApfel(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("kleider.png")
                .type(EntityType.APFEL)
                .scale(0.05,0.05)
                .with(new ProjectileComponent(new Point2D(0,20),200))
                .build();
    }




    @Spawns("ZUFALL")
    public Entity newRandom(SpawnData data) {
        Random random = new Random();
        String [] zufall = {"kleider.png", "iphonr.png", "kaputte lampe.png", "kaputtes iphone.png"};
        int zufallszahl = random.nextInt(zufall.length);
        return FXGL.entityBuilder(data)
                .view(zufall [zufallszahl])
                .type(EntityType.APFEL)
                .scale(0.05,0.05)
                .with(new ProjectileComponent(new Point2D(0,20),200))
                .build();
    }



    @Spawns("CART")
    public Entity newCart(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("cart_horizontal.png")
                .scale(0.03,0.03)
                .type(EntityType.CART)
                .build();
    }


    @Spawns("HOUSE")
    public Entity newHouse(SpawnData data) {
        Random random = new Random();
        String [] zufall = {"House1.png", "House2.png", "House3.png"};
        int zufallszahl = random.nextInt(zufall.length);
        return FXGL.entityBuilder(data)
                .view(zufall [zufallszahl])
                .type(EntityType.HOUSE)
                .scale(0.04,0.04)
                .build();
    }

    @Spawns("BACKGROUND")
    public Entity newBackground(SpawnData data) {
         return FXGL.entityBuilder(data)
                .view("background_bad.png")
                .type(EntityType.BACKGROUND)
                .build();
    }




}
