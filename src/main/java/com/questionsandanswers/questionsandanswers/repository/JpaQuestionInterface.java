package com.questionsandanswers.questionsandanswers.repository;

import com.questionsandanswers.questionsandanswers.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaQuestionInterface extends JpaRepository<Question, Long> {

    List<Question> findByUserId(long userId);

}
