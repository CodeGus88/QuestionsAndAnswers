package com.questionsandanswers.questionsandanswers.exceptions.runtime_exception_childs;

import org.springframework.http.HttpStatus;

/**
 * Clase para el uso de excepciones generales con solo un mensaje
 */
public class GeneralException extends RuntimeException{

    private HttpStatus status;

    public GeneralException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
