package be.ipl.android.moviebuzz.model;

public class JeuMaxEpreuves extends Jeu {

    public JeuMaxEpreuves(DAO dao, String player, int maxEpreuves) {
        super(dao, player);
        this.maxEpreuves = maxEpreuves;
    }

    @Override
    public void start() {
        super.start();

        startGameTimer();
    }

    @Override
    public boolean isGameFinished() {
        return super.isGameFinished() || nbReponses >= maxEpreuves;
    }
}
