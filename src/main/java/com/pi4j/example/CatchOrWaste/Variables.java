package com.pi4j.example.CatchOrWaste;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public class Variables {



    //Street variables
    static double STREET_LEFT = 0;
    static double STREET_RIGHT = getAppWidth() *0.85;

    //Player variables
    static double PLAYERSIZE = 3000*0.035;
    static double PLAYER_RIGHT = STREET_RIGHT -PLAYERSIZE;
    static double PLAYER_LEFT = STREET_LEFT;
    static int PLAYER_SPEED = 5;

    //Cart variables
    static int CART_SPEED = 2;

    //Switch variables
    static double SWITCH_HEIGHT = getAppHeight()*0.59;
}
