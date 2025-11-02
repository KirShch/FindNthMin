package com.example.findNthMin.exception;

import org.springframework.http.HttpStatus;

public class FindNthMinException extends RuntimeException{
    private final HttpStatus status;

    public FindNthMinException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}