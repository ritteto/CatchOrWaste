package CatchOrWaste.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;

public class TimerModel {
    private final IntegerProperty totalSeconds = new SimpleIntegerProperty(0);

    public int getTotalSeconds() {
        return totalSeconds.get();
    }

    public void setTotalSeconds(int seconds) {
        totalSeconds.set(seconds);
    }

    public int getMinutes() {
        return getTotalSeconds() / 60;
    }

    public int getSeconds() {
        return getTotalSeconds() % 60;
    }
}
