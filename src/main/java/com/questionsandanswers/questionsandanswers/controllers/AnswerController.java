package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.auth.security.services.UserDetailsServiceImpl;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.Answer;
import com.questionsandanswers.questionsandanswers.services.AnswerService;
import com.questionsandanswers.questionsandanswers.models.dto.AnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserDetailsServiceImpl authUser;

    @GetMapping("question/{questionId}")
    public ResponseEntity<List<AnswerDto>> findByQuestionId(@PathVariable long questionId){
        ResponseEntity<List<AnswerDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                answerService.getAnswersWithQuestionId(questionId)
        );
        return responseEntity;
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AnswerDto> create(@RequestBody Answer answer){
        ResponseEntity<AnswerDto> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                answerService.saveAnswer(answer)
        );
        return responseEntity;
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AnswerDto> update(@RequestBody Answer answer){
        check(answer.getId());
        ResponseEntity<AnswerDto> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                answerService.updateAnswer(answer)
        );
        return responseEntity;
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void delete(@PathVariable long id){
        check(id);
        answerService.deleteAnswer(id);
    }

    /**
     * Verifica que sea el autor del registro antes de editar o eliminar
     * @param answerId
     */
    private void check(long answerId){
        Validation.validateAuthor(
                authUser.loadUserByUsernameInfo(SecurityContextHolder.getContext().getAuthentication().getName()),
                answerService.getAnswer(answerId).getId()
        );
    }

}
