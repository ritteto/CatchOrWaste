package catchorwaste.controller;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import java.util.concurrent.atomic.AtomicBoolean;

import static catchorwaste.model.CartModel.setGate;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static catchorwaste.controller.PlayerController.movePlayer;

public class GPIOController {

    private static Context pi4j;
    private DigitalInput joystickRight;
    private DigitalInput joystickLeft;
    private DigitalInput buttonLeft;
    private DigitalInput buttonRight;
    private DigitalInput buttonAccept;
    private final AtomicBoolean movingLeft = new AtomicBoolean(false);
    private final AtomicBoolean movingRight = new AtomicBoolean(false);
    private AnimationTimer movementTimer;


    public void init(){
        pi4j = Pi4J.newAutoContext();
        setupGPIO();
        setupMovementTimer();
    }

   private void setupGPIO() {

        buttonLeft = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id("BUTTON_Left")
                .address(5)  // GPIO 5 for the left button
                .pull(PullResistance.PULL_UP)
                .provider("pigpio-digital-input"));


        
        buttonRight = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id("BUTTON_Right")
                .address(6)  // GPIO 6 for the right button
                .pull(PullResistance.PULL_UP)
                .provider("pigpio-digital-input"));

       buttonAccept = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
               .id("BUTTON_Accept")
               .address(22)  // GPIO 22 for the accept button
               .pull(PullResistance.PULL_UP)
               .provider("pigpio-digital-input"));

        buttonLeft.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                Platform.runLater(() -> setGate(true));
            }
        });

        buttonRight.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                Platform.runLater(() -> setGate(false));
            }
        });

        joystickRight = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id("JOYSTICK_Right")
                .address(19)
                .pull(PullResistance.PULL_UP)
                .provider("pigpio-digital-input"));

        joystickLeft = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id("JOYSTICK_Left")
                .address(13)
                .pull(PullResistance.PULL_UP)
                .provider("pigpio-digital-input"));

        handleMovement(joystickRight, movingRight, true);
        handleMovement(joystickLeft, movingLeft, false);
    }

    public void onAcceptButton(Runnable r) {
        buttonAccept.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                Platform.runLater(() -> r.run());
            }
        });
    }

    private void handleMovement(DigitalInput joystick, AtomicBoolean moving, boolean direction) {
        joystick.addListener(e -> {
            moving.set(e.state() == DigitalState.LOW);
        });
    }

    private void setupMovementTimer() {
        movementTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (movingRight.get()) {
                    movePlayer(true, getGameWorld());
                }
                if (movingLeft.get()) {
                    movePlayer(false, getGameWorld());
                }
            }
        };
        movementTimer.start();
    }
}
