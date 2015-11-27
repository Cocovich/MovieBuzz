package be.ipl.android.moviebuzz.events;

import java.util.EventObject;

public class TimerEvent extends EventObject {
    /**
     * Constructs a new instance of this class.
     *
     * @param source the object which fired the event.
     */
    public TimerEvent(Object source) {
        super(source);
    }
}
