package be.ipl.android.moviebuzz.model;

import android.os.CountDownTimer;

import java.util.ArrayList;
import java.util.PriorityQueue;

import be.ipl.android.moviebuzz.events.TimerEvent;
import be.ipl.android.moviebuzz.events.TimerListener;

public class Jeu extends PriorityQueue<Epreuve> {

    public static final int DEF_NOMBRE_EPREUVES = 15; // nombre d'épreuves max par défaut
    public static final int DEF_NOMBRE_POINTS = 1500; // nombre de points max par défaut
    public static final int DEF_DUREE_MAX = 90; // durée du jeu par défaut (en secondes)

    private ArrayList<TimerListener> listeners = new ArrayList<>();

    private boolean inProgress = false;
    private int questionCount = DEF_NOMBRE_EPREUVES;

    private int maxPoints = DEF_NOMBRE_POINTS;
    private int points = 0;

    private int gameMaxDuration = DEF_DUREE_MAX; // limite de temps en minutes
    private int gameRemainingTime; // durée actuelle de la partie (en millisecondes)
    private CountDownTimer gameTimer;

    private int questionRemainingTime;
    public static final int BUZZ_TIMER = 10; // temps par question (en secondes)
    private CountDownTimer questionTimer;

    public Jeu(int questionCount, int gameRemainingTime, int gamePoints) {
        this.questionCount = questionCount;
        this.gameMaxDuration = gameRemainingTime;
        this.gameRemainingTime = gameRemainingTime;
        this.maxPoints = gamePoints;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public int getQuestionRemainingTime() {
        return questionRemainingTime;
    }

    public int getGameRemainingTime() {
        return gameRemainingTime;
    }

    public int getPoints() {
        return points;
    }

    public boolean isInProgress() {
        return inProgress && ! isEmpty();
    }

    public void start() {
        inProgress = true;

        // Timer jeu
        gameTimer = new CountDownTimer(gameMaxDuration * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                gameRemainingTime = (int) millisUntilFinished / 1000;
                triggerEvent();
            }

            public void onFinish() {
                triggerEvent();
                //TODO qui a gagner dans le dialog machin
            }
            //
        }.start();

        // Timer question
        questionTimer = new CountDownTimer(BUZZ_TIMER * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                questionRemainingTime = (int) millisUntilFinished;
                triggerEvent();
            }

            @Override
            public void onFinish() {
                questionRemainingTime = 0;
                triggerEvent();
            }
        }.start();
    }

    public boolean buzz(String choice) {
        Epreuve epreuve = peek();
        boolean bonneReponse = epreuve.answer(choice);
        if (bonneReponse) {
            int answerTime = BUZZ_TIMER*1000 - questionRemainingTime; // temps de réponse
            points += epreuve.computePoints(answerTime, questionRemainingTime);
        }
        return bonneReponse;
    }

    public Epreuve getQuestion() {
        if (!isInProgress())
            return null;
        return peek();
    }

    public void nextQuestion() {
        questionTimer.cancel();
        questionTimer.start();
        poll();
    }

    public void finish() {
        this.inProgress = false;
    }

    public void addListener(TimerListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TimerListener listener) {
        listeners.remove(listener);
    }

    public void triggerEvent() {
        TimerEvent event = new TimerEvent(this);
        for (TimerListener listener : listeners)
            listener.timerChanged(event);
    }
}
