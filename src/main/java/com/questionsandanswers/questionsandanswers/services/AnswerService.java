package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.exceptions.runtime_exception_childs.GeneralException;
import com.questionsandanswers.questionsandanswers.models.Answer;
import com.questionsandanswers.questionsandanswers.models.requests.answers.AnswerRequest;
import com.questionsandanswers.questionsandanswers.models.requests.answers.AnswerUpdateRequest;
import com.questionsandanswers.questionsandanswers.repository.AnswerRepository;
import com.questionsandanswers.questionsandanswers.models.dto.AnswerDto;
import com.questionsandanswers.questionsandanswers.repository.QuestionRepository;
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

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    /**
     * Deveuelve las respuestas de una pregunta
     * @param questionId
     * @return answerDtoList
     */
    @Transactional(readOnly = true)
    public List<AnswerDto> getAnswersWithQuestionId(long questionId){
        return ListConvert.answerToAnswerDto(
                answerRepository.findByQuestionId(questionId)
        );
    }

    /**
     *
     * @param id
     * @return answerDtoList
     */
    @Transactional(readOnly = true)
    public AnswerDto getAnswer(long id){
        Optional<Answer> optional = answerRepository.findById(id);
        Validation.notFound(id, !optional.isEmpty());
        return new AnswerDto(optional.get());
    }

    /**
     * Crea una nueva respuesta
     * @param answer
     * return answerRequest
     */
    public AnswerRequest saveAnswer(Answer answer) {
        Validation.notFound(answer.getQuestion().getId(),
                questionRepository.existsById(answer.getQuestion().getId())
        );
        answer.setCreateDate(ZonedDateTime.now());
        try{
            return new AnswerRequest(answerRepository.save(answer));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Actualiza una respuesta
     * @param answer
     */
    public AnswerUpdateRequest updateAnswer(Answer answer) {
        Validation.notFound(answer.getId(), answerRepository.existsById(answer.getId()));
        try{
            return new AnswerUpdateRequest(answerRepository.save(answer));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina una respuesta con id
     * @param id
     */
    public void deleteAnswer(Long id) {
        Validation.notFound(id, answerRepository.existsById(id));
        answerRepository.deleteById(id);
    }

}
