package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.models.QuestionVote;
import com.questionsandanswers.questionsandanswers.services.QuestionVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la entidad Vote (votos)
 */
@RestController
@RequestMapping("api/question-votes")
public class QuestionVoteController {

    @Autowired
    private QuestionVoteService questionVoteService;

    @PostMapping("create")
    public ResponseEntity<Boolean> create(@RequestBody QuestionVote questionVote){
        return questionVoteService.addVote(questionVote);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        return questionVoteService.removeVote(id);
    }

    @DeleteMapping("delete/{questionId}/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "questionId") long questionId, @PathVariable(name = "userId") long userId){
        return questionVoteService.removeVoteWithQuestionAndUser(questionId, userId);
    }

}
