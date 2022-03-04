package com.questionsandanswers.questionsandanswers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> requestException(RuntimeException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ErrorEntity> validationException(ValidationException e){
        e.getError().setMessage(e.getMessage());
        ResponseEntity responseEntity = ResponseEntity.status(e.getStatus()).body(e.getError());
        return responseEntity;
    }


}
