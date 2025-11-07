package com.example.findNthMin.exception;

import org.springframework.http.HttpStatus;

public class NOutOfBoundException extends FindNthMinException{
    public NOutOfBoundException(String message){ super(message, HttpStatus.NOT_ACCEPTABLE); }
}
