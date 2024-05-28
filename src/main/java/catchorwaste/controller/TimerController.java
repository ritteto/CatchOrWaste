package catchorwaste.controller;

import catchorwaste.model.TimerModel;
import catchorwaste.view.TimerView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.AnimationTimer;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static catchorwaste.model.TimerModel.setTotalSeconds;
import static catchorwaste.model.variables.Constants.TOTAL_TIME_LIMIT_SECONDS;
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
        int remainingTimeSeconds = Math.max(readTotalSeconds() - elapsedTimeSeconds, 0);

        // Aktualisiere das Timer-Modell und die Ansicht
        setTotalSeconds(remainingTimeSeconds);
        TimerView.updateTimer(TimerModel.getMinutes(), TimerModel.getSeconds());

        // checks if there's no time left
        if (remainingTimeSeconds == 0) {
            stopTimer();
            if (listener != null) {
                listener.onTimerStopped();
            }
        }
    }

    public static int readTotalSeconds(){
        var seconds = 0;
        File file;
        if(System.getProperty("os.name").contains("Windows")){
            file = new File("src/main/resources/config/gameVariables/configurableVariables.json");
        }else{
            file = new File("/home/pi4j/deploy/configurableVariables.json");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(file);

            Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
            while (iterator.hasNext()){
                Map.Entry<String, JsonNode> field = iterator.next();
                if(field.getKey().equals("totalTimeSec")){
                    seconds = Integer.parseInt(String.valueOf(field.getValue()));
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return seconds;
    }

    private static void stopTimer() {
        // Stoppe den AnimationTimer
        animationTimer.stop();
    }

    public static void setTimerListener(TimerListener newListener) {
        listener = newListener;
    }

}

