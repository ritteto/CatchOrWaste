package catchorwaste.controller;

import catchorwaste.model.PunktesystemModel;
import catchorwaste.view.PunktesystemView;

import java.util.Map;

import static catchorwaste.model.variables.globalVariables.score;


public class PunktesystemController {

    PunktesystemModel punktesystemModel;
    PunktesystemView punktesystemView;

    public PunktesystemController(PunktesystemModel punktesystemModel){
        this.punktesystemModel = punktesystemModel;
        this.punktesystemView = new PunktesystemView();
    }
    public void initPunktesystem() {
        punktesystemView.initPunktesystemView();
        score = 0;
        punktesystemView.updateScore();
        punktesystemModel.initPointsMap();
    }

    public void addPoints(int points) {
        punktesystemModel.addPoints(points);
        punktesystemView.updateScore();
    }

    public void subtractPoints(int points) {
        punktesystemModel.subtractPoints(points);
        punktesystemView.updateScore();
    }

    public void displayUpdate(int points, double x, double y){
        punktesystemView.displayUpdate(points,x,y);
    }

    public Map<String, Map<String, Map<String, Integer>>> getPointsMap(){
        return punktesystemModel.getPointsMap();
    }

    public void resetModel(){
        this.punktesystemModel = new PunktesystemModel();
    }

}
