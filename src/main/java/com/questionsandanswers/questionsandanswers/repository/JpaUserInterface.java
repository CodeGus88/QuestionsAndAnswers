package com.questionsandanswers.questionsandanswers.repository;


import com.questionsandanswers.questionsandanswers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaUserInterface extends JpaRepository<User, Long> {

    List<User> findByEmail(String id);

}
