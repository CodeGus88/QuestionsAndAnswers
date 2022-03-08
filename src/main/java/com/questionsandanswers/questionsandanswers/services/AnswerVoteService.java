package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.AnswerVote;
import com.questionsandanswers.questionsandanswers.repository.JpaAnswerVoteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Servicio de Vote, peticiones al servidor
 */
@Service
public class AnswerVoteService {

    @Autowired
    private JpaAnswerVoteInterface jpaAnswerVoteInterface;

    /**
     * Guarda un voto, devuelve true si se procesa correctamente
     * @param answerVote
     * @return isSuccess
     */
    public ResponseEntity<Boolean> addVote(AnswerVote answerVote){
        ResponseEntity<Boolean> responseEntity;
        try{
            if(jpaAnswerVoteInterface.findByAnswerAndUserId(
                    answerVote.getAnswer().getId(), answerVote.getUser().getId()) == null
            ){
                jpaAnswerVoteInterface.save(answerVote);
                responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(true);
            }else{
                responseEntity = ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
        return responseEntity;
    }

    /**
     * Elimina un voto
     * @param id, userId
     * @return issucces
     */
    public ResponseEntity<Boolean> removeVote(long id){
        ResponseEntity<Boolean> responseEntity;
        try {
            Validation.notFound(id, jpaAnswerVoteInterface.findById(id).isEmpty());
            jpaAnswerVoteInterface.deleteById(id);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (Exception e){
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
        return  responseEntity;
    }

    /**
     * Elimina el voto de un usuario en una pregunta
     * @param answerId
     * @param userId
     * @return
     */
    public ResponseEntity<Boolean> removeVoteWithQuestionAndUser(long answerId, long userId){
        ResponseEntity<Boolean> responseEntity;
        try {
            jpaAnswerVoteInterface.removeVotesWithAnswerIdAndUserId(answerId, userId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (Exception e){
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
        return responseEntity;
    }
}
