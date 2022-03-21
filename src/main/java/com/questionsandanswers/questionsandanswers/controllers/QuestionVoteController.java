package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.QuestionVote;
import com.questionsandanswers.questionsandanswers.services.QuestionVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la entidad Vote (votos)
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/question-votes")
public class QuestionVoteController extends MainClassController{

    @Autowired
    private QuestionVoteService questionVoteService;

    @PostMapping("create/{questionId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> create(@PathVariable long questionId){
        QuestionVote questionVote = new QuestionVote(questionId, getUserDetailsImp().getId());
        return ResponseEntity.ok().body(questionVoteService.addVote(questionVote));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        check(id);
        return ResponseEntity.ok().body(questionVoteService.removeVote(id));
    }

    @DeleteMapping("delete/{questionId}/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "questionId") long questionId, @PathVariable(name = "userId") long userId){
        return ResponseEntity.ok().body(questionVoteService.removeVoteWithQuestionAndUser(questionId, userId));
    }

    /**
     * Verifica que sea el autor del registro antes de editar o eliminar
     * @param questionVoteId
     */
    private void check(long questionVoteId){
        Validation.validateAuthor(
                getUserDetailsImp(),
                questionVoteService.getQuestionVote(questionVoteId).getUser().getId()
        );
    }
}
