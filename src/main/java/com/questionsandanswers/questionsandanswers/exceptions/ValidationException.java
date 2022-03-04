package com.questionsandanswers.questionsandanswers.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException{

    private ErrorEntity error;
    private HttpStatus status;

    public ValidationException(String message, ErrorEntity error, HttpStatus status) {
        super(message);
        this.error = error;
        this.status = status;
    }

    public ErrorEntity getError() {
        return error;
    }

    public void setError(ErrorEntity error) {
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
