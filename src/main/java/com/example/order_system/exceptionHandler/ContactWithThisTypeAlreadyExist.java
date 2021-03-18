package com.example.order_system.exceptionHandler;

public class ContactWithThisTypeAlreadyExist extends RuntimeException {

    public ContactWithThisTypeAlreadyExist() {
    }

    public ContactWithThisTypeAlreadyExist(String message) {
        super(message);
    }
}
