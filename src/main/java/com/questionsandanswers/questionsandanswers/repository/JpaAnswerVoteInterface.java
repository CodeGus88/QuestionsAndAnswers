package com.questionsandanswers.questionsandanswers.repository;

import com.questionsandanswers.questionsandanswers.models.AnswerVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Repositorio para Vote
 */
public interface JpaAnswerVoteInterface extends JpaRepository<AnswerVote, Long> {

    /**
     * Busca el voto de un usuario en una pregunta
     * @param answerId
     * @param userId
     * @return vote
     */
    @Query(value = "SELECT * FROM answer_votes WHERE answer_id = ?1 AND user_id = ?2 ", nativeQuery = true)
    AnswerVote findByAnswerAndUserId(long answerId, long userId);

    /**
     * Busca los votos de una pregunta
     * @param answerId
     * @return voteList
     */
    @Query(value = "SELECT FROM answer_votes WHERE answer_id = ?1 ", nativeQuery = true)
    List<AnswerVote> findVotesForAnswerId(long answerId);

    /**
     * Elimina el voto de un usuario en una pregunta
     * @param questionId
     * @param userId
     * @return voteList
     */
    @Modifying
    @Query(value = "REMOVE FROM answer_votes WHERE answer_id = ?1 AND user_id = ?2 ", nativeQuery = true)
    List<AnswerVote> removeVotesWithAnswerIdAndUserId(long questionId, long userId);

}
