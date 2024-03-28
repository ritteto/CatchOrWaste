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



    @Spawns("PLAYER")
    public Entity newPlayer(SpawnData data) {
        Entity entity = FXGL.entityBuilder(data)
                .view("player/wegwerfpolizist_R_resized.png")
                .with(new CargoComponent(null))
                .with(new PlayerDirectionComponent(true))
                .scale(2,2)
                .type(EntityType.PLAYER)
                .build();

        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(PLAYERSIZE,PLAYERSIZE)));

        return entity;
    }

    @Spawns("OBJECT")
    public Entity newObject(SpawnData data) {
        Random random = new Random();
        String [] zufall = {"iphone_d.png", "iphone_f.png", "iphone_r.png", "kleid_d.png", "kleid_f.png", "kleid_r.png", "lampe_d.png", "lampe_f.png", "lampe_r.png"};
        int zufallszahl = random.nextInt(zufall.length);
        String name = zufall[zufallszahl].replace(".png", "");
        return FXGL.entityBuilder(data)
                .view("fallingObjects/"+zufall [zufallszahl])
                .type(EntityType.OBJECT)
                .scale(1,1)
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
                .scale(1.7,1.7)
                .type(EntityType.CART)
                .build();
    }



    @Spawns("HOUSE")
    public Entity newHouse(SpawnData data) {
        var houses = new String[]{"structures/haus1.png","structures/haus2.png"};
        return FXGL.entityBuilder(data)
                .view(houses[(int) data.get("Position")-1])
                .type(EntityType.HOUSE)
                .scale(1.2,1.2)
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
        var workstations = new String[]{"structures/reparieren.png","structures/markt.png","structures/recycle.png"};
        return FXGL.entityBuilder(data)
                .view(workstations[(int)data.get("Position")-1])
                .scale(1.45,1.45)
                .zIndex(10)
                .build();
    }



}

