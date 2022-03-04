package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.services.dto.UserDto;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> userList(){
        return userService.getUserList();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> search(@PathVariable long id){
        return userService.getUser(id);
    }

    @PostMapping("create")
    public ResponseEntity<UserDto> create(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping("update")
    public ResponseEntity<UserDto> update(@RequestBody User user){
        return userService.updateUser(user);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable long id){
        userService.deleteUser(id);
    }

}
