package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.AnswerVote;
import com.questionsandanswers.questionsandanswers.repository.JpaAnswerVoteInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio de Vote, peticiones al servidor
 */
@Service
public class AnswerVoteService {

    @Autowired
    private JpaAnswerVoteInterface jpaAnswerVoteInterface;

    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    /**
     * Guarda un voto, devuelve true si se procesa correctamente
     * @param answerVote
     * @return isSuccess
     */
    public boolean addVote(AnswerVote answerVote){
        Validation.validateWhriteVote(
                jpaAnswerVoteInterface.existByUserIdAndAnswerId(
                        answerVote.getUser().getId(),
                        answerVote.getAnswer().getId()
                ));
        try{
            jpaAnswerVoteInterface.save(answerVote);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            Validation.catchException(e);
            return false;
        }
    }

    /**
     * Elimina un voto
     * @param id, userId
     * @return isSucces
     */
    public boolean removeVote(long id){
        Validation.notFound(id, jpaAnswerVoteInterface.existsById(id));
        jpaAnswerVoteInterface.deleteById(id);
        return true;
    }

    /**
     * Elimina el voto de un usuario en una pregunta
     * @param answerId
     * @param userId
     * @return responseEntity
     */
    public boolean removeVoteWithQuestionAndUser(long answerId, long userId){
        try {
            jpaAnswerVoteInterface.removeVotesWithAnswerIdAndUserId(answerId, userId);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            Validation.catchException(e);
            return false;
        }
    }
}
