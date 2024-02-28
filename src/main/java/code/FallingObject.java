package code;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;

import java.util.Arrays;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;
import static code.FxglTest.*;
import static code.Variables.*;

/**
 * Represents a falling object in the game.
 * This class manages the state and behavior of a falling object.
 * R1.1 Gegenstand wird erstellt
 * R1.4 Gegenstand fällt mit zufälliger Richtung auf Enstorgungsstelle zu
 */
public class FallingObject {

    private final Entity entity;
    private boolean isCatched;

    /**
     * Constructor for FallingObject.
     *
     * @param entity The entity representing the falling object.
     */
    public FallingObject(Entity entity) {
        this.entity = entity;
        //entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(3000*0.02,3000*0.02)));
    }
    /**
     * Gets the entity associated with this falling object.
     *
     * @return The Entity instance.
     */
    public Entity getEntity() {
        return this.entity;
    }
    /**
     * Sets the state of the object as caught or not.
     *
     * @param isCatched Boolean representing if the object is caught.
     */
    public void setCatched(Boolean isCatched) {
        this.isCatched = isCatched;
    }
    /**
     * Checks if the object is caught.
     *
     * @return true if the object is caught, false otherwise.
     */
    public Boolean isCatched() {
        return this.isCatched;
    }
    /**
     * Updates the state of the falling object.
     * This method handles spawning of new objects and removal of objects that fall out of the world bounds.
     *
     * @param gameWorld The GameWorld in which this object exists.
     */
    public void onUpdate(GameWorld gameWorld) {

        int house1 = 100;
        int house2 = 200;
        int house3 = 300;
        int house4 = 400;
        int house5 = 500;
        int house6 = 600;


        int[] houseX = {house1, house2, house3, house4, house5, house6};

        Random random = new Random();
        int randomHouse = random.nextInt(houseX.length);

        if (gameWorld.getEntitiesByType(EntityType.OBJECT).size() < FALLING_OBJECT_AMOUNT) {
            FallingObject fallingObject = new FallingObject(gameWorld.spawn("OBJECT", houseX[randomHouse], 0));
            addFallingObjectToArray(fallingObject);
        }
        if (!gameWorld.getEntitiesByType(EntityType.OBJECT).isEmpty()) {
            for (Entity entity : gameWorld.getEntitiesByType(EntityType.OBJECT)) {
                if (entity.getY() >= getAppHeight()) {
                    entity.removeFromWorld();
                    for (int i = 0; i < fallingObjects.length; i++) {
                        if (fallingObjects[i] != null && fallingObjects[i].entity.equals(entity)) {
                            fallingObjects[i] = null;
                        }
                    }
                    FallingObject fallingObject = new FallingObject(spawn("OBJECT", houseX[randomHouse], 0));
                    addFallingObjectToArray(fallingObject);
                }
            }
        }

    }
    /**
     * Adds a falling object to the array of falling objects.
     * If the array is full, it is expanded to accommodate the new object.
     *
     * @param fallingObject The FallingObject to be added.
     */
    private void addFallingObjectToArray(FallingObject fallingObject) {
        if (Arrays.asList(fallingObjects).contains(null)) {
            for (int i = 0; i < fallingObjects.length; i++) {
                if (fallingObjects[i] == null) {
                    fallingObjects[i] = fallingObject;
                    break;
                }
            }
        } else {
            fallingObjects = Arrays.copyOf(fallingObjects, fallingObjects.length + 1);
            fallingObjects[fallingObjects.length - 1] = fallingObject;
        }


    }
}
