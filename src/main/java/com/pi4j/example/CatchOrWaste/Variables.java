package com.pi4j.example.CatchOrWaste;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public class Variables {



    //Street variables
    static double STREET_LEFT = getAppWidth() * 0.08;
    static double STREET_RIGHT = getAppWidth() *0.85;

    //Player variables
    static double PLAYERSIZE = 3000*0.035;
    static double PLAYER_RIGHT = STREET_RIGHT -PLAYERSIZE;
    static double PLAYER_LEFT = STREET_LEFT;
    static int PLAYER_SPEED = 5;

    //Cart variables
    static int CART_SPEED = 2;
    static double CART_CURVE_BR = getAppWidth() * 0.825; // First curve to vertical on right side of street
    static double CART_CURVE_BL = getAppWidth() * 0.2; // First curve to vertical on left side of street
    static double CART_CURVE_TR = getAppHeight() * 0.2; // Height of Curve on top to the village
    static double SWITCH_RIGHT = getAppWidth()*0.875; // Kurve nach oben bei Links turn
    static double SWITCH_LEFT = getAppWidth()*0.77;// Kurve nach oben bei Links turn

    //Switch variables
    static double SWITCH_HEIGHT = getAppHeight()*0.32;

    //Falling objects variables
    static int FALLING_OBJECT_AMOUNT = 3;
}
