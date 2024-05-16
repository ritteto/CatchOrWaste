package catchorwaste.controller;

import static catchorwaste.model.SelectionScreenModel.setSelected;
import static catchorwaste.view.SelectionScreenView.changeSelected;
import static catchorwaste.view.SelectionScreenView.initSelectionScreenView;

public class SelectionScreenController {
    public static void initSelectionScreen(){
        initSelectionScreenView();
    }

    public static void changeSelection(int position){
        setSelected(position);
        changeSelected();
    }
}
