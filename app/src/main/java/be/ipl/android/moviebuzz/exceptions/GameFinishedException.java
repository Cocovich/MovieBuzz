package be.ipl.android.moviebuzz.exceptions;

public class GameFinishedException extends RuntimeException {
    public GameFinishedException() {
    }

    public GameFinishedException(String detailMessage) {
        super(detailMessage);
    }

    public GameFinishedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public GameFinishedException(Throwable throwable) {
        super(throwable);
    }
}
