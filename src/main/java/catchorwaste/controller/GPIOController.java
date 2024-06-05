package catchorwaste.controller;

import catchorwaste.controller.entities.CartController;
import catchorwaste.controller.entities.PlayerController;
import catchorwaste.controller.screens.SettingsController;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

public class GPIOController {

    private static Context pi4j;
    private DigitalInput joystickRight;
    private DigitalInput joystickLeft;
    private DigitalInput buttonLeft;
    private DigitalInput buttonRight;
    private DigitalInput buttonAccept;

    private DigitalInput joystickDown;
    private DigitalInput joystickUp;
    private final AtomicBoolean movingLeft = new AtomicBoolean(false);
    private final AtomicBoolean movingRight = new AtomicBoolean(false);

    private final AtomicBoolean movingUp = new AtomicBoolean(false);
    private final AtomicBoolean movingDown = new AtomicBoolean(false);
    private AnimationTimer movementTimer;

    private  CartController cartController;
    private SettingsController settingsController;

    private Runnable actionAccept;
    private Runnable actionRight;
    private Runnable actionLeft;
    private Runnable actionUp;
    private Runnable actionDown;
    private Runnable actionBtnLeft;
    private Runnable actionBtnRight;

    public void initControllers(CartController cartController, SettingsController settingsController){
        this.cartController = cartController;
        this.settingsController = settingsController;
    }

    public void initActions(Runnable left, Runnable right, Runnable up, Runnable down,
                            Runnable accept, Runnable btnLeft, Runnable btnRight){
        this.actionLeft = left;
        this.actionRight = right;
        this.actionUp = up;
        this.actionDown = down;
        this.actionAccept = accept;
        this.actionBtnLeft = btnLeft;
        this.actionBtnRight = btnRight;
    }
    public void setup(){
        pi4j = Pi4J.newAutoContext();
        setupGPIO();
        setupMovementTimer();

    }

   private void setupGPIO() {

        buttonLeft = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id("BUTTON_Left")
                .address(25)  // GPIO 5 for the left button
                .pull(PullResistance.PULL_UP)
                .provider("pigpio-digital-input"));

        buttonRight = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id("BUTTON_Right")
                .address(24)  // GPIO 6 for the right button
                .pull(PullResistance.PULL_UP)
                .provider("pigpio-digital-input"));

       buttonAccept = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
               .id("BUTTON_Accept")
               .address(23)  // GPIO 22 for the accept button
               .pull(PullResistance.PULL_UP)
               .provider("pigpio-digital-input"));

        buttonLeft.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                Platform.runLater(actionBtnLeft);
            }
        });

        buttonRight.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                Platform.runLater(actionBtnRight);
            }
        });

       buttonAccept.addListener(e -> {
           if (e.state() == DigitalState.LOW) {
               Platform.runLater(actionAccept);
           }
       });

        joystickRight = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id("JOYSTICK_Right")
                .address(4)
                .pull(PullResistance.PULL_UP)
                .provider("pigpio-digital-input"));

        joystickLeft = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
                .id("JOYSTICK_Left")
                .address(17)
                .pull(PullResistance.PULL_UP)
                .provider("pigpio-digital-input"));

       joystickDown = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
               .id("JOYSTICK_Down")
               .address(5)
               .pull(PullResistance.PULL_UP)
               .provider("pigpio-digital-input"));

       joystickUp = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
               .id("JOYSTICK_Up")
               .address(6)
               .pull(PullResistance.PULL_UP)
               .provider("pigpio-digital-input"));


       handleMovement(joystickRight, movingRight, this::onJoystickRight);
       handleMovement(joystickLeft, movingLeft, this::onJoystickLeft);
       handleMovement(joystickUp, movingUp, this::onJoystickUp);
       handleMovement(joystickDown, movingDown, this::onJoystickDown);
    }


    private void handleMovement(DigitalInput joystick, AtomicBoolean moving, Runnable action) {
        joystick.addListener(e -> {
            moving.set(e.state() == DigitalState.LOW);
            if (moving.get()) {
                Platform.runLater(action);
            }
        });
    }

    private void setupMovementTimer() {
        PlayerController playerController = new PlayerController(cartController, settingsController);
        movementTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (movingRight.get()) {
                    playerController.movePlayer(true, getGameWorld());
                }
                if (movingLeft.get()) {
                    playerController.movePlayer(false, getGameWorld());
                }
            }
        };
        movementTimer.start();
    }

    private void onJoystickRight() {
        actionRight.run();
    }

    private void onJoystickLeft() {
        actionLeft.run();
    }

    private void onJoystickUp() {
        actionUp.run();
    }

    private void onJoystickDown() {
        actionDown.run();
    }

}
