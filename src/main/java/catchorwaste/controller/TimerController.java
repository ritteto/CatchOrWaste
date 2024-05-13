package catchorwaste.controller;

import catchorwaste.CatchOrWasteApp;
import catchorwaste.model.TimerModel;
import catchorwaste.view.PunktesystemView;
import catchorwaste.view.TimerView;
import com.almasb.fxgl.dsl.FXGL;
import javafx.animation.AnimationTimer;

import java.util.EventListener;

import static catchorwaste.model.TimerModel.getMinutes;
import static catchorwaste.model.TimerModel.setTotalSeconds;
import static catchorwaste.model.constants.Constants.TOTAL_TIME_LIMIT_SECONDS;
import static catchorwaste.view.TimerView.initTimerView;

public class TimerController {

    public interface TimerListener {
        void onTimerStopped();
    }

    private static AnimationTimer animationTimer;
    private static TimerListener listener;

    private static long startTimeNano; // time of start

    public static void initTimer(){
        initTimerView();

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateTimer(now);
            }
        };
    }


    public static void startTimer() {
        //sets time
        startTimeNano = System.nanoTime();
        animationTimer.start();
    }

    private static void updateTimer(long now) {
        // calculates remaining time after start
        long elapsedTimeNano = now - startTimeNano;
        int elapsedTimeSeconds = (int) (elapsedTimeNano / 1_000_000_000);

        // Begrenze den Timer auf 3 Minuten
        int remainingTimeSeconds = Math.max(TOTAL_TIME_LIMIT_SECONDS - elapsedTimeSeconds, 0);

        // Aktualisiere das Timer-Modell und die Ansicht
        setTotalSeconds(remainingTimeSeconds);
        TimerView.updateTimer(TimerModel.getMinutes(), TimerModel.getSeconds());

        // checks if there's no time left
        if (remainingTimeSeconds == 0) {
            stopTimer();
            if (listener != null) {
                listener.onTimerStopped();
            }
            //calls method from the main class
            //((CatchOrWasteApp) FXGL.getApp()).timeIsUp(timerView, punktesystemView);
        }
    }

    public static void stopTimer() {
        // Stoppe den AnimationTimer
        animationTimer.stop();
    }

    public static void setTimerListener(TimerListener newListener) {
        listener = newListener;
    }

}

