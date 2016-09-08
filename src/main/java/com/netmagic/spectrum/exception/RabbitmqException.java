package com.netmagic.spectrum.exception;

/**
 * Signals that the exception thrown when their is Rabbit connection refused by
 * the system.
 * 
 * @author Sudharani
 *
 */

public class RabbitmqException extends RuntimeException {

    private static final long serialVersionUID = -7249033387737784448L;

    public RabbitmqException(String message) {
        super(message);
    }

    public RabbitmqException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public RabbitmqException(Throwable throwable) {
        super(throwable);
    }
}
