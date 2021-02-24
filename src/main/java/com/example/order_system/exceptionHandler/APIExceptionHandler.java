package com.example.order_system.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler( value = {BadRequestException.class} )
    public ResponseEntity<Object> handleBadRequestException( BadRequestException e ) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                "Bad Request",
                ZonedDateTime.now(ZoneId.of("Asia/Jerusalem"))
        );

        return new ResponseEntity<>(apiException, badRequest);

    }

    @ExceptionHandler( value = {InternalServerErrorException.class} )
    public ResponseEntity<Object> handleInternalServerErrorException( InternalServerErrorException e ) {

        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(
                e.getMessage(),
                internalServerError,
                "Internal Server Error",
                ZonedDateTime.now(ZoneId.of("Asia/Jerusalem"))
        );

        return new ResponseEntity<>(apiException, internalServerError);

    }



}
