package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.dao.UserDao;
import com.questionsandanswers.questionsandanswers.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserDao userDao;

    @GetMapping("list")
    public List<User> userList(){
        return userDao.getUsers();
    }

    @GetMapping("search/{id}")
    public User search(@PathVariable long id){
        return userDao.user(id);
    }

    @PostMapping("create")
    public void create(@RequestBody User user){
        userDao.create(user);
    }

    @PutMapping("update")
    public void update(@RequestBody User user){
        userDao.update(user);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable long id){
        userDao.delete(id);
    }

}
