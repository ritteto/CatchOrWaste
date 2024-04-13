package code.controller;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.*;
import javafx.application.Platform;
import java.util.concurrent.atomic.AtomicBoolean;

import static code.controller.PlayerController.movePlayer;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

public class GPIOController {

    private static Context pi4j;
    private DigitalInput joystickRight;
    private DigitalInput joystickLeft;
    private final AtomicBoolean movingLeft = new AtomicBoolean(false);
    private final AtomicBoolean movingRight = new AtomicBoolean(false);

    public GPIOController(){
        pi4j = Pi4J.newAutoContext();
        setupGPIO();
    }

    private void setupGPIO() {
        joystickRight = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
            .id("JOYSTICK_Right")
            .address(13)
            .pull(PullResistance.PULL_UP)
            .provider("pigpio-digital-input"));

        joystickLeft = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
            .id("JOYSTICK_Left")
            .address(19)
            .pull(PullResistance.PULL_UP)
            .provider("pigpio-digital-input"));

        // Register event listeners for the joystick
        joystickRight.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                movingRight.set(true);
                startMovingRight();
            } else {
                movingRight.set(false);
            }
        });

        joystickLeft.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                movingLeft.set(true);
                startMovingLeft();
            } else {
                movingLeft.set(false);
            }
        });
    }

    private void startMovingRight() {
        new Thread(() -> {
            while (movingRight.get()) {
                Platform.runLater(() -> {
                    System.out.println("Joystick Right - Moving Player Right");
                    movePlayer(true, getGameWorld());
                });
                try {
                    Thread.sleep(20);  // adjust the speed of movement by changing the sleep duration
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void startMovingLeft() {
        new Thread(() -> {
            while (movingLeft.get()) {
                Platform.runLater(() -> {
                    System.out.println("Joystick Left - Moving Player Left");
                    movePlayer(false, getGameWorld());
                });
                try {
                    Thread.sleep(20);  // adjust the speed of movement by changing the sleep duration
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
