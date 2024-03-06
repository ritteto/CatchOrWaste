package code.model.Constants;

import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.almasb.fxgl.dsl.FXGL.getAppWidth;

public final class Constants {

    //Player
    public static double PLAYERSIZE = 84; //800*0.105

    //Cart
    public static final double STREET_RIGHT_END = getAppWidth()*0.85-PLAYERSIZE;
    public static final double STREET_LEFT_END = getAppWidth() * 0.08;
    public static final double STREET_HEIGHT = getAppHeight() * 0.775;

    public static final double CURVE_BR = getAppWidth() * 0.8175; // First curve to vertical on right side of street
    public static final double CURVE_BL = getAppWidth() * 0.01; // First curve to vertical on left side of street
    public static final double CURVE_TR = getAppHeight() * 0.2; // Height of Curve on top to the village

    public static final double RECYCLE_HEIGHT = getAppHeight()*0.6;

    public static final double GATE_HEIGHT = getAppHeight()*0.32;
    public static final double GATE_LEFT_END = getAppWidth() * 0.76;
    public static final double GATE_RIGHT_END = getAppWidth() * 0.8675;

    //Falling Objects



    //Background
    public static final double HOUSE4_X = getAppWidth()*0.62;
    public static final double HOUSE3_X = 1;
}
