package catchorwaste.model;

public class SelectionScreenModel {
    private static int selected = 1;

    public static int getSelected() {
        return selected;
    }

    public static void setSelected(int selection) {
        if(selection > 4){
            SelectionScreenModel.selected = 1;
        }else if(selection<1){
            SelectionScreenModel.selected = 4;
        }else{
            SelectionScreenModel.selected = selection;
        }
    }
}
