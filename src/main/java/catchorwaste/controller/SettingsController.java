package catchorwaste.controller;

import static catchorwaste.model.SettingsModel.setSelectedLine;
import static catchorwaste.view.SettingsView.changeSelectedLine;
import static catchorwaste.view.SettingsView.initSelectionScreenView;

public class SettingsController {
    public static void initSelectionScreen(){
        initSelectionScreenView();
    }

    public static void changeSelection(int position){
        setSelectedLine(position);
        changeSelectedLine();
    }
}
