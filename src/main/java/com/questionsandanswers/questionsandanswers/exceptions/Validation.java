package com.questionsandanswers.questionsandanswers.exceptions;

import com.questionsandanswers.questionsandanswers.exceptions.runtime_exception_childs.ValidationException;
import com.questionsandanswers.questionsandanswers.models.Answer;
import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.models.User;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Esta clase valida la información que se obtiene del cliente
 * hace que se proceda o lanza una excepción
 */
public class Validation {

    public static void notFound(long id, boolean exist){
        if(!exist){
            throw new RuntimeException("Element with id: " + id + " not found");
        }
    }

    /**
     * Evalua los datos de entrada para editar y crear usuario
     * @param user
     * @param emailWithMatches
     * @param IS_NEW_USER
     */
    public static void validateWhriteUserData(User user, List<User> emailWithMatches, final boolean IS_NEW_USER){
        ErrorModel error = new ErrorModel();
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

    /**
     * Evalua los datos de entrada para editar y crear pregunta
     * @param question
     */
    public static void validateWhriteQuestionData(Question question){
        ErrorModel error = new ErrorModel();
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


    /**
     * Evalua los datos de entrada para editar y crear respuesta
     * @param answer
     */
    public static void validateWhriteAnswerData(Answer answer){
        ErrorModel error = new ErrorModel();
        if(answer.getBody().isEmpty())
            error.putError("Body is required");

        if(!error.getErrors().isEmpty())
            throw new ValidationException("ERROR IN THE FORM", error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Evalua los datos de entrada para votar o eliminar una respuesta
     * @param exist
     */
    public static void validateWhriteVote(boolean exist){
        ErrorModel error = new ErrorModel();
        if(exist)
            error.putError("The user already voted for this element");

        if(!error.getErrors().isEmpty())
            throw new ValidationException("ERROR IN THE JSON BODY", error, HttpStatus.BAD_REQUEST);
    }
}
