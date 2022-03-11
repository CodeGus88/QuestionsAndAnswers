package com.questionsandanswers.questionsandanswers.repository;

import com.questionsandanswers.questionsandanswers.models.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Repositorio para Vote
 */
public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {

    /**
     * Busca el voto de un usuario en una pregunta
     * @param questionId
     * @param userId
     * @return vote
     */
    @Query(value = "SELECT * FROM question_votes WHERE question_id = ?1 AND user_id = ?2 ", nativeQuery = true)
    QuestionVote findByQuestionAndUserId(long questionId, long userId);

    /**
     * Busca los votos de una pregunta
     * @param questionId
     * @return voteList
     */
    @Query(value = "SELECT FROM question_votes WHERE question_id = ?1 ", nativeQuery = true)
    List<QuestionVote> findVotesForQuestionId(long questionId);

    /**
     * Elimina el voto de un usuario en una pregunta
     * @param questionId
     * @param userId
     * @return voteList
     */
    @Modifying
    @Query(value = "REMOVE FROM question_votes WHERE question_id = ?1 AND user_id = ?2 ", nativeQuery = true)
    List<QuestionVote> removeVotesWithQuestionIdAndUserId(long questionId, long userId);

    /**
     * Verifica si un voto existe
     */
    boolean existsById(long id);

    /**
     * Existe el voto del usuario userId en la pregunta questionId
     */
    @Query(value = "SELECT CASE WHEN EXISTS ( " +
            "SELECT * FROM question_votes WHERE user_id = ?1 AND question_id = ?2 " +
            ") " +
            "THEN TRUE ELSE FALSE " +
            "END", nativeQuery = true)
    boolean existByUserIdAndQuestionId(long userId, long questionId);

}
