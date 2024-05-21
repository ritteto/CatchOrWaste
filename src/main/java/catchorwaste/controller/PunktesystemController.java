package catchorwaste.controller;

import static catchorwaste.model.PunktesystemModel.initPointsMap;
import static catchorwaste.model.PunktesystemModel.initPunktesystemModel;
import static catchorwaste.view.PunktesystemView.initPunktesystemView;
import static catchorwaste.view.PunktesystemView.updateScore;


public class PunktesystemController {

    public static void initPunktesystem() {
        initPunktesystemView();
        initPunktesystemModel();
        updateScore(0);
        initPointsMap();
    }

}
