package com.pi4j.example.CatchOrWaste;

import static com.pi4j.example.CatchOrWaste.FxglTest.AppWidth;

public class Variables {



    //Street Variables
    static double STREET_LEFT = 0;
    static double STREET_RIGHT = AppWidth *0.85;

    //Player Variables
    static double PLAYERSIZE = 3000*0.035;
    static double PLAYER_RIGHT = STREET_RIGHT -PLAYERSIZE;
    static double PLAYER_LEFT = STREET_LEFT;
    static int SPEED = 5;
}
