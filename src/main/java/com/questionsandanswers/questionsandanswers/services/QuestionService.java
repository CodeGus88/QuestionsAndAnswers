package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.repository.JpaQuestionInterface;
import com.questionsandanswers.questionsandanswers.services.dto.QuestionDto;
import com.questionsandanswers.questionsandanswers.services.enums.TimeMeasurementsEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Servicio, peticiones al servidor
 */
@Service
public class QuestionService {

    @Autowired
    private JpaQuestionInterface jpaQuestionInterface;

    /**
     * Listar todas las preguntas
     * @return  questionList
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionList(){
        return convertQuestionListToQuestionDtoList(jpaQuestionInterface.findAll());
    }

    /**
     * Devuelve una pregunta, a partir de su id
     * @param id
     * @return pregunta
     */
    @Transactional(readOnly = true)
    public QuestionDto getQuestion(Long id){
        try{
            Optional<Question> optional = jpaQuestionInterface.findById(id);
            QuestionDto questionDto = new QuestionDto();
            questionDto.writeFromModel(optional.get());
            return questionDto;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Guarda una nueva pregunta
     * @param question
     * @return saveQuestion
     */
    public QuestionDto saveQuestion(Question question){
        question.setCreateDate(ZonedDateTime.now());
        try{
            question.setId(0L); // Se asegura de que es un nuevo registro
            QuestionDto questionDto = new QuestionDto();
            questionDto.writeFromModel(jpaQuestionInterface.save(question));
            return questionDto;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Edita una pregunta
     * @param question
     * @return updateQuestion
     */
    public QuestionDto updateQuestion(Question question){
        try{
            Optional<Question> optional = jpaQuestionInterface.findById(question.getId());
            if(!optional.isEmpty()) {
                QuestionDto questionDto = new QuestionDto();
                questionDto.writeFromModel(jpaQuestionInterface.save(question));
                return questionDto;
            } else return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Elimina una pregunta
     * @param id
     */
    public void deleteQuestion(Long id){
        jpaQuestionInterface.deleteById(id);
    }


//    SERVICIOS ADICIONALES AL CRUD

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param userId
     * @return userQuestionList
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> userQuestionList(long userId){
        return convertQuestionListToQuestionDtoList(jpaQuestionInterface.findByUserId(userId));
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
                QuestionDto questionDto = new QuestionDto();
                questionDto.writeFromModel(question);
                questionDtoList.add(questionDto);
            }
            return questionDtoList;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param days
     * @return questionDtoList
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionListOnDays(int  days){
        return convertQuestionListToQuestionDtoList(
            jpaQuestionInterface.findByCreateDateBetween(ZonedDateTime.now().minusDays(days), ZonedDateTime.now())
        );
    }

    /**
     * Devuelve la lista de preguntas de un usuario
     * @param rankOfTime
     * @return questionDtoList
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionListOnDays(String rankOfTime){
        TimeMeasurementsEnum timeMeasurementsEnum = TimeMeasurementsEnum.valueOf(rankOfTime.toUpperCase());
        return convertQuestionListToQuestionDtoList(
                jpaQuestionInterface.findByCreateDateBetween(ZonedDateTime.now().minusDays(timeMeasurementsEnum.getDays()),
                        ZonedDateTime.now())
        );
    }

    /**
     * Buscador de preguntas
     * @param search
     * @return questionList
     */
    @Transactional(readOnly = true)
    public List<QuestionDto> getQuestionListSearchMatches(String search){
        return convertQuestionListToQuestionDtoList(jpaQuestionInterface.searchMatches(search));
    }
}
