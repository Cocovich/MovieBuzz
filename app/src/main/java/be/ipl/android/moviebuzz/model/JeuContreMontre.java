package be.ipl.android.moviebuzz.model;

import android.os.CountDownTimer;

public class JeuContreMontre extends Jeu {

    protected int gameMaxDuration = DEF_DUREE; // limite de temps (en secondes)
    protected int gameRemainingTime;

    protected CountDownTimer gameRemainingTimer;

    public JeuContreMontre(int duration) {
        super();
        this.gameMaxDuration = duration;
        this.gameRemainingTime = duration;
    }

    @Override
    public void start() {
        super.start();

        // Timer jeu
        gameRemainingTimer = new CountDownTimer(gameMaxDuration * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                gameRemainingTime = (int) millisUntilFinished / 1000;
                triggerEvent(TIMER_EVENT);
            }

            public void onFinish() {
                gameRemainingTime = 0;
                triggerEvent(TIMER_EVENT);
                triggerEvent(END_GAME_EVENT);
            }
        }.start();
    }

    public int getGameRemainingTime() {
        return gameRemainingTime;
    }

    @Override
    public boolean isGameFinished() {
        return gameRemainingTime == 0;
    }
}
