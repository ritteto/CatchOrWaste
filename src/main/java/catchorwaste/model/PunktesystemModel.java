package catchorwaste.model;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static catchorwaste.model.constants.Constants.IPHONE_SCORE;
import static catchorwaste.model.constants.Constants.DRESS_SCORE;
import static catchorwaste.model.constants.Constants.LAMP_SCORE;
import static catchorwaste.view.PunktesystemView.updateScore;

public class PunktesystemModel {

    private static double score = 0;
    public static Map<String,Map<String,Map<String, Double>>> pointsMap;


    public static double getScore() {
        return score;
    }

    public static void  addPoints(double points) {
        score += points;
        updateScore(score);
    }


    public static void subtractPoints(double points) {
        score -= points;
        if (score < 0) {
            score = 0; // Verhindert negative Punktzahlen
        }
        updateScore(score);
    }



    public static void initPointsMap(){
        var initPointsMap = new HashMap<String,Map<String,Map<String, Double>>>();
        var workstations = new String[]{"recycle", "markt","reparieren", "default"};
        var objects = new String[]{"iphone", "kleid", "lampe"};
        var objectValues = new HashMap<String, double[][]>();
        var states = new String[]{"f", "r", "d"};


        objectValues.put(workstations[0], new double[][]{
                {IPHONE_SCORE*0, IPHONE_SCORE*0, IPHONE_SCORE*1},
                {DRESS_SCORE*0, DRESS_SCORE*0, DRESS_SCORE*1},
                {LAMP_SCORE*0, LAMP_SCORE * 0, LAMP_SCORE *1}
        });

        objectValues.put(workstations[1], new double[][]{
                {IPHONE_SCORE*1, IPHONE_SCORE*0.5, IPHONE_SCORE*0},
                {DRESS_SCORE*1, DRESS_SCORE*0.5, DRESS_SCORE*0},
                {LAMP_SCORE*1, LAMP_SCORE * 0.5, LAMP_SCORE *0}
        });

        objectValues.put(workstations[2], new double[][]{
                {IPHONE_SCORE*0.5, IPHONE_SCORE*1, IPHONE_SCORE*0},
                {DRESS_SCORE*0.5, DRESS_SCORE*1, DRESS_SCORE*0},
                {LAMP_SCORE*0.5, LAMP_SCORE * 1, LAMP_SCORE *0}
        });

        objectValues.put(workstations[3], new double[][]{
                {IPHONE_SCORE * 1, IPHONE_SCORE * 1, IPHONE_SCORE * 1},
                {DRESS_SCORE * 1, DRESS_SCORE * 1, DRESS_SCORE * 1},
                {LAMP_SCORE * 1, LAMP_SCORE * 1, LAMP_SCORE * 1}
        });


        initPointsMap = new LinkedHashMap<>();
        for (int i = 0; i < workstations.length; i++) {
            var map2 = new LinkedHashMap<String , Map<String, Double>>();
            for (int j = 0; j < objects.length; j++) {
                var map1 = new LinkedHashMap<String, Double>();
                for (int k = 0; k < states.length; k++) {
                    map1.put(states[k], objectValues.get(workstations[i])[j][k]);
                }
                map2.put(objects[j], map1);
            }
            initPointsMap.put(workstations[i], map2);
        }

        pointsMap =  initPointsMap;
    }
}
