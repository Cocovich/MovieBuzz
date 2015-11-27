package be.ipl.android.moviebuzz.exceptions;

public class EndOfGameException extends RuntimeException {
    public EndOfGameException() {
    }

    public EndOfGameException(String detailMessage) {
        super(detailMessage);
    }

    public EndOfGameException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public EndOfGameException(Throwable throwable) {
        super(throwable);
    }
}
