package catchorwaste.controller.screens;

import catchorwaste.model.screens.SettingsModel;
import catchorwaste.view.screens.SettingsView;


public class SettingsController {
    SettingsModel settingsModel;
    SettingsView settingsView;

    public SettingsController(SettingsModel settingsModel){
        this.settingsModel = settingsModel;
        settingsView = new SettingsView(settingsModel);
    }
    public void initSettings(){
        settingsView.initSettingsView();
    }

    public void changeSelectedLine(int position){
        settingsModel.setSelectedLine(position);
        settingsView.highlightSelectedLine();
    }

    public void changeSelectedColumn(int position){
        settingsModel.setSelectedColumn(settingsModel.getSelectedLine(),position);
        settingsView.highlightSelectedColumn();
    }

    public void updateLanguage(){
        settingsModel.setSelectedLanguage(settingsModel.getSelectedLanguage());
    }

    public boolean isTutorialSelected(){
        return settingsModel.getSelectedTutorial() == 1;
    }

    public int getSelectedLine(){
        return settingsModel.getSelectedLine();
    }

    public int getSelectedColumn(){
        return settingsModel.getSelectedColumn();
    }

    public void setItemsPerSecond(double rate){
        settingsModel.setItemsPerSecond(rate);
    }

    public double getItemsPerSecond(){
        return settingsModel.getItemsPerSecond();
    }

    public void updateDifficulty(){
        settingsModel.updateDifficulty();
    }

}
