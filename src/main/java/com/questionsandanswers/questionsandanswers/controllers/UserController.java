package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.dto.UserDto;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador para la entidad User (usuarios)
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/users")
public class UserController extends MainClassController{

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> userList(){
        ResponseEntity<List<UserDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                userService.getUserList()
        );
        return responseEntity;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> search(@PathVariable long id){
        ResponseEntity<UserDto> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                userService.getUser(id)
        );
        return responseEntity;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable long id){
        userService.deleteUser(id);
    }

    /**
     * Verifica que sea el autor del registro antes de editar o eliminar
     * @param userId
     */
    private void check(long userId){
        Validation.validateAuthor(
                getUserDetailsImp(),
                userId
        );
    }
}
