package com.questionsandanswers.questionsandanswers.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ErrorEntity {

    private String message;
    private List<String> errors;

    public ErrorEntity(){
        errors = new ArrayList<>();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void putError(String error){
        errors.add(error);
    }

}
