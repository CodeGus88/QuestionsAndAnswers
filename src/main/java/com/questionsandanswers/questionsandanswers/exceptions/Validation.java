package com.questionsandanswers.questionsandanswers.exceptions;

import com.questionsandanswers.questionsandanswers.auth.security.services.UserDetailsImpl;
import com.questionsandanswers.questionsandanswers.exceptions.runtime_exception_childs.ValidationException;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.models.enums.ERole;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase valida la información que se obtiene del cliente
 * hace que se proceda o lanza una excepción
 */
public class Validation {

    public static void notFound(long id, boolean exist){
        if(!exist){
            throw new RuntimeException("Element with id: " + id + " not found!");
        }
    }

    /**
     * Genera Evalua si existen errores en los request
     * @param result
     */
    public static void generalValidations(BindingResult result){
        if(result.hasErrors()) {
            ErrorModel error = new ErrorModel();
            for (FieldError fieldError: result.getFieldErrors())
                error.putError(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            throw new ValidationException(
                    "BAD REQUEST",
                    error,
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    /**
     * Evalua los datos de entrada para editar y crear usuario
     * @param user
     * @param usernameExist
     * @param emailExist
     */
    public static void validateWhriteUserData(User user, boolean usernameExist, boolean emailExist){
        ErrorModel error = new ErrorModel();

        if(user.getUsername().equals("anonymousUser"))
            error.putError("Username: ''"+ user.getUsername() +"'' is invalid!");

        if(usernameExist)
            error.putError("Username: already exist!");

        if(!Tools.isEmail(user.getEmail()))
            error.putError("Email: is invalid!");
        else if(emailExist)
                error.putError("Email: already exists!");

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

    // VALIDACIÓN DE AUTORÍA

    /**
     * Verifica si es el autor del registro
     * @param authUser
     * @param authorId
     */
    public static void validateAuthor(UserDetailsImpl authUser, long authorId){
        ErrorModel error = new ErrorModel();
        List<String>userRoles = new ArrayList<>();
        for(GrantedAuthority grantedAuthority : authUser.getAuthorities())
            userRoles.add(grantedAuthority.getAuthority());
        if((authUser.getId() != authorId) && !userRoles.contains(ERole.ROLE_ADMIN.name())){
            error.putError("You do not have permissions");
        }

        if(!error.getErrors().isEmpty())
            throw new ValidationException("NOT ALLOWED", error, HttpStatus.UNAUTHORIZED);
    }

    public static boolean existRole(UserDetailsImpl authUser, ERole eRole){
        List<String>userRoles = new ArrayList<>();
        for(GrantedAuthority grantedAuthority : authUser.getAuthorities())
            userRoles.add(grantedAuthority.getAuthority());
        return userRoles.contains(eRole.name());
    }
}
