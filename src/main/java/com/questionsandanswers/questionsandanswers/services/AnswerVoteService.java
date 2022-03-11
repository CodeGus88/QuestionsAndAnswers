package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.exceptions.runtime_exception_childs.GeneralException;
import com.questionsandanswers.questionsandanswers.models.AnswerVote;
import com.questionsandanswers.questionsandanswers.repository.AnswerVoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio de Vote, peticiones al servidor
 */
@Service
public class AnswerVoteService {

    @Autowired
    private AnswerVoteRepository answerVoteRepository;

    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    /**
     * Devuelve una pregunta, a partir de su id
     * @param id
     * @return answerVote
     */
    public AnswerVote getAnswerVote(long id){
        Optional<AnswerVote> optional = answerVoteRepository.findById(id);
        Validation.notFound(id, !optional.isEmpty());
        return optional.get();
    }

    /**
     * Guarda un voto, devuelve true si se procesa correctamente
     * @param answerVote
     * @return isSuccess
     */
    public boolean addVote(AnswerVote answerVote){
        Validation.validateWhriteVote(
                answerVoteRepository.existByUserIdAndAnswerId(
                        answerVote.getUser().getId(),
                        answerVote.getAnswer().getId()
                ));
        try{
            answerVoteRepository.save(answerVote);
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
        Validation.notFound(id, answerVoteRepository.existsById(id));
        answerVoteRepository.deleteById(id);
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
            answerVoteRepository.removeVotesWithAnswerIdAndUserId(answerId, userId);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new GeneralException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
