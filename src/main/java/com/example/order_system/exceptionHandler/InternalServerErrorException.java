package com.example.order_system.exceptionHandler;

public class InternalServerErrorException extends RuntimeException{

    public InternalServerErrorException() {
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
