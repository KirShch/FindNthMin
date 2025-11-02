package com.example.findNthMin.exception;

import com.example.findNthMin.ExceptionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class FindNthMinExceptionHandler {

    private ExceptionResponseDto getResponse(FindNthMinException ex, WebRequest request){
        return new ExceptionResponseDto(
                ex.getStatus().value(),
                ex.getStatus().getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );
    }

    @ExceptionHandler(IncorrectPath.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(IncorrectPath ex, WebRequest request) {
        return new ResponseEntity<>(getResponse(ex, request), ex.getStatus());
    }

    @ExceptionHandler(NOutOfBound.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(NOutOfBound ex, WebRequest request) {
        return new ResponseEntity<>(getResponse(ex, request), ex.getStatus());
    }

    @ExceptionHandler(FindNthMinIOException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(FindNthMinIOException ex, WebRequest request) {
        return new ResponseEntity<>(getResponse(ex, request), ex.getStatus());
    }
}
