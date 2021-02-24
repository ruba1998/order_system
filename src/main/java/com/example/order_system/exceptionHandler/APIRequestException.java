package com.example.order_system.exceptionHandler;

public class APIRequestException extends RuntimeException {

    public APIRequestException() {
        super();
    }

    public APIRequestException(String message) {
        super(message);
    }

    public APIRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
