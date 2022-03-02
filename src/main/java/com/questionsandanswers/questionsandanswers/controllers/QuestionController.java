package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.services.QuestionService;
import com.questionsandanswers.questionsandanswers.services.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<QuestionDto>> questionList(){
        return questionService.getQuestionList();
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionDto> findQuestionById(@PathVariable long id){
        return questionService.getQuestion(id);
    }

    @PostMapping("create")
    public ResponseEntity<QuestionDto> create(@RequestBody Question question){ // llega en forma de las columnas de la tabla
        return questionService.saveQuestion(question);
    }

    @PutMapping("update")
    public ResponseEntity<QuestionDto> update(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable long id){
        questionService.deleteQuestion(id);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<QuestionDto>> userQuestionList(@PathVariable long userId){
        return questionService.userQuestionList(userId);
    }

    @GetMapping("in/{days}")
    public ResponseEntity<List<QuestionDto>>  userQuestionListInDays(@PathVariable int days){
        return questionService.getQuestionListInDays(days);
    }

    @GetMapping("latest/{rankOfTime}")
    public ResponseEntity<List<QuestionDto>> userLatestQuestionList(@PathVariable String rankOfTime){
        return questionService.getQuestionListInDays(rankOfTime);
    }
    @GetMapping("search/{search}")
    public ResponseEntity<List<QuestionDto>>  search(@PathVariable String search){
        return questionService.getQuestionListSearchMatches(search);
    }

}
