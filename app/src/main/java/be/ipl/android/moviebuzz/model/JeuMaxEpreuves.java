package be.ipl.android.moviebuzz.model;

public class JeuMaxEpreuves extends Jeu {

    public JeuMaxEpreuves(int maxEpreuves) {
        this.maxEpreuves = maxEpreuves;
    }

    @Override
    public void start() {
        super.start();

        startGameTimer();
    }

    @Override
    public boolean isGameFinished() {
        return nbEpreuves >= maxEpreuves;
    }
}
