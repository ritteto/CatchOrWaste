package com.pi4j.example.CatchOrWaste;

import com.almasb.fxgl.app.scene.GameView;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PolygonShapeData;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.pi4j.example.CatchOrWaste.Variables.PLAYERSIZE;
import static com.pi4j.example.CatchOrWaste.Variables.STREET_RIGHT;
import static java.lang.Math.E;
import static java.lang.Math.random;

public class SimpleFactory implements EntityFactory {

    public static double scale=0.035; //scale=0.035


    @Spawns("PLAYER")
    public Entity newPlayer(SpawnData data) {
        Entity entity = FXGL.entityBuilder(data)
                .view("wegwerfpolizist_R_resized.png")
                .scale(scale,scale)
                .type(EntityType.PLAYER)
                .build();

        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(PLAYERSIZE,PLAYERSIZE)));

        return entity;
    }

    @Spawns("OBJECT")
    public Entity newObject(SpawnData data) {
        Random random = new Random();
        String [] zufall = {"kleider.png", "iphonr.png", "kaputte lampe.png", "kaputtes iphone.png"};
        int zufallszahl = random.nextInt(zufall.length);
        return FXGL.entityBuilder(data)
                .view(zufall [zufallszahl])
                .type(EntityType.OBJECT)
                .scale(0.02,0.02)
                .with(new ProjectileComponent(new Point2D(0,1),100))
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
                .type(EntityType.ZUFALL)
                .scale(0.02,0.02)
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


    @Spawns("HOUSE1")
    public Entity newHouse1(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("House1.png")
                .type(EntityType.HOUSE1)
                .scale(0.04,0.04)
                .build();
    }

    @Spawns("HOUSE2")
    public Entity newHouse2(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("House2.png")
                .type(EntityType.HOUSE2)
                .scale(0.04,0.04)
                .build();
    }

    @Spawns("HOUSE3")
    public Entity newHouse3(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("House3.png")
                .type(EntityType.HOUSE3)
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

    @Spawns("MARTIN")
    public Entity newMartin(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("cool.png")
                .type(EntityType.ZUFALL)
                .build();
    }

    @Spawns("REPAIR")
    public Entity newRepair(SpawnData data) {

        return FXGL.entityBuilder(data)
                .view("reparieren.png")
                .type(EntityType.REPAIR)
                .scale(0.05,0.05)
                .zIndex(10)
                .build();
    }

    @Spawns("MARKET")
    public Entity newMarket(SpawnData data) {

        return FXGL.entityBuilder(data)
                .view("weitergeben.png")
                .type(EntityType.MARKET)
                .scale(0.05,0.05)
                .zIndex(10)
                .build();

    }

    @Spawns("RECYCLING")
    public Entity newRecycling(SpawnData data) {

        return FXGL.entityBuilder(data)
                .view("recyclestation.png")
                .type(EntityType.RECYCLING)
                .scale(0.05,0.05)
                .zIndex(10)
                .build();
    }


}

