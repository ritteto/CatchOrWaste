package catchorwaste.model;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static catchorwaste.model.variables.Constants.IPHONE_SCORE;
import static catchorwaste.model.variables.Constants.DRESS_SCORE;
import static catchorwaste.model.variables.Constants.LAMP_SCORE;
import static catchorwaste.model.variables.globalVariables.score;

public class PunktesystemModel {

    public PunktesystemModel(){
        score = 0;
    }

    public Map<String, Map<String, Map<String, Integer>>> pointsMap;

    public void addPoints(int points) {
        score += points;
    }


    public void subtractPoints(int points) {
        score -= points;
    }

    public int getPoints() {
        return score;
    }

    public void initPointsMap() {
        var initPointsMap = new HashMap<String, Map<String, Map<String, Integer>>>();
        var workstations = new String[] {"recycle", "markt", "reparieren", "default"};
        var objects = new String[] {"iphone", "kleid", "lampe"};
        var objectValues = new HashMap<String, int[][]>();
        var states = new String[] {"f", "r", "d"};


        objectValues.put(workstations[0], new int[][] {
                {0, 0, IPHONE_SCORE},
                {0, 0, DRESS_SCORE},
                {0, 0, LAMP_SCORE}
        });

        objectValues.put(workstations[1], new int[][] {
                {IPHONE_SCORE, IPHONE_SCORE / 2, 0},
                {DRESS_SCORE, DRESS_SCORE / 2, 0},
                {LAMP_SCORE, LAMP_SCORE / 2, 0}
        });

        objectValues.put(workstations[2], new int[][] {
                {IPHONE_SCORE / 2, IPHONE_SCORE, 0},
                {DRESS_SCORE / 2, DRESS_SCORE, 0},
                {LAMP_SCORE / 2, LAMP_SCORE, 0}
        });

        objectValues.put(workstations[3], new int[][] {
                {IPHONE_SCORE, IPHONE_SCORE, IPHONE_SCORE},
                {DRESS_SCORE, DRESS_SCORE, DRESS_SCORE},
                {LAMP_SCORE, LAMP_SCORE, LAMP_SCORE}
        });


        initPointsMap = new LinkedHashMap<>();
        for (String workstation : workstations) {
            var map2 = new LinkedHashMap<String, Map<String, Integer>>();
            for (int j = 0; j < objects.length; j++) {
                var map1 = new LinkedHashMap<String, Integer>();
                for (int k = 0; k < states.length; k++) {
                    map1.put(states[k], objectValues.get(workstation)[j][k]);
                }
                map2.put(objects[j], map1);
            }
            initPointsMap.put(workstation, map2);
        }

        pointsMap = initPointsMap;
    }

    public Map<String, Map<String, Map<String, Integer>>>  getPointsMap(){
        return this.pointsMap;
    }
}
