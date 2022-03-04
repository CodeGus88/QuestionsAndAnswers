package com.questionsandanswers.questionsandanswers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Esta clase Responde a las exceptiones
 */
@RestControllerAdvice
public class AdviceController {

    /**
     * Responde con el mensaje de la excepción not found
     * @param e
     * @return responEntity
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> requestException(RuntimeException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Responde a una excepción que hace referencia a la validación de los formularios
     * @param e
     * @return responEntity
     */
    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ErrorModel> validationException(ValidationException e){
        e.getError().setMessage(e.getMessage());
        ResponseEntity responseEntity = ResponseEntity.status(e.getStatus()).body(e.getError());
        return responseEntity;
    }

}
