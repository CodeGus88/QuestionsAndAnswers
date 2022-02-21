package com.questionsandanswers.questionsandanswers.dao;

import com.questionsandanswers.questionsandanswers.models.User;

import java.util.List;

public interface UserDao {

    List<User> getUsers();
    User user(Long id);
    void create(User user);
    void update(User user);
    void delete(Long id);

}
