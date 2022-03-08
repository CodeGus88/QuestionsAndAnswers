package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.models.Answer;
import com.questionsandanswers.questionsandanswers.services.AnswerService;
import com.questionsandanswers.questionsandanswers.services.dto.AnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("question/{questionId}")
    public ResponseEntity<List<AnswerDto>> findByQuestionId(@PathVariable long questionId){
        return answerService.getAnswerWithQuestionId(questionId);
    }

    @PostMapping("create")
    public ResponseEntity<AnswerDto> create(@RequestBody Answer answer){
        return answerService.saveAnswer(answer);
    }

    @PutMapping("update")
    public ResponseEntity<AnswerDto> update(@RequestBody Answer answer){
        return answerService.updateAnswer(answer);
    }

}
