package org.browserstack.assignment.exception;

public class ScreenshotFailedException extends RuntimeException {
    public ScreenshotFailedException(String message) {
        super(message);
    }
}
