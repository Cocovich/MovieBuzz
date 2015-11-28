package be.ipl.android.moviebuzz.model;

import android.os.CountDownTimer;

public class JeuContreMontre extends Jeu {

    protected int gameMaxDuration = DEF_DUREE; // limite de temps (en secondes)

    protected CountDownTimer gameRemainingTimer;

    public JeuContreMontre(int duration) {
        super();
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
                triggerEvent(TIMER_EVENT);
            }

            public void onFinish() {
                gameTime = gameMaxDuration;
                triggerEvent(TIMER_EVENT);
                triggerEvent(END_GAME_EVENT);
            }
        }.start();
    }

    public int getGameRemainingTime() {
        return gameMaxDuration - gameTime;
    }

    @Override
    public boolean isGameFinished() {
        return gameTime >= gameMaxDuration;
    }
}
