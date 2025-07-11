package org.browserstack.assignment.exception;

public class NavigationFailedException extends RuntimeException {
    public NavigationFailedException(String message) {
        super(message);
    }
}
