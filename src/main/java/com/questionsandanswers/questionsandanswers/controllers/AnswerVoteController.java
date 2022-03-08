package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.models.AnswerVote;
import com.questionsandanswers.questionsandanswers.services.AnswerVoteService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return answerVoteService.addVote(answerVote);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        return answerVoteService.removeVote(id);
    }

    @DeleteMapping("delete/{answerId}/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "answerId") long answerId, @PathVariable(name = "userId") long userId){
        return answerVoteService.removeVoteWithQuestionAndUser(answerId, userId);
    }

}
