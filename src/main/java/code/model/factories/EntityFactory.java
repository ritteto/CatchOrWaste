package code.model.factories;

import code.model.components.*;
import code.model.enums.EntityType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;

import java.util.Random;

import static code.model.Constants.Constants.PLAYERSIZE;


public class EntityFactory implements com.almasb.fxgl.entity.EntityFactory {

    public static double scale=0.115;


    @Spawns("PLAYER")
    public Entity newPlayer(SpawnData data) {
        Entity entity = FXGL.entityBuilder(data)
                .view("player/wegwerfpolizist_R_resized.png")
                .with(new CargoComponent(null))
                .with(new PlayerDirectionComponent(true))
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
                .with(new IsCatchedComponent(false))
                .build();
    }


    @Spawns("CART")
    public Entity newCart(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("carts/"+data.get("CargoName")+"_cart_horizontal.png")
                .with(new ImageNameComponent(data.get("CargoName")))
                .with(new CartDirectionComponent(true))
                .scale(0.12,0.12)
                .type(EntityType.CART)
                .build();
    }



    @Spawns("HOUSE")
    public Entity newHouse(SpawnData data) {
        var houses = new String[]{"structures/House1.png","structures/House2.png","structures/House3.png"};
        return FXGL.entityBuilder(data)
                .view(houses[(int) data.get("Position")-1])
                .type(EntityType.HOUSE)
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


    @Spawns("WORKSTATION")
    public Entity newWorkstation(SpawnData data) {
        var workstations = new String[]{"structures/reparieren.png","structures/market.png","structures/recyclestation.png"};
        return FXGL.entityBuilder(data)
                .view(workstations[(int)data.get("Position")-1])
                .scale(0.05,0.05)
                .zIndex(10)
                .build();
    }



}

