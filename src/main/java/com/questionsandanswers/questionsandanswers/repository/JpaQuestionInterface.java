package com.questionsandanswers.questionsandanswers.repository;

import com.questionsandanswers.questionsandanswers.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Repositorio para Question
 */
public interface JpaQuestionInterface extends JpaRepository<Question, Long> {

    /**
     * Busca un las preguntas de un usuario
     * @param userId
     * @return questionList
     */
    public List<Question> findByUserId(long userId);


    /**
     * Busca las preguntas en un rango de tiempo
     * @param start
     * @param end
     * @return questionList
     */
    @Query(value = "SELECT * " +
            "FROM questions " +
            "WHERE create_date BETWEEN ?1 and ?2", nativeQuery = true)
    public List<Question> findByCreateDateBetween(ZonedDateTime start, ZonedDateTime end);

    /**
     * Busca coincidencias, comando en cuenta los campos title, body y tags de la entidad question
     * @param search
     * @return questionList
     */
    @Query(value = "SELECT * " +
            "FROM questions " +
            "WHERE " +
            "title LIKE %?1% OR " +
            "body LIKE %?1% OR " +
            "tags LIKE %?1% ", nativeQuery = true)
    public List<Question> searchMatches(String search);

    /**
     * Verifica si una pregunta existe
     */
    boolean existsById(long id);

}
