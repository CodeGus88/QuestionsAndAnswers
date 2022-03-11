package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.auth.security.services.UserDetailsServiceImpl;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.AnswerVote;
import com.questionsandanswers.questionsandanswers.services.AnswerVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la entidad Vote (votos)
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/answer-votes")
public class AnswerVoteController {

    @Autowired
    private AnswerVoteService answerVoteService;

    @Autowired
    private UserDetailsServiceImpl authUser;

    @PostMapping("create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> create(@RequestBody AnswerVote answerVote){
        ResponseEntity<Boolean> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                answerVoteService.addVote(answerVote)
        );
        return responseEntity;
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        check(id);
        ResponseEntity<Boolean> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                answerVoteService.removeVote(id)
        );
        return responseEntity;
    }

    @DeleteMapping("delete/{answerId}/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "answerId") long answerId, @PathVariable(name = "userId") long userId){
        ResponseEntity<Boolean> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                answerVoteService.removeVoteWithQuestionAndUser(answerId, userId)
        );
        return responseEntity;
    }

    /**
     * Verifica que sea el autor del registro antes de editar o eliminar
     * @param questionVoteId
     */
    private void check(long questionVoteId){
        Validation.validateAuthor(
                authUser.loadUserByUsernameInfo(SecurityContextHolder.getContext().getAuthentication().getName()),
                answerVoteService.getAnswerVote(questionVoteId).getId()
        );
    }

}
