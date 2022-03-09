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
    public List<AnswerDto> getAnswerWithQuestionId(long questionId){
        return ListConvert.answerToAnswerDto(
                jpaAnswerInterface.findByQuestionId(questionId)
        );
    }

    /**
     * Crea una nueva respuesta
     * @param answer
     * return answerDto
     */
    public AnswerDto saveAnswer(Answer answer) {
        answer.setId(0L);
        answer.setCreateDate(ZonedDateTime.now());
        Validation.validateWhriteAnswerData(answer);
        AnswerDto answerDto;
        try{
            answerDto = new AnswerDto(jpaAnswerInterface.save(answer));
        }catch (Exception e){
            logger.error(e.getMessage());
            Validation.catchException(e);
            answerDto = null;
        }
        return answerDto;
    }

    /**
     * Actualiza una respuesta
     * @param answer
     */
    public AnswerDto updateAnswer(Answer answer) {
        Validation.notFound(answer.getId(), jpaAnswerInterface.existsById(answer.getId()));
        Validation.validateWhriteAnswerData(answer);
        AnswerDto answerDto;
        try{
            answerDto = new AnswerDto(jpaAnswerInterface.save(answer));
        }catch (Exception e){
            logger.error(e.getMessage());
            Validation.catchException(e);
            answerDto = null;
        }
        return answerDto;
    }

    /**
     * Elimina una respuesta con id
     * @param id
     */
    public void deleteAnswer(Long id) {
        Validation.notFound(id, jpaAnswerInterface.existsById(id));
        jpaAnswerInterface.deleteById(id);
    }

}
