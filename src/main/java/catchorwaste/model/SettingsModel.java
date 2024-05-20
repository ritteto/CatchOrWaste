package catchorwaste.model;

import static catchorwaste.CatchOrWasteApp.languages;
import static catchorwaste.CatchOrWasteApp.languageMap;
import static catchorwaste.CatchOrWasteApp.textMap;

public class SettingsModel {
    private static int selectedLine = 1;
    private static int selectedLang = 1;
    private static int selectedDiff = 1;
    private static int selectedTutorial = 1;
    private static double itemsPerSecond = 0;

    private static String selectedLanguage = "german";




    public static int getSelectedLine() {
        return selectedLine;
    }

    public static int getSelectedColumn() {
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

    public static void setSelectedColumn(int currentSelectedLine, int selectedColumn) {
        switch (currentSelectedLine){
            case 1:
                if(selectedColumn < 1){
                    selectedLang = 1;
                }else if(selectedColumn > languages.length){
                    selectedLang = languages.length;
                }else{
                    selectedLang = selectedColumn;
                }
                setSelectedLanguage(languages[selectedLang-1]);
                break;
            case 2:
                if(selectedColumn < 1){
                    selectedDiff = 1;
                }else if(selectedColumn > 3){
                    selectedDiff = 3;
                }else{
                    selectedDiff = selectedColumn;
                }
                updateDifficulty();
                break;
            case 3:
                if(selectedColumn < 1){
                    selectedTutorial = 1;
                }else if(selectedColumn > 2){
                    selectedTutorial = 2;
                }else{
                    selectedTutorial = selectedColumn;
                }
                break;
        }

    }

    public static void setSelectedLine(int selectedLine) {
        if(selectedLine > 4){
            SettingsModel.selectedLine = 1;
        }else if(selectedLine<1){
            SettingsModel.selectedLine = 4;
        }else{
            SettingsModel.selectedLine = selectedLine;
        }
    }

    public static void setSelectedLanguage(String language){
        selectedLanguage = language;
        languageMap = textMap.get(language);
    }

    public static String getSelectedLanguage(){
        return selectedLanguage;
    }

    public static int getSelectedTutorial() {
        return selectedTutorial;
    }

    public static int getSelectedDiff(){
        return selectedDiff;
    }

    public static double getItemsPerSecond(){
        return itemsPerSecond;
    }

    public static void setItemsPerSecond(double rate){
        itemsPerSecond = rate;
    }


    public static void updateDifficulty(){
        switch (selectedDiff){
            case 1:
                itemsPerSecond = 0.4;
                break;
            case 2:
                itemsPerSecond = 0.6;
                break;
            case 3:
                itemsPerSecond = 0.8;
                break;
        }
    }
}
