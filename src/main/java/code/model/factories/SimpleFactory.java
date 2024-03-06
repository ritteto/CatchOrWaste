package code.model.factories;

import code.model.components.CargoComponent;
import code.model.components.ImageNameComponent;
import code.model.enums.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.fasterxml.jackson.databind.node.ValueNode;
import javafx.geometry.Point2D;

import java.util.Random;

import static code.model.PlayerModel.PLAYERSIZE;


public class SimpleFactory implements EntityFactory {

    public static double scale=0.115; //scale=0.035


    @Spawns("PLAYER")
    public Entity newPlayer(SpawnData data) {
        Entity entity = FXGL.entityBuilder(data)
                .view("player/wegwerfpolizist_R_resized.png")
                .scale(scale,scale)
                .type(EntityType.PLAYER)
                .build();

        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(PLAYERSIZE,PLAYERSIZE)));

        return entity;
    }

    @Spawns("OBJECT")
    public Entity newObject(SpawnData data) {
        Random random = new Random();
        String [] zufall = {"kleider.png", "iphone.png", "kaputte_lampe.png", "lampe_leuchtend.png", "kaputtes_iphone.png"};
        int zufallszahl = random.nextInt(zufall.length);
        String name = zufall[zufallszahl].replace(".png", "");
        return FXGL.entityBuilder(data)
                .view("fallingObjects/"+zufall [zufallszahl])
                .type(EntityType.OBJECT)
                .scale(0.07,0.07)
                .with(new ProjectileComponent(new Point2D(0,1),100))
                .with(new ImageNameComponent(name))
                .build();
    }

    @Spawns("Apfel")
    public Entity newApfel(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("fallingObjects/kleider.png")
                .type(EntityType.APFEL)
                .scale(0.05,0.05)
                .with(new ProjectileComponent(new Point2D(0,20),200))
                .build();
    }




    @Spawns("ZUFALL")
    public Entity newRandom(SpawnData data) {
        Random random = new Random();
        String [] zufall =
                {"kleider_gedreht.png", "iphone.png", "kaputte_lampe.png", "kaputtes_iphone.png"};
        int zufallszahl = random.nextInt(zufall.length);
        return FXGL.entityBuilder(data)
                .view("fallingObjects/"+zufall [zufallszahl])
                .type(EntityType.ZUFALL)
                .scale(0.07,0.07)
                .with(new ProjectileComponent(new Point2D(0,20),200))
                .build();
    }



    @Spawns("CART")
    public Entity newCart(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("carts/"+data.get("Name")+"_cart_horizontal.png")
                .with(new ImageNameComponent(data.get("Name")+"_cart"))
                .scale(0.12,0.12)
                .type(EntityType.CART)
                .build();
    }

    @Spawns("TEST")
    public Entity newTest(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("carts/"+data.get("CargoName")+"_cart_horizontal.png")
                .at(data.getX(),data.getY())
                .with(new ImageNameComponent(data.get("CargoName")))
                .with(new CargoComponent(data.get("Cargo")))
                .with(new ProjectileComponent(new Point2D(1,0), data.get("Speed")))
                .scale(0.12,0.12)
                .type(EntityType.TEST)
                .build();
    }


    @Spawns("HOUSE1")
    public Entity newHouse1(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("structures/House1.png")
                .type(EntityType.HOUSE1)
                .scale(0.14,0.14)
                .build();
    }

    @Spawns("HOUSE2")
    public Entity newHouse2(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("structures/House2.png")
                .type(EntityType.HOUSE2)
                .scale(0.14,0.14)
                .build();
    }

    @Spawns("HOUSE3")
    public Entity newHouse3(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("structures/House3.png")
                .type(EntityType.HOUSE3)
                .scale(0.14,0.14)
                .build();
    }

    @Spawns("BACKGROUND")
    public Entity newBackground(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("backgrounds/background_bad.png")
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
                .view("structures/reparieren.png")
                .type(EntityType.REPAIR)
                .scale(0.05,0.05)
                .zIndex(10)
                .build();
    }

    @Spawns("MARKET")
    public Entity newMarket(SpawnData data) {

        return FXGL.entityBuilder(data)
                .view("structures/weitergeben.png")
                .type(EntityType.MARKET)
                .scale(0.05,0.05)
                .zIndex(10)
                .build();

    }

    @Spawns("RECYCLING")
    public Entity newRecycling(SpawnData data) {

        return FXGL.entityBuilder(data)
                .view("structures/recyclestation.png")
                .type(EntityType.RECYCLING)
                .scale(0.05,0.05)
                .zIndex(10)
                .build();
    }


}

