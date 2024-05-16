package catchorwaste.model;

public class SettingsModel {
    private static int selected = 1;

    public static int getSelected() {
        return selected;
    }

    public static void setSelectedLine(int selection) {
        if(selection > 4){
            SettingsModel.selected = 1;
        }else if(selection<1){
            SettingsModel.selected = 4;
        }else{
            SettingsModel.selected = selection;
        }
    }
}
