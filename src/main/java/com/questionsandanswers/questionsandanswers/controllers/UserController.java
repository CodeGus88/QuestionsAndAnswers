package com.questionsandanswers.questionsandanswers.controllers;

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
public class UserController {

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

    @PutMapping("update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> update(@RequestBody User user){

        ResponseEntity<UserDto> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                userService.updateUser(user)
        );
        return responseEntity;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable long id){
        userService.deleteUser(id);
    }


}
