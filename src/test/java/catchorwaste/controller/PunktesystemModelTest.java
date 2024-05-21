package catchorwaste.controller;

import catchorwaste.model.PunktesystemModel;
import catchorwaste.view.PunktesystemView;
import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PunktesystemModelTest {

    @BeforeAll
    static void initJavaFX() {
        // Initialisiere die JavaFX-Laufzeitumgebung
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp() {
        // Vor jedem Test die Punktemap initialisieren
        PunktesystemModel.initPointsMap();

        // Manuelle Initialisierung von PunktesystemView.scoreLabel
        PunktesystemView.scoreLabel = new javafx.scene.control.Label();
    }

    @Test
    void testAddPoints() {
        int initialScore = PunktesystemModel.score;
        int pointsToAdd = 10;

        PunktesystemModel.addPoints(pointsToAdd);

        assertEquals(initialScore + pointsToAdd, PunktesystemModel.score);
    }

    @Test
    void testSubtractPoints() {
        int initialScore = PunktesystemModel.score;
        int pointsToSubtract = 5;

        PunktesystemModel.subtractPoints(pointsToSubtract);

        assertEquals(initialScore - pointsToSubtract, PunktesystemModel.score);
    }

    @Test
    void testInitPointsMap() {
        // Testet die Initialisierung der Punktemap mit leeren Eingabedaten
        PunktesystemModel.pointsMap = null;

        PunktesystemModel.initPointsMap();

        // Überprüft, ob die Punktemap korrekt initialisiert wurde
        assertEquals(4, PunktesystemModel.pointsMap.size());
        assertEquals(3, PunktesystemModel.pointsMap.get("recycle").size());
        assertEquals(3, PunktesystemModel.pointsMap.get("markt").size());
        assertEquals(3, PunktesystemModel.pointsMap.get("reparieren").size());
        assertEquals(3, PunktesystemModel.pointsMap.get("default").size());
    }

    @Test
    void testAddPointsNegativeValue() {
        // Testet das Hinzufügen von negativen Punkten zu einem Anfangswert von 0
        PunktesystemModel.score = 0;
        int pointsToAdd = -10;

        PunktesystemModel.addPoints(pointsToAdd);

        assertEquals(pointsToAdd, PunktesystemModel.score);
    }

    @Test
    void testSubtractPointsNegativeValue() {
        // Testet das Subtrahieren von negativen Punkten von einem Anfangswert von 0
        PunktesystemModel.score = 0;
        int pointsToSubtract = -5;

        PunktesystemModel.subtractPoints(pointsToSubtract);

        assertEquals(-pointsToSubtract, PunktesystemModel.score);
    }


}
