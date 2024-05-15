package catchorwaste.controller;

import static catchorwaste.model.PunktesystemModel.initPointsMap;
import static catchorwaste.view.PunktesystemView.initPunktesystemView;
import static catchorwaste.view.PunktesystemView.updateScore;


public class PunktesystemController {

    public static void initPunktesystem() {
        initPunktesystemView();
        updateScore(0);
        initPointsMap();
    }

}
