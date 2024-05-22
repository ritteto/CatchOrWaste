package catchorwaste.controller;

import catchorwaste.model.enums.GameState;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalInput;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.io.gpio.digital.PullResistance;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import java.util.concurrent.atomic.AtomicBoolean;

import static catchorwaste.CatchOrWasteApp.gameState;
import static catchorwaste.controller.SettingsController.changeSelectedColumn;
import static catchorwaste.controller.SettingsController.changeSelectedLine;
import static catchorwaste.controller.StartScreenController.changeSelectedOption;
import static catchorwaste.model.CartModel.setGate;
import static catchorwaste.model.NameGeneratorModel.setActiveLane;
import static catchorwaste.model.NameGeneratorModel.getActiveLane;
import static catchorwaste.model.NameGeneratorModel.getLetter;
import static catchorwaste.model.NameGeneratorModel.changeLetter;
import static catchorwaste.model.SettingsModel.getSelectedColumn;
import static catchorwaste.model.SettingsModel.getSelectedLine;
import static catchorwaste.model.StartScreenModel.getOption;
import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static catchorwaste.controller.PlayerController.movePlayer;

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
            if(gameState.equals(GameState.GAME)){
                if (e.state() == DigitalState.LOW) {
                    Platform.runLater(() -> setGate(true));
                }
            } else if (gameState.equals(GameState.SETTINGS) && getSelectedLine()<4) {
                if (e.state() == DigitalState.LOW) {
                    Platform.runLater(() ->  changeSelectedColumn(getSelectedColumn()-1));
                }
            }
        });

        buttonRight.addListener(e -> {

            if(gameState.equals(GameState.GAME)){
                if (e.state() == DigitalState.LOW) {
                    Platform.runLater(() -> setGate(false));
                }
            } else if (gameState.equals(GameState.SETTINGS) && getSelectedLine()<4) {
                if (e.state() == DigitalState.LOW) {
                    Platform.runLater(() -> changeSelectedColumn(getSelectedColumn()+1));
                }
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

       joystickDown = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
               .id("JOYSTICK_Right")
               .address(19)
               .pull(PullResistance.PULL_UP)
               .provider("pigpio-digital-input"));

       joystickUp = pi4j.create(DigitalInput.newConfigBuilder(pi4j)
               .id("JOYSTICK_Left")
               .address(13)
               .pull(PullResistance.PULL_UP)
               .provider("pigpio-digital-input"));


       handleMovement(joystickRight, movingRight, this::onJoystickRight);
       handleMovement(joystickLeft, movingLeft, this::onJoystickLeft);
       handleMovement(joystickUp, movingUp, this::onJoystickUp);
       handleMovement(joystickDown, movingDown, this::onJoystickDown);
    }

    public void onAcceptButton(Runnable r) {
        buttonAccept.addListener(e -> {
            if (e.state() == DigitalState.LOW) {
                Platform.runLater(() -> r.run());
            }
        });
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

    private void onJoystickRight() {
        if (gameState.equals(GameState.SETTINGS)) {
            changeSelectedLine(getSelectedLine() + 1);
        } else if (gameState.equals(GameState.STARTSCREEN)) {
            changeSelectedOption(getOption() + 1);
        } else if (gameState.equals(GameState.NAMEGENERATOR)) {
            setActiveLane(getActiveLane() + 1);
        }
    }

    private void onJoystickLeft() {
        if (gameState.equals(GameState.SETTINGS)) {
            changeSelectedLine(getSelectedLine() - 1);
        } else if (gameState.equals(GameState.STARTSCREEN)) {
            changeSelectedOption(getOption() - 1);
        } else if (gameState.equals(GameState.NAMEGENERATOR)) {
            setActiveLane(getActiveLane() - 1);
        }
    }

    private void onJoystickUp() {
        if (gameState.equals(GameState.NAMEGENERATOR)) {
            changeLetter(getLetter() - 1);
        }
    }

    private void onJoystickDown() {
        if (gameState.equals(GameState.NAMEGENERATOR)) {
            changeLetter(getLetter() + 1);
        }
    }
}
