package be.ipl.android.moviebuzz.model;

import android.os.CountDownTimer;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

import be.ipl.android.moviebuzz.events.TimerEvent;
import be.ipl.android.moviebuzz.events.TimerListener;
import be.ipl.android.moviebuzz.exceptions.EmptyGameException;
import be.ipl.android.moviebuzz.exceptions.EndOfGameException;

public abstract class Jeu {

    protected static final int TIMER_EVENT = 1;
    protected static final int END_GAME_EVENT = 2;

    public static final int DEF_NOMBRE_EPREUVES = 10; // nombre d'épreuves max par défaut
    public static final int DEF_NOMBRE_POINTS = 1500; // nombre de points max par défaut
    public static final int DEF_DUREE = 90; // durée max du jeu par défaut (en secondes)

    protected ArrayList<TimerListener> listeners = new ArrayList<>();

    // Epreuves
    protected Iterator<Epreuve> iterator;
    protected PriorityQueue<Epreuve> epreuves = new PriorityQueue<>();
    protected Epreuve epreuve;
    protected int nbEpreuves = 0; // Nombre d'épreuves passées
    protected int maxEpreuves = 0; // Nombre d'épreuves à pré-charger ("0" signifie "tout")

    // Points
    protected int points = 0; // points total de la partie

    // Durée jeu
    protected int gameTime = 0; // durée actuelle de la partie (en secondes)
    protected Timer gameTimer; // timer jeu

    // Durée question
    public static final int BUZZ_TIMER = 10; // temps par question (en secondes)
    protected int questionRemainingTime; // temps restant question (en millisecondes)
    protected CountDownTimer questionTimer; // timer question

    public void addEpreuve(Epreuve epreuve) {
        epreuves.add(epreuve);
    }

    public void removeEpreuve(Epreuve epreuve) {
        epreuves.remove(epreuve);
    }

    public PriorityQueue<Epreuve> getEpreuves() {
        return epreuves;
    }

    public Epreuve getEpreuve() {
        return epreuve;
    }

    public Epreuve nextEpreuve() {
        questionTimer.cancel();

        if (iterator.hasNext())
            epreuve = iterator.next();
        if (isGameFinished())
            throw new EndOfGameException();

        questionTimer.start();

        return epreuve;
    }

    public int getMaxEpreuves() {
        return maxEpreuves;
    }

    public int getGameTime() {
        return gameTime;
    }

    public int getQuestionRemainingTime() {
        return questionRemainingTime;
    }

    public int getNbEpreuves() {
        return nbEpreuves;
    }

    public int getPoints() {
        return points;
    }

    public void start() {

        if (epreuves.size() == 0)
            throw new EmptyGameException();

        // Timer jeu
        gameTimer = new Timer("Timer jeu");
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isGameFinished()) {
                    gameTime += 1;
                    updateGameTimer();
                }
                else
                    cancel();
            }
        }, 1000, 1000);

        // Timer question
        questionTimer = new CountDownTimer(BUZZ_TIMER * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                questionRemainingTime = (int) millisUntilFinished;
                triggerEvent(TIMER_EVENT);
            }

            @Override
            public void onFinish() {
                questionRemainingTime = 0;
                triggerEvent(TIMER_EVENT);
            }
        }.start();

        iterator = epreuves.iterator();
        epreuve = iterator.next();
    }

    public boolean buzz(String choice) {

        nbEpreuves++;
        boolean isTrue = epreuve.check(choice);

        if (isTrue) {
            // Calcul points
            int answerTime = BUZZ_TIMER*1000 - questionRemainingTime; // temps de réponse
            points += epreuve.computePoints(answerTime, questionRemainingTime);
        }

        return isTrue;
    }

    public abstract boolean isGameFinished();

    public boolean isWon() {
        return true;
    }

    public void addListener(TimerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TimerListener listener) {
        listeners.remove(listener);
    }

    public void triggerEvent(int eventType) {
        TimerEvent event = new TimerEvent(this);
        for (TimerListener listener : listeners) {
            switch (eventType) {
                case TIMER_EVENT:
                    listener.timerChanged(event);
                    break;
                case END_GAME_EVENT:
                    listener.endOfTimer(event);
                    break;
            }
        }
    }

    /* Code pour màj le timer de jeu
     * @link http://www.techsono.com/consult/update-android-gui-timer/
     */
    final Handler myHandler = new Handler(); // handler pour màj le timer de jeu dans l'activité

    public void updateGameTimer() {
        myHandler.post(myRunnable);
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            triggerEvent(TIMER_EVENT);
        }
    };
}
