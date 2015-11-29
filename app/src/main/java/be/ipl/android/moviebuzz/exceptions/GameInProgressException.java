package be.ipl.android.moviebuzz.exceptions;

public class GameInProgressException extends RuntimeException {
    public GameInProgressException() {
    }

    public GameInProgressException(String detailMessage) {
        super(detailMessage);
    }

    public GameInProgressException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public GameInProgressException(Throwable throwable) {
        super(throwable);
    }
}
