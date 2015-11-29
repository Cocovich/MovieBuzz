package be.ipl.android.moviebuzz.model;

import android.os.CountDownTimer;

import be.ipl.android.moviebuzz.events.GameListener;

public class JeuContreMontre extends Jeu {

    protected int gameMaxDuration = DEF_DUREE; // limite de temps (en secondes)

    protected CountDownTimer gameRemainingTimer;

    public JeuContreMontre(DAO dao, String player, int duration) {
        super(dao, player);
        this.gameMaxDuration = duration;
    }

    @Override
    public void start() {
        super.start();

        gameTime = -1; // Pour compenser la 1ère éxécution (immédiate) du countdown.start()

        // Timer jeu
        gameRemainingTimer = new CountDownTimer(gameMaxDuration * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                gameTime += 1;
                triggerEvent(GameListener.GAME_TIMER_EVENT);
            }

            public void onFinish() {
                gameTime = gameMaxDuration;
                triggerEvent(GameListener.GAME_TIMER_EVENT);
                triggerEvent(GameListener.GAME_FINISHED_EVENT);
            }
        }.start();
    }

    public int getGameRemainingTime() {
        return gameMaxDuration - gameTime;
    }

    @Override
    public boolean isGameFinished() {
        return super.isGameFinished() || gameTime >= gameMaxDuration;
    }
}
