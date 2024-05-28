package catchorwaste.controller.entities;

import catchorwaste.controller.PunktesystemController;
import catchorwaste.controller.screens.SettingsController;
import catchorwaste.model.components.ImageNameComponent;
import catchorwaste.model.components.IsCatchedComponent;
import catchorwaste.model.components.CargoComponent;
import catchorwaste.model.components.PlayerDirectionComponent;
import catchorwaste.model.entities.FallingObjectModel;
import catchorwaste.model.enums.EntityType;
import catchorwaste.view.entities.FallingObjectView;
import com.almasb.fxgl.entity.Entity;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class FallingObjectController {

    private FallingObjectView fallingObjectView;
    private FallingObjectModel fallingObjectModel;

    private PunktesystemController punktesystemController;




    public FallingObjectController(FallingObjectModel fallingObjectModel, SettingsController settingsController,
                                   PunktesystemController punktesystemController){
        this.punktesystemController = punktesystemController;
        this.fallingObjectModel = fallingObjectModel;
        this.fallingObjectView = new FallingObjectView(fallingObjectModel, settingsController);

    }

    public void dropObjects() {
        var entities = getGameWorld().getEntitiesByType(EntityType.OBJECT);
        if (!entities.isEmpty()) {
            for (Entity entity : entities) {
                if (entity.getY() >= getAppHeight()*0.9) {
                    var stringSplit = entity.getComponent(ImageNameComponent.class).getImageName().split("_");
                    var points = punktesystemController.getPointsMap()
                            .get("default").get(stringSplit[0]).get(stringSplit[1]);
                    punktesystemController.subtractPoints(points);
                    punktesystemController.displayUpdate(-points, entity.getX(), getAppHeight()-50);
                    entity.removeFromWorld();
                }
            }
        }

    }

    public void stickToPlayer() {
        var entities = getGameWorld().getEntitiesByType(EntityType.OBJECT);
        var players = getGameWorld().getEntitiesByType(EntityType.PLAYER);
        for (Entity object : entities) {
            var isCatched = object.getComponent(IsCatchedComponent.class).isCatched();
            for (Entity player : players) {
                if (!player.getComponent(CargoComponent.class).isFull()
                        && isCatched) {
                    player.getComponent(CargoComponent.class).setCatchedEntity(object);
                        setPosition(player, object);
                } else if (player.getComponent(CargoComponent.class).isFull()
                        && isCatched && player.getComponent(CargoComponent.class).getCatchedEntity().equals(object)) {
                        setPosition(player, object);
                }
            }
        }
    }

    private void setPosition(Entity player, Entity object){
        var width = player.getBoundingBoxComponent().getWidth();
        if (player.getComponent(PlayerDirectionComponent.class).getDirection()) {
            object.setX(player.getX()+width*0.5);
            object.setY(player.getY());
        } else {
            object.setX(player.getX() - width*0.5);
            object.setY(player.getY());
        }
    }

    public void spawnObjects(){
        fallingObjectView.spawnObjects();
    }

    public void setGameStartTime(long gameStartTime){
        fallingObjectModel.setGameStartTime(gameStartTime);
    }


}
