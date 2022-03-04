package com.questionsandanswers.questionsandanswers.exceptions;

import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.models.User;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class Validation {

    public static void notFound(long id, boolean isEmpty){
        if(isEmpty){
            throw new RuntimeException("Element with id: " + id + " not found");
        }
    }

    public static void validateWhriteUserData(User user, List<User> emailWithMatches, final boolean IS_NEW_USER){
        ErrorEntity error = new ErrorEntity();
        if(user.getFullName().isEmpty())
            error.putError("Fullname is required");

        if(user.getEmail().isEmpty())
            error.putError("Email is required");
        else if(!Tools.isEmail(user.getEmail()))
            error.putError("Email is invalid");
        else if(!emailWithMatches.isEmpty()){
            if(IS_NEW_USER)
                error.putError("Email already exists");
            else if(emailWithMatches.get(0).getId() != user.getId())
                error.putError("Email already exists");
        }

        if(user.getPassword().isEmpty())
            error.putError("Password is required");

        if(!error.getErrors().isEmpty())
            throw new ValidationException("ERROR IN THE FORM", error, HttpStatus.BAD_REQUEST);
    }

    public static void validateWhriteQuestionData(Question question){
        ErrorEntity error = new ErrorEntity();
        if(question.getTitle().isEmpty())
            error.putError("Title is required");

        if(question.getBody().isEmpty())
            error.putError("Body is required");

        if(question.getTags().isEmpty())
            error.putError("Tags is required");

        if(question.getUser().getId() < 0)
            error.putError("User is required");

        if(!error.getErrors().isEmpty())
            throw new ValidationException("ERROR IN THE FORM", error, HttpStatus.BAD_REQUEST);
    }

}
