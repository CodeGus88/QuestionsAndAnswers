package com.questionsandanswers.questionsandanswers.exceptions.runtime_exception_childs;

import com.questionsandanswers.questionsandanswers.exceptions.ErrorModel;
import org.springframework.http.HttpStatus;

/**
 * Excepción personalizada, además del message, se adjunta el objeto Error y el status
 */
public class ValidationException extends RuntimeException{

    private ErrorModel error;
    private HttpStatus status;

    public ValidationException(String message, ErrorModel error, HttpStatus status) {
        super(message);
        this.error = error;
        this.status = status;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
