package be.ipl.android.moviebuzz.exceptions;

public class EmptyGameException extends RuntimeException {
    public EmptyGameException() {
    }

    public EmptyGameException(String detailMessage) {
        super(detailMessage);
    }

    public EmptyGameException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public EmptyGameException(Throwable throwable) {
        super(throwable);
    }
}
