package com.example.order_system.exceptionHandler;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private String message;
    private HttpStatus httpStatus;
    private String exceptionType;
    private ZonedDateTime timestamp;

    public ApiException(String message, HttpStatus httpStatus, String exceptionType, ZonedDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.exceptionType = exceptionType;
        this.timestamp = timestamp;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
