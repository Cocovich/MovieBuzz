package be.ipl.android.moviebuzz.model;

public class JeuMaxPoints extends Jeu {

    protected int maxPoints = DEF_NOMBRE_POINTS; // limite de points

    public JeuMaxPoints(int maxPoints) {
        super();
        this.maxPoints = maxPoints;
    }

    @Override
    public boolean isGameFinished() {
        return points >= maxPoints;
    }
}
