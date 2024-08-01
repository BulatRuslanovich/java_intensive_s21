package edu.school21.excaptions;

public class AlreadyAuthenticatedException extends RuntimeException {
    public AlreadyAuthenticatedException(String message) {
        super(message);
    }
}
