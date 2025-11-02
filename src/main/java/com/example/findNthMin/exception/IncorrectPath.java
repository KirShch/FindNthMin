package com.example.findNthMin.exception;

import org.springframework.http.HttpStatus;

public class IncorrectPath extends FindNthMinException{
    public IncorrectPath(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
