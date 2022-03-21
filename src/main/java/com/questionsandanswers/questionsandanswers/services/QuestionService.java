package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.exceptions.runtime_exception_childs.GeneralException;
import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionItemDto;
import com.questionsandanswers.questionsandanswers.models.requests.questions.QuestionRequest;
import com.questionsandanswers.questionsandanswers.models.requests.questions.QuestionUpdateRequest;
import com.questionsandanswers.questionsandanswers.repository.QuestionRepository;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionDto;
import com.questionsandanswers.questionsandanswers.models.enums.TimeMeasurementsEnum;
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
    private QuestionRepository questionRepository;

    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    /**
     * Listar todas las preguntas
     * @return  questionList
     */
    @Transactional(readOnly = true)
    public List<QuestionItemDto> getQuestionList(){
        return ListConvert.questionToQuestionItemDto(
                questionRepository.findAll()
        );
    }

    /**
     * Devuelve una pregunta, a partir de su id
     * @param id
     * @return pregunta
     */
    @Transactional(readOnly = true)
    public QuestionDto getQuestion(Long id){
        Optional<Question> optional = questionRepository.findById(id);
        Validation.notFound(id, !optional.isEmpty());
        return new QuestionDto(
                optional.get()
        );
    }

    /**
     * Guarda una nueva pregunta
     * @param question
     * @return saveQuestion
     */
    public QuestionRequest saveQuestion(Question question){
        try{
            return new QuestionRequest(
                    questionRepository.save(question)
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
    public QuestionUpdateRequest updateQuestion(Question question){
        Validation.notFound(question.getId(), questionRepository.existsById(question.getId()));
        try{
            return new QuestionUpdateRequest(questionRepository.save(question));
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
        Validation.notFound(id, questionRepository.existsById(id));
        questionRepository.deleteById(id);
    }

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param userId
     * @return questionItemDtoList
     */
    @Transactional(readOnly = true)
    public List<QuestionItemDto> userQuestionList(long userId){
        try {
            return ListConvert.questionToQuestionItemDto(questionRepository.findByUserId(userId));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param days
     * @return questionItemDtoList
     */
    @Transactional(readOnly = true)
    public List<QuestionItemDto> getQuestionListInDays(int  days){
        try{
            List<QuestionItemDto> questionItemDtoList = ListConvert.questionToQuestionItemDto(
                    questionRepository.findByCreateDateBetween(
                            ZonedDateTime.now().minusDays(days),
                            ZonedDateTime.now())
            );
            return questionItemDtoList;
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
    public List<QuestionItemDto> getQuestionListInDays(String rankOfTime){
        try{
            TimeMeasurementsEnum timeMeasurementsEnum = TimeMeasurementsEnum.valueOf(rankOfTime.toUpperCase());
            List<QuestionItemDto> questionItemDtoList =
                    ListConvert.questionToQuestionItemDto(
                            questionRepository.findByCreateDateBetween(
                                ZonedDateTime.now().minusDays(timeMeasurementsEnum.getDays()),
                                ZonedDateTime.now())
                    );
            return questionItemDtoList;
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
    public List<QuestionItemDto> getQuestionListSearchMatches(String search){
        try{
            List<QuestionItemDto> questionItemDtoList =
                    ListConvert.questionToQuestionItemDto(questionRepository.searchMatches(search));
            return questionItemDtoList;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
