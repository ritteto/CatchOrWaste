package catchorwaste.controller;

import catchorwaste.view.SettingsView;

import static catchorwaste.model.SettingsModel.setSelectedLine;
import static catchorwaste.model.SettingsModel.getSelectedLine;
import static catchorwaste.model.SettingsModel.setSelectedColumn;
import static catchorwaste.model.SettingsModel.setSelectedLanguage;
import static catchorwaste.model.SettingsModel.getSelectedLanguage;
import static catchorwaste.model.SettingsModel.getSelectedTutorial;

import static catchorwaste.view.SettingsView.highlightSelectedLine;
import static catchorwaste.view.SettingsView.initSettingsView;

public class SettingsController {
    public static void initSettings(){
        initSettingsView();
    }

    public static void changeSelectedLine(int position){
        setSelectedLine(position);
        highlightSelectedLine();
    }

    public static void changeSelectedColumn(int position){
        setSelectedColumn(getSelectedLine(),position);
        SettingsView.highlightSelectedColumn();
    }

    public static void updateLanguage(){
        setSelectedLanguage(getSelectedLanguage());
    }

    public static boolean isTutorialSelected(){
        return getSelectedTutorial() == 1;
    }

}
