package catchorwaste.model.factories;

import catchorwaste.model.components.CargoComponent;
import catchorwaste.model.components.PlayerDirectionComponent;
import catchorwaste.model.components.ImageNameComponent;
import catchorwaste.model.components.IsCatchedComponent;
import catchorwaste.model.components.CartDirectionComponent;


import catchorwaste.model.enums.EntityType;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.TransformComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;



import java.util.Random;

import static catchorwaste.CatchOrWasteApp.imageMap;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;


public class EntityFactory implements com.almasb.fxgl.entity.EntityFactory {


    @Spawns("PLAYER")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox(new ImageView(imageMap.get("wegwerfpolizist_r_resized")))
                .with(new CargoComponent(null))
                .with(new PlayerDirectionComponent(true))
                .with(new CollidableComponent(true))
                .scale(2, 2)
                .type(EntityType.PLAYER)
                .build();
    }

    @Spawns("OBJECT")
    public Entity newObject(SpawnData data) {
        Random random = new Random();
        String[] names = {
                "iphone_d", "iphone_f", "iphone_r",
                "kleid_d", "kleid_f", "kleid_r",
                "lampe_d", "lampe_f", "lampe_r"};
        int zufallszahl = random.nextInt(names.length);
        String name = names[zufallszahl];
        Entity entity = FXGL.entityBuilder(data)
                .viewWithBBox(new ImageView(imageMap.get(names[zufallszahl])))
                .type(EntityType.OBJECT)
                .scale(1, 1)
                .with(new ProjectileComponent(new Point2D(0, 1),100))
                .with(new ImageNameComponent(name))
                .with(new IsCatchedComponent(false))
                .with(new CollidableComponent(true))
                .build();

        double[] range = null;
        if(data.get("fallingSpeedRange") instanceof double[]){
            range = data.get("fallingSpeedRange");
        }
        assert range != null;
        var speed = random.nextDouble(range[0], range[1]);
        entity.getComponent(ProjectileComponent.class).setSpeed(speed);
        entity.getComponent(TransformComponent.class).rotateBy(270);

        return entity;
    }



    @Spawns("CART")
    public Entity newCart(SpawnData data) {
        return FXGL.entityBuilder(data)
                .viewWithBBox(new ImageView(imageMap.get(data.get("CargoName") + "_cart_horizontal")))
                .with(new ImageNameComponent(data.get("CargoName")))
                .with(new CartDirectionComponent(true))
                .with(new CollidableComponent(true))
                .scale(1.7, 1.7)
                .type(EntityType.CART)
                .build();
    }


    @Spawns("HOUSE")
    public Entity newHouse(SpawnData data) {
        var houses = new String[]{"haus1", "haus2"};
        return FXGL.entityBuilder(data)
                .viewWithBBox(new ImageView(imageMap.get(houses[(int) data.get("Position") - 1])))
                .type(EntityType.HOUSE)
                .scale(1.2, 1.2)
                .build();

    }


    @Spawns("BACKGROUND")
    public Entity newBackground(SpawnData data) {
        var backgrounds = new String[]{"background_bad", "streets_left"};
        var backgroundView = new ImageView(imageMap.get(backgrounds[(int) data.get("Position") - 1]));
        backgroundView.setFitWidth(getAppWidth());
        backgroundView.setFitHeight(getAppHeight());
        return FXGL.entityBuilder(data)
                .view(backgroundView)
                .with(new ImageNameComponent(data.get("Name")))
                .type(EntityType.BACKGROUND)
                .build();
    }

    @Spawns("WORKSTATION")
    public Entity newWorkstation(SpawnData data) {
        var workstations = new String[]{"reparieren", "markt", "recycle"};
        return FXGL.entityBuilder(data)
                .viewWithBBox(new ImageView(imageMap.get(workstations[(int) data.get("Position") - 1])))
                .with(new ImageNameComponent(workstations[(int) data.get("Position") - 1]))
                .with(new CollidableComponent(true))
                .type(EntityType.WORKSTATION)
                .scale(1.45, 1.45)
                .zIndex(10)
                .build();
    }

    @Spawns("ENDSCREEN")
    public Entity newEndScreen(SpawnData data) {
        var endScreenView = new ImageView(imageMap.get("endScreen_1"));
        endScreenView.setFitWidth(getAppWidth());
        endScreenView.setFitHeight(getAppHeight());
        return FXGL.entityBuilder(data)
                .view(endScreenView)
                .type(EntityType.ENDSCREEN)
                .build();
    }


}

