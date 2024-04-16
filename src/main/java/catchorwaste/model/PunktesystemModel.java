package catchorwaste.model;

public class PunktesystemModel {
    private double score;

    public PunktesystemModel() {
        this.score = 0;
    }

    public double getScore() {
        return score;
    }

    public void addPoints(double points) {
        score += points;
    }

    public void subtractPoints(double points) {
        score -= points;
        if (score < 0) {
            score = 0; // Verhindert negative Punktzahlen
        }
    }
}
