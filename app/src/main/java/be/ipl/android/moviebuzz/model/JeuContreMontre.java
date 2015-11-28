package be.ipl.android.moviebuzz.model;

public class JeuContreMontre extends Jeu {

    protected int gameMaxDuration = DEF_DUREE; // limite de temps (en secondes)

    public JeuContreMontre(int duration) {
        super();
        this.gameMaxDuration = duration;
    }

    public int getGameRemainingTime() {
        return gameMaxDuration - gameTime;
    }

    @Override
    public boolean isGameFinished() {
        return gameTime >= gameMaxDuration;
    }
}
