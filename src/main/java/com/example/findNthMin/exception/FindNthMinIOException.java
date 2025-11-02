package com.example.findNthMin.exception;

import org.springframework.http.HttpStatus;

public class FindNthMinIOException extends FindNthMinException{
    public FindNthMinIOException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
