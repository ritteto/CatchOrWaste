package catchorwaste.model.factories;

import catchorwaste.model.components.*;
import catchorwaste.model.enums.EntityType;
import catchorwaste.model.enums.ItemStatus;
import catchorwaste.model.enums.ItemType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

import static catchorwaste.CatchOrWasteApp.imageMap;
import static catchorwaste.model.constants.Constants.PLAYERSIZE;


public class EntityFactory implements com.almasb.fxgl.entity.EntityFactory {


    @Spawns("PLAYER")
    public Entity newPlayer(SpawnData data) {
        Entity entity = FXGL.entityBuilder(data)
                .view(new ImageView(imageMap.get("wegwerfpolizist_r_resized")))
                .with(new CargoComponent(null))
                .with(new PlayerDirectionComponent(true))
                .scale(2, 2)
                .type(EntityType.PLAYER)
                .build();

        entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(PLAYERSIZE, PLAYERSIZE)));

        return entity;
    }

    @Spawns("OBJECT")
    public Entity newObject(SpawnData data) {
        Random random = new Random();
        Image[] zufall = {
                imageMap.get("iphone_d"), imageMap.get("iphone_f"), imageMap.get("iphone_r"),
                imageMap.get("kleid_d"), imageMap.get("kleid_f"), imageMap.get("kleid_r"),
                imageMap.get("lampe_d"), imageMap.get("lampe_f"), imageMap.get("lampe_r")};
        String[] names = {
                "iphone_d", "iphone_f", "iphone_r",
                "kleid_d", "kleid_f", "kleid_r",
                "lampe_d", "lampe_f", "lampe_r"};
        int zufallszahl = random.nextInt(zufall.length);
        String name = names[zufallszahl];
        ItemStatus itemStatus = null;
        ItemType itemType = null;
        // Bestimme den ItemType basierend auf dem Namen des Objekts
        if (name.startsWith("iphone")) {
            itemType = ItemType.IPHONE;
        } else if (name.startsWith("kleid")) {
            itemType = ItemType.DRESS;
        } else if (name.startsWith("lampe")) {
            itemType = ItemType.LAMP;
        }
        if (name.endsWith("_d")) {
            itemStatus = ItemStatus.DEFECT;
        } else if ((name.endsWith("_f"))) {
            itemStatus = ItemStatus.FUNCTIONAL;
        } else {
            itemStatus = ItemStatus.REPAIRABLE;
        }
        return FXGL.entityBuilder(data)
                .view(new ImageView(zufall[zufallszahl]))
                .type(EntityType.OBJECT)
                .scale(1, 1)
                .with(new ProjectileComponent(new Point2D(0, 1), 100))
                .with(new ImageNameComponent(name))
                .with(new IsCatchedComponent(false))
                .with(new ItemTypeComponent(itemType))
                .with(new ItemStatusComponent(itemStatus))
                .build();
    }


    @Spawns("CART")
    public Entity newCart(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new ImageView(imageMap.get(data.get("CargoName") + "_cart_horizontal")))
                .with(new ImageNameComponent(data.get("CargoName")))
                .with(new CartDirectionComponent(true))
                .scale(1.7, 1.7)
                .type(EntityType.CART)
                .build();
    }


    @Spawns("HOUSE")
    public Entity newHouse(SpawnData data) {
        var houses = new String[]{"haus1", "haus2"};
        return FXGL.entityBuilder(data)
                .view(new ImageView(imageMap.get(houses[(int) data.get("Position") - 1])))
                .type(EntityType.HOUSE)
                .scale(1.2, 1.2)
                .build();
    }


    @Spawns("BACKGROUND")
    public Entity newBackground(SpawnData data) {
        var backgrounds = new String[]{"background_bad", "streets"};
        return FXGL.entityBuilder(data)
                .view(new ImageView(imageMap.get(backgrounds[(int) data.get("Position") - 1])))
                .type(EntityType.BACKGROUND)
                .build();
    }


    public Entity newWorkstation(SpawnData data) {
        var workstations = new String[]{"reparieren","markt","recycle"};
        return FXGL.entityBuilder(data)
                .view(new ImageView(imageMap.get(workstations[(int)data.get("Position")-1])))
                .scale(1.45,1.45)
                .zIndex(10)
                .build();
    }


}

