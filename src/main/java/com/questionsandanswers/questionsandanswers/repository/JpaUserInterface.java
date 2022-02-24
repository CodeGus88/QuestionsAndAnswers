package com.questionsandanswers.questionsandanswers.repository;


import com.questionsandanswers.questionsandanswers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserInterface extends JpaRepository<User, Long> {


}
