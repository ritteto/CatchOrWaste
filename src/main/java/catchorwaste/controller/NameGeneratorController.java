package catchorwaste.controller;

import static catchorwaste.model.NameGeneratorModel.initNameGeneratorModel;
import static catchorwaste.view.NameGeneratorView.initNameGeneratorView;

public class NameGeneratorController {
    public static void initNameGenerator(){
        initNameGeneratorView();
        initNameGeneratorModel();
    }
}
