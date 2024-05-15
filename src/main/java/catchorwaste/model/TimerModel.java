package catchorwaste.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

public class TimerModel {
    private static final IntegerProperty totalSeconds = new SimpleIntegerProperty(0);

    public static int getTotalSeconds() {
        return totalSeconds.get();
    }

    public static void setTotalSeconds(int seconds) {
        totalSeconds.set(seconds);
    }

    public static int getMinutes() {
        return getTotalSeconds() / 60;
    }

    public static int getSeconds() {
        return getTotalSeconds() % 60;
    }
}
