package catchorwaste.model;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

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


    public void initPointsMap() {
        var initPointsMap = new HashMap<String, Map<String, Map<String, Integer>>>();
        var workstations = new String[] {"recycle", "markt", "reparieren", "default"};
        var objects = new String[] {"iphone", "kleid", "lampe"};
        var objectValues = new HashMap<String, int[][]>();
        var states = new String[] {"f", "r", "d"};

        var iphoneScore = readScoreValues("iphoneScore");
        var dressScore = readScoreValues("dressScore");
        var lampScore = readScoreValues("lampScore");

        objectValues.put(workstations[0], new int[][] {
                {0, 0, iphoneScore},
                {0, 0, dressScore},
                {0, 0, lampScore}
        });

        objectValues.put(workstations[1], new int[][] {
                {iphoneScore, iphoneScore / 2, 0},
                {dressScore, dressScore / 2, 0},
                {lampScore, lampScore / 2, 0}
        });

        objectValues.put(workstations[2], new int[][] {
                {iphoneScore / 2, iphoneScore, 0},
                {dressScore / 2, dressScore, 0},
                {lampScore / 2, lampScore, 0}
        });

        objectValues.put(workstations[3], new int[][] {
                {iphoneScore, iphoneScore, iphoneScore},
                {dressScore, dressScore, dressScore},
                {lampScore, lampScore, lampScore}
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

    public int readScoreValues(String key){
        int value = 0;

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
                if(field.getKey().equals(key)){
                    value = Integer.parseInt(String.valueOf(field.getValue()));
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return value;
    }

    public Map<String, Map<String, Map<String, Integer>>>  getPointsMap(){
        return this.pointsMap;
    }
}
