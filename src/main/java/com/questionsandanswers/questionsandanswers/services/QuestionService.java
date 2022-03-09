package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.exceptions.runtime_exception_childs.GeneralException;
import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.repository.JpaQuestionInterface;
import com.questionsandanswers.questionsandanswers.services.dto.QuestionDto;
import com.questionsandanswers.questionsandanswers.services.enums.TimeMeasurementsEnum;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.services.tools.ListConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio de Question, peticiones al servidor
 */
@Service
public class QuestionService {

    @Autowired
    private JpaQuestionInterface jpaQuestionInterface;

    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    /**
     * Listar todas las preguntas
     * @return  questionList
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionList(){
        return ListConvert.questionToQuestionDto(
                jpaQuestionInterface.findAll()
        );
    }

    /**
     * Devuelve una pregunta, a partir de su id
     * @param id
     * @return pregunta
     */
    @Transactional(readOnly = true)
    public QuestionDto getQuestion(Long id){
        Optional<Question> optional = jpaQuestionInterface.findById(id);
        Validation.notFound(id, optional.isEmpty());
        return new QuestionDto(
                optional.get()
        );
    }

    /**
     * Guarda una nueva pregunta
     * @param question
     * @return saveQuestion
     */
    public QuestionDto saveQuestion(Question question){
        Validation.validateWhriteQuestionData(question);
        question.setCreateDate(ZonedDateTime.now());
        try{
            question.setId(0L);
            return new QuestionDto(
                    jpaQuestionInterface.save(question)
            );
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Edita una pregunta
     * @param question
     * @return updateQuestion
     */
    public QuestionDto updateQuestion(Question question){
        Validation.notFound(question.getId(), jpaQuestionInterface.existsById(question.getId()));
        Validation.validateWhriteQuestionData(question);
        try{
            QuestionDto questionDto = new QuestionDto(jpaQuestionInterface.save(question));
            return new QuestionDto(jpaQuestionInterface.save(question));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina una pregunta
     * @param id
     */
    public void deleteQuestion(Long id){
        Validation.notFound(id, jpaQuestionInterface.existsById(id));
        jpaQuestionInterface.deleteById(id);
    }

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param userId
     * @return userQuestionList
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> userQuestionList(long userId){
        try {
            return ListConvert.questionToQuestionDto(jpaQuestionInterface.findByUserId(userId));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param days
     * @return questionDtoList
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionListInDays(int  days){
        try{
            List<QuestionDto> questionDtoList = ListConvert.questionToQuestionDto(
                    jpaQuestionInterface.findByCreateDateBetween(
                            ZonedDateTime.now().minusDays(days),
                            ZonedDateTime.now())
            );
            return questionDtoList;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param rankOfTime
     * @return questionDtoList
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionListInDays(String rankOfTime){
        try{
            TimeMeasurementsEnum timeMeasurementsEnum = TimeMeasurementsEnum.valueOf(rankOfTime.toUpperCase());
            List<QuestionDto> questionDtoList =
                    ListConvert.questionToQuestionDto(
                            jpaQuestionInterface.findByCreateDateBetween(
                                ZonedDateTime.now().minusDays(timeMeasurementsEnum.getDays()),
                                ZonedDateTime.now())
                    );
            return questionDtoList;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Buscador de preguntas
     * @param search
     * @return questionList
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionListSearchMatches(String search){
        try{
            List<QuestionDto> questionDtoList =
                    ListConvert.questionToQuestionDto(jpaQuestionInterface.searchMatches(search));
            return questionDtoList;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
