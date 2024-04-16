package catchOrWaste.controller;

import catchOrWaste.CatchOrWasteApp;
import catchOrWaste.model.TimerModel;
import catchOrWaste.view.TimerView;
import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.AnimationTimer;
import static catchOrWaste.model.constants.Constants.TOTAL_TIME_LIMIT_SECONDS;
public class TimerController {


    private final TimerModel timerModel;
    private final TimerView timerView;

    private final AnimationTimer animationTimer;

    private long startTimeNano; // time of start

    public TimerController(TimerModel model, TimerView view) {
        this.timerModel = model;
        this.timerView = view;

        // creates animation timer to update timer
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateTimer(now);
            }
        };
    }

    public void startTimer() {
        //sets time
        startTimeNano = System.nanoTime();
        animationTimer.start();
    }

    private void updateTimer(long now) {
        // calculates remaining time after start
        long elapsedTimeNano = now - startTimeNano;
        int elapsedTimeSeconds = (int) (elapsedTimeNano / 1_000_000_000);

        // Begrenze den Timer auf 3 Minuten
        int remainingTimeSeconds = Math.max(TOTAL_TIME_LIMIT_SECONDS - elapsedTimeSeconds, 0);

        // Aktualisiere das Timer-Modell und die Ansicht
        timerModel.setTotalSeconds(remainingTimeSeconds);
        timerView.updateTimer(timerModel.getMinutes(), timerModel.getSeconds());

        // checks if there's no time left
        if (remainingTimeSeconds == 0) {
            stopTimer();
            //calls method from the main class
            ((CatchOrWasteApp) FXGL.getApp()).timeIsUp();
        }
    }

    public void stopTimer() {
        // Stoppe den AnimationTimer
        animationTimer.stop();
    }
}


