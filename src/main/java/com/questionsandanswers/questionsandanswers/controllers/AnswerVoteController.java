package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.AnswerVote;
import com.questionsandanswers.questionsandanswers.services.AnswerVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la entidad Vote (votos)
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/answer-votes")
public class AnswerVoteController extends MainClassController{

    @Autowired
    private AnswerVoteService answerVoteService;

    @PostMapping("create/{answerId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> create(@PathVariable long answerId){
        AnswerVote answerVote = new AnswerVote(answerId, getUserDetailsImp().getId());
        return ResponseEntity.ok().body(answerVoteService.addVote(answerVote));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        check(id);
        return ResponseEntity.ok().body(answerVoteService.removeVote(id));
    }

    @DeleteMapping("delete/{answerId}/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "answerId") long answerId, @PathVariable(name = "userId") long userId){
        return ResponseEntity.ok().body(answerVoteService.removeVoteWithQuestionAndUser(answerId, userId));
    }

    /**
     * Verifica que sea el autor del registro antes de editar o eliminar
     * @param questionVoteId
     */
    private void check(long questionVoteId){
        Validation.validateAuthor(
                getUserDetailsImp(),
                answerVoteService.getAnswerVote(questionVoteId).getId()
        );
    }

}
