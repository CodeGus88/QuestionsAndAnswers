package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.dao.UserDao;
import com.questionsandanswers.questionsandanswers.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> userList(){

        return userDao.getUsers();
    }

    @RequestMapping(value = "api/user/{id}", method = RequestMethod.GET)
    public User search(@PathVariable long id){
        return userDao.user(id);
    }




    //    public void create(@RequestBody User user){
    @RequestMapping(value = "api/user/create", method = RequestMethod.POST)
    public void create(@RequestParam String fulname,
                       @RequestParam String email, @RequestParam String password){
        User user = new User();
        user.setId(-1L);
        user.setFulName(fulname);
        user.setEmail(email);
        user.setPassword(password);
        userDao.create(user);
    }

    //    public void opdate(@RequestBody User user){
    @RequestMapping(value = "api/user/update", method = RequestMethod.PUT)
    public void update(@RequestParam Long id, @RequestParam String fulname,
                       @RequestParam String email, @RequestParam String password){

        userDao.update(new User(id, fulname, email, password));
    }

    @RequestMapping(value = "api/user/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id){
        userDao.delete(id);
    }

}
