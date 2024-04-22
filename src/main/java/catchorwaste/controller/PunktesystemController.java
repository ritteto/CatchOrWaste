package catchorwaste.controller;

import catchorwaste.model.PunktesystemModel;
import catchorwaste.model.enums.ItemStatus;
import catchorwaste.model.enums.ItemType;
import catchorwaste.view.PunktesystemView;

import java.util.Objects;

import static catchorwaste.model.constants.Constants.IPHONE_SCORE;
import static catchorwaste.model.constants.Constants.LAMP_SCORE;
import static catchorwaste.model.constants.Constants.DRESS_SCORE;


public class PunktesystemController {
    private final PunktesystemModel scoreModel;
    private final PunktesystemView scoreView;

    public PunktesystemController(PunktesystemModel scoreModel, PunktesystemView scoreView) {
        this.scoreModel = scoreModel;
        this.scoreView = scoreView;
    }

    // Method to add points based on workstation position, item type, and status
    public void addPoints(int stationPosition, ItemType type, ItemStatus itemStatus) {
        double percentage = 0;
        switch (stationPosition) {
            case 1 -> { // Reparatur
                switch (itemStatus) {
                    case FUNCTIONAL -> percentage = 0.5; // 50% der Punkte werden gutgeschrieben
                    case REPAIRABLE -> percentage = 1.0; // 100% der Punkte werden gutgeschrieben
                    case DEFECT -> percentage = 0.0; // keine punkte vergeben
                }
            }
            case 2 -> { // Markt
                switch (itemStatus) {
                    case FUNCTIONAL -> percentage = 1.0; // 100% der Punkte werden gutgeschrieben
                    case REPAIRABLE -> percentage = 0.5; // 50% der Punkte werden gutgeschrieben
                    case DEFECT -> percentage = 0.0; // keine Punkte gegeben
                }
            }
            case 3 -> { // Recycling-Center
                if (Objects.requireNonNull(itemStatus) == ItemStatus.DEFECT) {
                    percentage = 1.0; // 100% der Punkte werden gutgeschrieben
                }
            }
        }
        switch (type) {
            case IPHONE -> scoreModel.addPoints(IPHONE_SCORE * percentage);
            case LAMP -> scoreModel.addPoints(LAMP_SCORE * percentage);
            case DRESS -> scoreModel.addPoints(DRESS_SCORE * percentage);
        }
        PunktesystemView.updateScore(PunktesystemModel.getScore());
    }
}
