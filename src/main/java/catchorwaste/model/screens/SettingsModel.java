package catchorwaste.model.screens;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static catchorwaste.CatchOrWasteApp.languages;
import static catchorwaste.CatchOrWasteApp.languageMap;
import static catchorwaste.CatchOrWasteApp.textMap;

public class SettingsModel {
    private int selectedLine = 1;
    private int selectedLang = 1;
    private int selectedDiff = 1;
    private int selectedTutorial = 1;
    private double itemsPerSecond = 0;
    private double[] speedRange = {80.0,120.0};

    private String selectedLanguage = "german";



    public int getSelectedLine() {
        return selectedLine;
    }

    public int getSelectedColumn() {
        if(getSelectedLine() == 1){
           return selectedLang;
        }else if (getSelectedLine() == 2) {
            return selectedDiff;
        }else if (getSelectedLine() == 3) {
            return selectedTutorial;
        }else{
            return 0;
        }

    }

    public void setSelectedColumn(int currentSelectedLine, int selectedColumn) {
        switch (currentSelectedLine){
            case 1:
                if(selectedColumn < 1){
                    this.selectedLang = 1;
                }else if(selectedColumn > languages.length){
                    this.selectedLang = languages.length;
                }else{
                    this.selectedLang = selectedColumn;
                }
                setSelectedLanguage(languages[selectedLang-1]);
                break;
            case 2:
                if(selectedColumn < 1){
                    this.selectedDiff = 1;
                }else if(selectedColumn > 3){
                    this.selectedDiff = 3;
                }else{
                    this.selectedDiff = selectedColumn;
                }
                updateDifficulty();
                break;
            case 3:
                if(selectedColumn < 1){
                    this.selectedTutorial = 1;
                }else if(selectedColumn > 2){
                    this.selectedTutorial = 2;
                }else{
                    this.selectedTutorial = selectedColumn;
                }
                break;
        }

    }

    public void setSelectedLine(int selectedLine) {
        if(selectedLine > 4){
            this.selectedLine = 1;
        }else if(selectedLine<1){
            this.selectedLine = 4;
        }else{
            this.selectedLine = selectedLine;
        }
    }

    public void setSelectedLanguage(String language){
        this.selectedLanguage = language;
        languageMap = textMap.get(language);
    }

    public String getSelectedLanguage(){
        return this.selectedLanguage;
    }

    public int getSelectedTutorial() {
        return this.selectedTutorial;
    }


    public double getItemsPerSecond(){
        return this.itemsPerSecond;
    }

    public void setItemsPerSecond(double rate){
        this.itemsPerSecond = rate;
    }

    public double[] getSpeedRange(){
        return this.speedRange;
    }

    public int getDifficulty(){
        return this.selectedDiff;
    }

    public void updateDifficulty(){
        switch (this.selectedDiff){
            case 1:
                this.itemsPerSecond = readDifficulty(1);
                this.speedRange = readSpeedRange(1);
                break;
            case 2:
                this.itemsPerSecond = readDifficulty(2);
                this.speedRange = readSpeedRange(2);
                break;
            case 3:
                this.itemsPerSecond = readDifficulty(3);
                this.speedRange = readSpeedRange(3);
                break;
        }
    }


    public double readDifficulty(int level){
        double rate=0.0;
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
                if(field.getKey().equals("difficulty"+level)){
                    rate = Double.parseDouble(String.valueOf(field.getValue()));
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return rate;
    }

    public double[] readSpeedRange(int level){
        double[] rate={0.0,0.0};
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
                if(field.getKey().equals("speedRange"+level)){
                    rate[0] = field.getValue().get(0).asDouble();
                    rate[1] = field.getValue().get(1).asDouble();
                }
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return rate;
    }
}
