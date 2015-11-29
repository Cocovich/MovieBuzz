package be.ipl.android.moviebuzz.model;

public class JeuMaxPoints extends Jeu {

    protected int maxPoints = DEF_POINTS; // limite de points

    public JeuMaxPoints(DAO dao, String player, int maxPoints) {
        super(dao, player);
        this.maxPoints = maxPoints;
    }

    @Override
    public void start() {
        super.start();

        startGameTimer();
    }

    @Override
    public boolean isGameFinished() {
        return super.isGameFinished() || points >= maxPoints;
    }
}
