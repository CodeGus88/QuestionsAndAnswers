package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.Answer;
import com.questionsandanswers.questionsandanswers.repository.JpaAnswerInterface;
import com.questionsandanswers.questionsandanswers.services.dto.AnswerDto;
import com.questionsandanswers.questionsandanswers.services.tools.ListConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private JpaAnswerInterface jpaAnswerInterface;
    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    /**
     *
     * @param questionId
     * @return answerDtoList
     */
    @Transactional(readOnly = true)
    public ResponseEntity<List<AnswerDto>> getAnswerWithQuestionId(long questionId){
        ResponseEntity<List<AnswerDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
            ListConvert.answerToAnswerDto(
                jpaAnswerInterface.findByQuestionId(questionId)
            )
        );
        return responseEntity;
    }

    /**
     * Crea una nueva respuesta
     * @param answer
     * return answerDto
     */
    public ResponseEntity<AnswerDto> saveAnswer(Answer answer) {
        ResponseEntity<AnswerDto> responseEntity;
        answer.setId(0L);
        answer.setCreateDate(ZonedDateTime.now());
        Validation.validateWhriteAnswerData(answer);
        try{
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                new AnswerDto(
                    jpaAnswerInterface.save(answer)
                )
            );
        }catch (Exception e){
            e.getMessage();
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return responseEntity;
    }

    /**
     * Actualiza una respuesta
     * @param answer
     */
    public ResponseEntity<AnswerDto> updateAnswer(Answer answer) {
        ResponseEntity<AnswerDto> responseEntity;
        Validation.notFound(answer.getId(), jpaAnswerInterface.findById(answer.getId()).isEmpty());
        Validation.validateWhriteAnswerData(answer);
        try{
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                    new AnswerDto(jpaAnswerInterface.save(answer))
            );
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
        return responseEntity;
    }

    /**
     * Elimina una respuesta con id
     * @param id
     */
    public void deleteUser(Long id) {
        Validation.notFound(id, jpaAnswerInterface.findById(id).isEmpty());
        jpaAnswerInterface.deleteById(id);
    }

}
