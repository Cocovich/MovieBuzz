package be.ipl.android.moviebuzz.events;

import java.util.EventListener;

public interface TimerListener extends EventListener {
    void timerChanged(TimerEvent event);
    void endOfTimer(TimerEvent event);
}
