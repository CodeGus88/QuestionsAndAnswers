package com.questionsandanswers.questionsandanswers.repository;

import com.questionsandanswers.questionsandanswers.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaAnswerInterface extends JpaRepository<Answer, Long> {

    /**
     * Devuelve la respuesta con id
     * @param questionId
     * @return answerList
     */
    List<Answer> findByQuestionId(long questionId);

    /**
     * Devuelve la respuesta con id
     * @param id
     * @return answerList
     */
    List<Answer> findById(long id);

    /**
     * devuelve las respuestas de un usuario
     * @param userId
     * @return answerList
     */
    List<Answer> findByUserId(long userId);

    /**
     * Verifica si un voto existe
     */
    boolean existsById(long id);

}
