package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.auth.security.services.UserDetailsServiceImpl;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.QuestionVote;
import com.questionsandanswers.questionsandanswers.services.QuestionVoteService;
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
@RequestMapping("api/question-votes")
public class QuestionVoteController {

    @Autowired
    private QuestionVoteService questionVoteService;

    @Autowired
    private UserDetailsServiceImpl authUser;

    @PostMapping("create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> create(@RequestBody QuestionVote questionVote){
        ResponseEntity<Boolean> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                questionVoteService.addVote(questionVote)
        );
        return responseEntity;
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        check(id);
        ResponseEntity<Boolean> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                questionVoteService.removeVote(id)
        );
        return responseEntity;
    }

    @DeleteMapping("delete/{questionId}/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "questionId") long questionId, @PathVariable(name = "userId") long userId){
        ResponseEntity<Boolean> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                questionVoteService.removeVoteWithQuestionAndUser(questionId, userId)
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
                questionVoteService.getQuestionVote(questionVoteId).getId()
        );
    }
}
