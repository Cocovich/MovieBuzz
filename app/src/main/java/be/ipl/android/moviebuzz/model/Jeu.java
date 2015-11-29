package be.ipl.android.moviebuzz.model;

import android.os.CountDownTimer;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

import be.ipl.android.moviebuzz.events.GameListener;
import be.ipl.android.moviebuzz.exceptions.EmptyGameException;
import be.ipl.android.moviebuzz.exceptions.GameFinishedException;
import be.ipl.android.moviebuzz.exceptions.GameInProgressException;

public abstract class Jeu {

    public static final int MIN_EPREUVES = 5; // nombre d'épreuves min d'un jeu
    public static final int DEF_EPREUVES = 10; // nombre d'épreuves max par défaut
    public static final int MAX_EPREUVES = 50; // nombre d'épreuves max d'un jeu
    public static final int MIN_POINTS = 300; // nombre de points min d'un jeu
    public static final int DEF_POINTS = 1500; // nombre de points max par défaut
    public static final int MAX_POINTS = 5000; // nombre de points max d'un jeu
    public static final int MIN_DUREE = 30; // durée min d'un jeu (en secondes)
    public static final int DEF_DUREE = 90; // durée max du jeu par défaut (en secondes)
    public static final int MAX_DUREE = 300; // durée max d'un jeu (en secondes)

    protected ArrayList<GameListener> listeners = new ArrayList<>();

    protected DAO dao;

    protected String player; // nom du joueur

    // Epreuves
    protected Iterator<Epreuve> iterator;
    protected PriorityQueue<Epreuve> epreuves = new PriorityQueue<>();
    protected Epreuve epreuve;
    protected int nbReponses = 0; // nombre de réponses données
    protected int maxEpreuves = 0; // nombre d'épreuves à pré-charger ("0" signifie "tout")

    // Points
    protected int points = 0; // points total de la partie
    protected Timer gameTimer; // timer jeu

    // Durée jeu
    protected int gameTime = 0; // durée actuelle de la partie (en secondes)

    // Durée question
    public static final int BUZZ_TIMER = 10; // temps par question (en secondes)
    protected int questionRemainingTime; // temps restant question (en millisecondes)
    protected CountDownTimer questionTimer; // timer question

    public Jeu(DAO dao, String player) {
        this.dao = dao;
        this.player = player;
    }

    public void fillGame() {
        dao.open();
        dao.fillGame(this);
        dao.close();
    }

    public void saveStats() {
        if (! isGameFinished())
            throw new GameInProgressException();

        dao.open();
        dao.saveStats(this);
        dao.close();
    }

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
            throw new GameFinishedException();

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

    public int getNbReponses() {
        return nbReponses;
    }

    public int getNbBonnesReponses() {
        int nb = 0;
        Iterator<Epreuve> it = epreuves.iterator();
        for (int i = 0; i < nbReponses; i++) {
            if (it.next().isTrue())
                nb++;
        }
        return nb;
    }

    public int getNbMauvaisesReponses() {
        return getNbReponses() - getNbBonnesReponses();
    }

    public int getPoints() {
        return points;
    }

    public String getPlayer() {
        return player;
    }

    public void start() {

        if (epreuves.size() == 0)
            throw new EmptyGameException();

        // Timer question
        questionTimer = new CountDownTimer(BUZZ_TIMER * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                questionRemainingTime = (int) millisUntilFinished;
                triggerEvent(GameListener.QUESTION_TIMER_EVENT);
            }

            @Override
            public void onFinish() {
                questionRemainingTime = 0;
                triggerEvent(GameListener.QUESTION_TIMER_EVENT);
            }
        }.start();

        iterator = epreuves.iterator();
        epreuve = iterator.next();
    }

    public boolean buzz(String choice) {

        int answerTime = BUZZ_TIMER*1000 - questionRemainingTime; // temps de réponse
        boolean isTrue = epreuve.check(choice, answerTime, questionRemainingTime);

        points += epreuve.getPoints();
        nbReponses++;

        return isTrue;
    }

    public boolean isGameFinished() {
        return ! iterator.hasNext();
    }

    public void finish() {
        saveStats();
    }

    public void addListener(GameListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameListener listener) {
        listeners.remove(listener);
    }

    public void triggerEvent(int event) {
        for (GameListener listener : listeners)
            listener.gameStateChanged(event);
    }

    protected void startGameTimer() {
        // Timer jeu
        gameTimer = new Timer("Timer jeu");
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!isGameFinished()) {
                    gameTime += 1;
                    updateGameTimer();
                } else {
                    cancel();
                }
            }
        }, 1000, 1000);
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
            triggerEvent(GameListener.GAME_TIMER_EVENT);
        }
    };
}
