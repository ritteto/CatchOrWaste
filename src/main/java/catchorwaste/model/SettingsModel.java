package catchorwaste.model;

public class SettingsModel {
    private static int selectedLine = 1;
    private static int selectedColumn = 1;

    public static int getSelectedLine() {
        return selectedLine;
    }

    public static int getSelectedColumn() {
        return selectedColumn;
    }

    public static void setSelectedColumn(int selectedColumn) {
        SettingsModel.selectedColumn = selectedColumn;
    }

    public static void setSelectedLine(int selection) {
        if(selection > 4){
            SettingsModel.selectedLine = 1;
        }else if(selection<1){
            SettingsModel.selectedLine = 4;
        }else{
            SettingsModel.selectedLine = selection;
        }
    }
}
