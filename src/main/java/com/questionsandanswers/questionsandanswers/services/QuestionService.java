package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.repository.JpaQuestionInterface;
import com.questionsandanswers.questionsandanswers.services.dto.QuestionDto;
import com.questionsandanswers.questionsandanswers.services.enums.TimeMeasurementsEnum;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
    public ResponseEntity<List<QuestionDto>> getQuestionList(){
        ResponseEntity<List<QuestionDto>> responseEntity;
        try{
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                    convertQuestionListToQuestionDtoList(jpaQuestionInterface.findAll())
            );
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return responseEntity;
    }

    /**
     * Devuelve una pregunta, a partir de su id
     * @param id
     * @return pregunta
     */
    @Transactional(readOnly = true)
    public ResponseEntity<QuestionDto> getQuestion(Long id){
        Optional<Question> optional = jpaQuestionInterface.findById(id);
        Validation.notFound(id, optional.isEmpty());
        QuestionDto questionDto = new QuestionDto(optional.get());
        ResponseEntity<QuestionDto> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(questionDto);
        return responseEntity;
    }

    /**
     * Guarda una nueva pregunta
     * @param question
     * @return saveQuestion
     */
    public ResponseEntity<QuestionDto> saveQuestion(Question question){
        Validation.validateWhriteQuestionData(question);
        ResponseEntity<QuestionDto> responseEntity;
        question.setCreateDate(ZonedDateTime.now());
        try{
            question.setId(0L);
            QuestionDto questionDto = new QuestionDto(jpaQuestionInterface.save(question));
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(questionDto);
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return responseEntity;
    }

    /**
     * Edita una pregunta
     * @param question
     * @return updateQuestion
     */
    public ResponseEntity<QuestionDto> updateQuestion(Question question){
        Validation.notFound(question.getId(), jpaQuestionInterface.findById(question.getId()).isEmpty());
        Validation.validateWhriteQuestionData(question);
        ResponseEntity<QuestionDto> responseEntity;
        try{
            QuestionDto questionDto = new QuestionDto(jpaQuestionInterface.save(question));
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(questionDto);
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return responseEntity;
    }

    /**
     * Elimina una pregunta
     * @param id
     */
    public void deleteQuestion(Long id){
        Validation.notFound(id, jpaQuestionInterface.findById(id).isEmpty());
        jpaQuestionInterface.deleteById(id);
    }

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param userId
     * @return userQuestionList
     */
    @Transactional(readOnly = true)
    public ResponseEntity<List<QuestionDto>> userQuestionList(long userId){
        ResponseEntity<List<QuestionDto>> responseEntity;
        try {
            List<QuestionDto> questionDtoList =
                    convertQuestionListToQuestionDtoList(jpaQuestionInterface.findByUserId(userId));
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(questionDtoList);
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return responseEntity;
    }

    /**
     * Convierte la lista Question a QuestionDto
     * @param questionList
     * @return  QuestionDtoList
     */
    private List<QuestionDto> convertQuestionListToQuestionDtoList(List<Question> questionList){
        try{
            List<QuestionDto> questionDtoList = new ArrayList<>();
            for(Question question : questionList){
                QuestionDto questionDto = new QuestionDto(question);
                questionDtoList.add(questionDto);
            }
            return questionDtoList;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param days
     * @return questionDtoList
     */
    @Transactional(readOnly = true)
    public ResponseEntity<List<QuestionDto>> getQuestionListInDays(int  days){
        ResponseEntity<List<QuestionDto>> responseEntity;
        try{
            List<QuestionDto> questionDtoList = convertQuestionListToQuestionDtoList(
                    jpaQuestionInterface.findByCreateDateBetween(
                            ZonedDateTime.now().minusDays(days),
                            ZonedDateTime.now())
            );
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(questionDtoList);
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return responseEntity;
    }

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param rankOfTime
     * @return questionDtoList
     */
    @Transactional(readOnly = true)
    public ResponseEntity<List<QuestionDto>> getQuestionListInDays(String rankOfTime){
        ResponseEntity<List<QuestionDto>> responseEntity;
        try{
            TimeMeasurementsEnum timeMeasurementsEnum = TimeMeasurementsEnum.valueOf(rankOfTime.toUpperCase());
            List<QuestionDto> questionDtoList =
                    convertQuestionListToQuestionDtoList(
                            jpaQuestionInterface.findByCreateDateBetween(
                                ZonedDateTime.now().minusDays(timeMeasurementsEnum.getDays()),
                                ZonedDateTime.now())
                    );
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(questionDtoList);
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return responseEntity;
    }

    /**
     * Buscador de preguntas
     * @param search
     * @return questionList
     */
    @Transactional(readOnly = true)
    public ResponseEntity<List<QuestionDto>> getQuestionListSearchMatches(String search){
        ResponseEntity<List<QuestionDto>> responseEntity;
        try{
            List<QuestionDto> questionDtoList =
                    convertQuestionListToQuestionDtoList(jpaQuestionInterface.searchMatches(search));
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(questionDtoList);
        }catch (Exception e){
            logger.error(e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return responseEntity;
    }
}
