package com.questionsandanswers.questionsandanswers.repository;

import com.questionsandanswers.questionsandanswers.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaVoteInterface extends JpaRepository<Vote, Long> {

    @Query(value = "SELECT * FROM votes WHERE question_id = ?1 AND user_id = ?2 ", nativeQuery = true)
    Vote findByQuestionAndUserId(long questionId, long userId);

    @Query(value = "SELECT FROM votes WHERE question_id = ?1 ", nativeQuery = true)
    List<Vote> findVotesForQuestionId(long questionId);

    @Modifying
    @Query(value = "REMOVE FROM votes WHERE question_id = ?1 AND user_id = ?2 ", nativeQuery = true)
    List<Vote> removeVotesWithQuestionIdAndUserId(long questionId, long userId);

}
