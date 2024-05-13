package catchorwaste.model.constants;

import com.almasb.fxgl.dsl.FXGL;

import java.util.HashMap;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public final class Constants {
    public static final int TOTAL_TIME_LIMIT_SECONDS = 180;
    public static double itemsPerSecond = 0.5; // Objekte pro Sekunde
    public static long lastSpawnTime = System.currentTimeMillis();
    public static final double STREET_RIGHT_END = getAppWidth() * 0.76;
    public static final double STREET_LEFT_END = getAppWidth() * 0.10;
    public static final double STREET_HEIGHT = getAppHeight() * 0.775;
    public static final double CURVE_BR = getAppWidth() * 0.841; // First curve to vertical on right side of street
    public static final double CURVE_BL = getAppWidth() * 0.035; // First curve to vertical on left side of street
    public static final double CURVE_TR = getAppHeight()*0.07; // Height of Curve on top to the village
    public static final double RECYCLE_HEIGHT = getAppHeight() * 0.6;
    public static final double GATE_HEIGHT = getAppHeight() * 0.665;
    public static final double GATE_LEFT_END = getAppWidth() * 0.785;
    public static final double GATE_RIGHT_END = getAppWidth() * 0.891;
    public static final double HOUSE_Y = getAppHeight() * 0.02;
    public static final double HOUSE1_X = getAppWidth() * 0.20;
    public static final double HOUSE2_X = getAppWidth() * 0.34;
    public static final double HOUSE3_X = getAppWidth() * 0.48;
    public static final double HOUSE4_X = getAppWidth() * 0.62;
    public static final double[] HOUSES = {HOUSE1_X, HOUSE2_X, HOUSE3_X, HOUSE4_X};

    public static final double CART_HEIGHT_AT_STREET = STREET_HEIGHT + FXGL.getAppHeight()*0.02;

    public static final double RECYCLE_X = getAppWidth() * 0.009;

    public static final double MARKT_X = getAppWidth() * 0.759;

    public static final double REPARIEREN_X = getAppWidth() * 0.866;

    public static final double WORKSTATION_RIGHT_Y = getAppHeight()*0.4;

    public static final int IPHONE_SCORE = 50;
    public static final int LAMP_SCORE = 20;
    public static final int DRESS_SCORE = 10;
    public static final String FONT_SIZE = "-fx-font-size: 30px;";
    public static final String START_SCREEN_IMG = "assets/textures/backgrounds/background_bad.png";

    public static final String TUTORIAL_SCREEN_IMG = "assets/textures/startScreen/tutorial-test-1.png";
    public static final String FONT = "/fonts/ArcadeFont.ttf";

    public static final Map<String, String> LEARNING_MESSAGES;
    static {
        LEARNING_MESSAGES = new HashMap<>();
        LEARNING_MESSAGES.put("Recycliere mich", "Recycling verringert die Notwendigkeit, neue Rohstoffe abzubauen, und schont somit natürliche Ressourcen wie Waelder, Mineralien und Wasser.");
        LEARNING_MESSAGES.put("Recycliere mich", "Durch Recycling wird weniger Muell produziert und die Belastung von Deponien reduziert, was zur Vermeidung von Umweltverschmutzung beitraegt.");
        LEARNING_MESSAGES.put("Recycliere mich", "Die Herstellung von Produkten aus recycelten Materialien erfordert oft weniger Energie als die Herstellung aus neuen Rohstoffen, was zu einer Verringerung der Treibhausgasemissionen fuehrt.");
        LEARNING_MESSAGES.put("Recycliere mich", "Recycling traegt zur Reduzierung von Treibhausgasemissionen bei, indem es die Menge an Abfall verbrennt oder deponiert, die sonst zur Freisetzung von Methan fuehren wuerde.");
        LEARNING_MESSAGES.put("Recycliere mich", "Recycling foerdert eine nachhaltige Wirtschaft, indem es Materialien zurueck in den Produktionskreislauf fuehrt und den Verbrauch von neuen Ressourcen reduziert.");

        LEARNING_MESSAGES.put("Kaufe second Hand!!", "Der Kauf gebrauchter Artikel verhindert, dass Gegenstaende auf Deponien landen, was zur Abfallvermeidung beiträgt und die Lebensdauer von Produkten verlaengert.");
        LEARNING_MESSAGES.put("Kaufe second Hand!!", "Second-Hand-Produkte sind oft guenstiger als neue Artikel, was es Verbrauchern ermoeglicht, Geld zu sparen, ohne auf Qualitaet zu verzichten.");
        LEARNING_MESSAGES.put("Kaufe second Hand!!", "Second-Hand-Einkäufe bieten oft einzigartige und seltene Gegenstände, die nicht mehr hergestellt werden, was zu individuellem Stil und Ausdruck führt.");
        LEARNING_MESSAGES.put("Kaufe second Hand!!", "Durch den Kauf gebrauchter Artikel werden weniger neue Produkte hergestellt, was dazu beitraegt, natuerliche Ressourcen zu schonen und den oekologischen Fussabdruck zu reduzieren.");
        LEARNING_MESSAGES.put("Kaufe second Hand!!", "Der Kauf bei Second-Hand-Laeden oder Flohmaerkten unterstuetzt oft lokale Geschaefte und gemeinnuetzige Organisationen, was zur Staerkung der Gemeinschaft beitraegt.");

    }
}
