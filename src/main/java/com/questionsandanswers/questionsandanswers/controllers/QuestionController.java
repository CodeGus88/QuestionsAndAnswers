package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.services.QuestionService;
import com.questionsandanswers.questionsandanswers.services.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador para la entidad Question (preguntas)
 */
@RestController
@RequestMapping("api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionDto>> questionList(){
        ResponseEntity<List<QuestionDto>> responseEntity;
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
    public ResponseEntity<QuestionDto> create(@RequestBody Question question){
        ResponseEntity<QuestionDto> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                questionService.saveQuestion(question)
        );
        return responseEntity;
    }

    @PutMapping("update")
    public ResponseEntity<QuestionDto> update(@RequestBody Question question){
        ResponseEntity<QuestionDto> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                questionService.updateQuestion(question)
        );
        return responseEntity;
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable long id){
        questionService.deleteQuestion(id);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<QuestionDto>> userQuestionList(@PathVariable long userId){
        ResponseEntity<List<QuestionDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                questionService.userQuestionList(userId)
        );
        return responseEntity;
    }

    @GetMapping("in/{days}")
    public ResponseEntity<List<QuestionDto>>  userQuestionListInDays(@PathVariable int days){
        ResponseEntity<List<QuestionDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                questionService.getQuestionListInDays(days)
        );
        return responseEntity;
    }

    @GetMapping("latest/{rankOfTime}")
    public ResponseEntity<List<QuestionDto>> userLatestQuestionList(@PathVariable String rankOfTime){
        ResponseEntity<List<QuestionDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                questionService.getQuestionListInDays(rankOfTime)
        );
        return responseEntity;
    }
    @GetMapping("search/{search}")
    public ResponseEntity<List<QuestionDto>>  search(@PathVariable String search){
        ResponseEntity<List<QuestionDto>> responseEntity;
        responseEntity = ResponseEntity.status(HttpStatus.OK).body(
                questionService.getQuestionListSearchMatches(search)
        );
        return responseEntity;
    }

}
