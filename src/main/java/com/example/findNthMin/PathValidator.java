package com.example.findNthMin;

import com.example.findNthMin.exception.IncorrectPath;
import org.springframework.stereotype.Component;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

@Component
public class PathValidator {

    public void validPath(String path){
        try {
            Paths.get(path);
        } catch (InvalidPathException e) {
            throw new IncorrectPath(e.getMessage());
        }
    }
}
