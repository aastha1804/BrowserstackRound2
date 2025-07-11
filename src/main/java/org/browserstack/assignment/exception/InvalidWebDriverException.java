package org.browserstack.assignment.exception;

public class InvalidWebDriverException extends RuntimeException {
    public InvalidWebDriverException(String message) {
        super(message);
    }
}