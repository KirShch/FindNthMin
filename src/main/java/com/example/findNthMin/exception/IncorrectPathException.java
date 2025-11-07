package com.example.findNthMin.exception;

import org.springframework.http.HttpStatus;

public class IncorrectPathException extends FindNthMinException{
    public IncorrectPathException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
