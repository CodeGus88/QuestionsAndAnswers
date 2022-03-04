package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.models.Vote;
import com.questionsandanswers.questionsandanswers.repository.JpaVoteInterface;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Servicio, peticiones al servidor
 */
@Service
public class VoteService {

    @Autowired
    private JpaVoteInterface jpaVoteInterface;

    /**
     * Guarda un voto, devuelve true si se procesa correctamente
     * @param vote
     * @return isSuccess
     */
    public ResponseEntity<Boolean> addVote(Vote vote){
        ResponseEntity<Boolean> responseEntity;
        try{
            if(jpaVoteInterface.findByQuestionAndUserId(vote.getQuestion().getId(), vote.getUser().getId()) == null){
                jpaVoteInterface.save(vote);
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
            Validation.notFound(id, jpaVoteInterface.findById(id).isEmpty());
            jpaVoteInterface.deleteById(id);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (Exception e){
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
        return  responseEntity;
    }

    /**
     * Elimina el voto de un usuario en una pregunta
     * @param questionId
     * @param userId
     * @return
     */
    public ResponseEntity<Boolean> removeVoteWithQuestionAndUser(long questionId, long userId){
        ResponseEntity<Boolean> responseEntity;
        try {
            jpaVoteInterface.removeVotesWithQuestionIdAndUserId(questionId, userId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (Exception e){
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
        return responseEntity;
    }
}
