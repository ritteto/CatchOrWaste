package catchorwaste.model;

public class FallingObjectModel {
    private static long gameStartTime;

    public static long getGameStartTime() {
        return gameStartTime;
    }

    public static void setGameStartTime(long gameStartTime) {
        FallingObjectModel.gameStartTime = gameStartTime;
    }
}
