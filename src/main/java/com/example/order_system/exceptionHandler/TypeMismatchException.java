package com.example.order_system.exceptionHandler;

public class TypeMismatchException extends RuntimeException {

    public TypeMismatchException() {
    }

    public TypeMismatchException(String message) {
        super(message);
    }
}
