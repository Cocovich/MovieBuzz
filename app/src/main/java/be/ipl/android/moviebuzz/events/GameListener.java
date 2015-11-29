package be.ipl.android.moviebuzz.events;

import java.util.EventListener;

public interface GameListener extends EventListener {
    int GAME_TIMER_EVENT = 1;
    int QUESTION_TIMER_EVENT = 2;
    int GAME_FINISHED_EVENT = 3;

    void gameStateChanged(int event);
}
