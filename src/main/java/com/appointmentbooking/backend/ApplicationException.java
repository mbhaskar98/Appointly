package com.appointmentbooking.backend;

public class ApplicationException extends Exception {

    // Constructor with no arguments
    public ApplicationException() {
        super();
    }

    // Constructor that accepts a message
    public ApplicationException(String message) {
        super(message);
    }

    // Constructor that accepts a message and a cause
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public ApplicationException(Throwable cause) {
        super(cause);
    }
}
