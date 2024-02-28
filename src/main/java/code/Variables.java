package code;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public class Variables {



    //Street variables
    static double STREET_LEFT = getAppWidth() * 0.08;
    static double STREET_RIGHT = getAppWidth() *0.85;

    //Player variables
    static double PLAYERSIZE = 84; //800*0.105
    static double PLAYER_RIGHT = STREET_RIGHT -PLAYERSIZE;
    static double PLAYER_LEFT = STREET_LEFT;
    static int PLAYER_SPEED = 5;

    //Cart variables
    static int CART_SPEED = 2;
    static double CART_SIZE = 860*0.105;
    static double CART_CURVE_BR = getAppWidth() * 0.8175; // First curve to vertical on right side of street
    static double CART_CURVE_BL = getAppWidth() * 0.01; // First curve to vertical on left side of street
    static double CART_CURVE_TR = getAppHeight() * 0.2; // Height of Curve on top to the village

    static double CART_SWITCH_HEIGHT = getAppHeight()*0.32;

    static double CART_SWITCH_LENGTH_RIGHT = getAppWidth() * 0.8675;// Kurve nach oben bei rechts turn
    static double CART_SWITCH_LENGTH_LEFT = getAppWidth() * 0.76; // Kurve nach oben bei Links turn

    static double CART_CURVE_RIGHT_HEIGHT = getAppHeight()*0.00000000001;
    static double CART_CURVE_LEFT_HEIGHT = getAppHeight()*0.1;



    //Switch variables
    static double SWITCH_HEIGHT = getAppHeight()*0.32; // Höhe der Weiche
    static double SWITCH_RIGHT = getAppWidth() * 0.875; // Kurve nach oben bei Links turn
    static double SWITCH_LEFT = getAppWidth() * 0.77;// Kurve nach oben bei Links turn

    //Falling objects variables
    static int FALLING_OBJECT_AMOUNT = 3;
    static double FO_SIZE = 860 *0.07;

    static double HOUSE1_X = getAppWidth()*0.62;
    static double HOUSE1_Y = getAppHeight()*-0.001;
    static double HOUSE2_X = getAppWidth()*0.48;
    static double HOUSE2_Y = getAppHeight()*-0.001;
    static double HOUSE3_X = getAppWidth()*0.34;
    static double HOUSE3_Y = getAppHeight()*-0.001;
    static double HOUSE4_X = getAppWidth()*0.20;
    static double HOUSE4_Y = getAppHeight()*-0.001;

}
