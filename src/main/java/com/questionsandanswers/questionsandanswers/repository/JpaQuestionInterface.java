package com.questionsandanswers.questionsandanswers.repository;

import com.questionsandanswers.questionsandanswers.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface JpaQuestionInterface extends JpaRepository<Question, Long> {

    public List<Question> findByUserId(long userId);

    @Query(value = "SELECT * " +
            "FROM questions q " +
            "WHERE q.create_date BETWEEN ?1 and ?2", nativeQuery = true)
    public List<Question> findByCreateDateBetween(ZonedDateTime start, ZonedDateTime end);

    @Query(value = "SELECT * " +
            "FROM questions q " +
            "WHERE " +
            "q.title LIKE %?1% OR " +
            "q.body LIKE %?1% OR " +
            "q.tags LIKE %?1% ", nativeQuery = true)
    public List<Question> searchMatches(String search);
}
