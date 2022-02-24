package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.services.QuestionService;
import com.questionsandanswers.questionsandanswers.services.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping
    public List<QuestionDto> questionList(){
        return questionService.getQuestionList();
    }

    @GetMapping("{id}")
    public QuestionDto findQuestionById(@PathVariable long id){
        return questionService.getQuestion(id);
    }

    @PostMapping("create")
    public QuestionDto create(@RequestBody Question question){ // llega en forma de las columnas de la tabla
        return questionService.saveQuestion(question);
    }

    @PutMapping("update")
    public QuestionDto update(@RequestBody Question question){
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable long id){
        questionService.deleteQuestion(id);
    }

    @GetMapping("user/{userId}")
    public List<QuestionDto> userQuestionList(@PathVariable Long userId){
        return questionService.userQuestionList(userId);
    }
}
