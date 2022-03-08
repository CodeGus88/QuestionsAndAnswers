package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.models.QuestionVote;
import com.questionsandanswers.questionsandanswers.repository.JpaQuestionVoteInterface;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Servicio de Vote, peticiones al servidor
 */
@Service
public class QuestionVoteService {

    @Autowired
    private JpaQuestionVoteInterface jpaQuestionVoteInterface;

    /**
     * Guarda un voto, devuelve true si se procesa correctamente
     * @param questionVote
     * @return isSuccess
     */
    public ResponseEntity<Boolean> addVote(QuestionVote questionVote){
        ResponseEntity<Boolean> responseEntity;
        try{
            if(jpaQuestionVoteInterface.findByQuestionAndUserId(questionVote.getQuestion().getId(), questionVote.getUser().getId()) == null){
                jpaQuestionVoteInterface.save(questionVote);
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
            Validation.notFound(id, jpaQuestionVoteInterface.findById(id).isEmpty());
            jpaQuestionVoteInterface.deleteById(id);
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
            jpaQuestionVoteInterface.removeVotesWithQuestionIdAndUserId(questionId, userId);
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(true);
        }catch (Exception e){
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
        return responseEntity;
    }
}
