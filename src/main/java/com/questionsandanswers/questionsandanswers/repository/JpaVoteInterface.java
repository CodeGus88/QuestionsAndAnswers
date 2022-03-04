package com.questionsandanswers.questionsandanswers.repository;

import com.questionsandanswers.questionsandanswers.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repositorio para Vote
 */
public interface JpaVoteInterface extends JpaRepository<Vote, Long> {

    /**
     * Busca el voto de un usuario en una pregunta
     * @param questionId
     * @param userId
     * @return vote
     */
    @Query(value = "SELECT * FROM votes WHERE question_id = ?1 AND user_id = ?2 ", nativeQuery = true)
    Vote findByQuestionAndUserId(long questionId, long userId);

    /**
     * Busca los votos de una pregunta
     * @param questionId
     * @return voteList
     */
    @Query(value = "SELECT FROM votes WHERE question_id = ?1 ", nativeQuery = true)
    List<Vote> findVotesForQuestionId(long questionId);

    /**
     * Elimina el voto de un usuario en una pregunta
     * @param questionId
     * @param userId
     * @return voteList
     */
    @Modifying
    @Query(value = "REMOVE FROM votes WHERE question_id = ?1 AND user_id = ?2 ", nativeQuery = true)
    List<Vote> removeVotesWithQuestionIdAndUserId(long questionId, long userId);

}
