package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.auth.security.services.UserDetailsServiceImpl;
import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionItemDto;
import com.questionsandanswers.questionsandanswers.services.QuestionService;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador para la entidad Question (preguntas)
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserDetailsServiceImpl authUser;

    @GetMapping
    public ResponseEntity<List<QuestionItemDto>> questionList(){
        ResponseEntity<List<QuestionItemDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                questionService.getQuestionList()
        );
        return responseEntity;
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionDto> findQuestionById(@PathVariable long id){
        ResponseEntity<QuestionDto> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                questionService.getQuestion(id)
        );
        return responseEntity;
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<QuestionDto> create(@RequestBody Question question){
        question.getUser().setId(authUser.loadUserByUsernameInfo(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        ResponseEntity<QuestionDto> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                questionService.saveQuestion(question)
        );
        return responseEntity;
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<QuestionDto> update(@RequestBody Question question){
        check(question.getId());
        ResponseEntity<QuestionDto> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                questionService.updateQuestion(question)
        );
        return responseEntity;
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void delete(@PathVariable long id){
        check(id);
        questionService.deleteQuestion(id);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<QuestionItemDto>> userQuestionList(@PathVariable long userId){
        ResponseEntity<List<QuestionItemDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                questionService.userQuestionList(userId)
        );
        return responseEntity;
    }

    @GetMapping("in/{days}")
    public ResponseEntity<List<QuestionItemDto>>  userQuestionListInDays(@PathVariable int days){
        ResponseEntity<List<QuestionItemDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                questionService.getQuestionListInDays(days)
        );
        return responseEntity;
    }

    @GetMapping("latest/{rankOfTime}")
    public ResponseEntity<List<QuestionItemDto>> userLatestQuestionList(@PathVariable String rankOfTime){
        ResponseEntity<List<QuestionItemDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                questionService.getQuestionListInDays(rankOfTime)
        );
        return responseEntity;
    }
    @GetMapping("search/{search}")
    public ResponseEntity<List<QuestionItemDto>>  search(@PathVariable String search){
        ResponseEntity<List<QuestionItemDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                questionService.getQuestionListSearchMatches(search)
        );
        return responseEntity;
    }

    /**
     * Verifica que sea el autor del registro antes de editar o eliminar
     * @param questionId
     */
    private void check(long questionId){
        Validation.validateAuthor(
                authUser.loadUserByUsernameInfo(SecurityContextHolder.getContext().getAuthentication().getName()),
                questionService.getQuestion(questionId).getId()
        );
    }

}
