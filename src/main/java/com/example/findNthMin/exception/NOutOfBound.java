package com.example.findNthMin.exception;

import org.springframework.http.HttpStatus;

public class NOutOfBound extends FindNthMinException{
    public NOutOfBound(String message){ super(message, HttpStatus.NOT_ACCEPTABLE); }
}
