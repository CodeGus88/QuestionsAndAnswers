package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.models.AnswerVote;
import com.questionsandanswers.questionsandanswers.services.AnswerVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la entidad Vote (votos)
 */
@RestController
@RequestMapping("api/answer-votes")
public class AnswerVoteController {

    @Autowired
    private AnswerVoteService answerVoteService;

    @PostMapping("create")
    public ResponseEntity<Boolean> create(@RequestBody AnswerVote answerVote){
        ResponseEntity<Boolean> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                answerVoteService.addVote(answerVote)
        );
        return responseEntity;
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        ResponseEntity<Boolean> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                answerVoteService.removeVote(id)
        );
        return responseEntity;
    }

    @DeleteMapping("delete/{answerId}/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "answerId") long answerId, @PathVariable(name = "userId") long userId){
        ResponseEntity<Boolean> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                answerVoteService.removeVoteWithQuestionAndUser(answerId, userId)
        );
        return responseEntity;
    }

}
