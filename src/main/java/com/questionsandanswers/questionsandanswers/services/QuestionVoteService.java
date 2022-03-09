package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.exceptions.runtime_exception_childs.GeneralException;
import com.questionsandanswers.questionsandanswers.models.QuestionVote;
import com.questionsandanswers.questionsandanswers.repository.JpaQuestionVoteInterface;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Servicio de Vote, peticiones al servidor
 */
@Service
public class QuestionVoteService {

    @Autowired
    private JpaQuestionVoteInterface jpaQuestionVoteInterface;

    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    /**
     * Guarda un voto, devuelve true si se procesa correctamente
     * @param questionVote
     * @return isSuccess
     */
    public boolean addVote(QuestionVote questionVote){
        Validation.validateWhriteVote(
                jpaQuestionVoteInterface.existByUserIdAndQuestionId(
                        questionVote.getUser().getId(),
                        questionVote.getQuestion().getId()
                ));
        try{
            jpaQuestionVoteInterface.save(questionVote);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un voto
     * @param id, userId
     * @return isSucces
     */
    public boolean removeVote(long id){
        Validation.notFound(id, jpaQuestionVoteInterface.existsById(id));
        jpaQuestionVoteInterface.deleteById(id);
        return true;
    }

    /**
     * Elimina el voto de un usuario en una pregunta
     * @param questionId
     * @param userId
     * @return isSuccess
     */
    public boolean removeVoteWithQuestionAndUser(long questionId, long userId){
        try {
            jpaQuestionVoteInterface.removeVotesWithQuestionIdAndUserId(questionId, userId);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
