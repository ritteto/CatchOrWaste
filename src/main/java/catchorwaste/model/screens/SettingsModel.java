package catchorwaste.model.screens;

import static catchorwaste.CatchOrWasteApp.languages;
import static catchorwaste.CatchOrWasteApp.languageMap;
import static catchorwaste.CatchOrWasteApp.textMap;

public class SettingsModel {
    private int selectedLine = 1;
    private int selectedLang = 1;
    private int selectedDiff = 1;
    private int selectedTutorial = 1;
    private double itemsPerSecond = 0;

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

    public int getSelectedDiff(){
        return this.selectedDiff;
    }

    public double getItemsPerSecond(){
        return this.itemsPerSecond;
    }

    public void setItemsPerSecond(double rate){
        this.itemsPerSecond = rate;
    }


    public void updateDifficulty(){
        switch (this.selectedDiff){
            case 1:
                this.itemsPerSecond = 0.4;
                break;
            case 2:
                this.itemsPerSecond = 0.6;
                break;
            case 3:
                this.itemsPerSecond = 0.8;
                break;
        }
    }
}
