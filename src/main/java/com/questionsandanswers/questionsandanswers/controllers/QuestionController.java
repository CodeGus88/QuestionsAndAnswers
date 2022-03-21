package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionItemDto;
import com.questionsandanswers.questionsandanswers.models.requests.questions.QuestionRequest;
import com.questionsandanswers.questionsandanswers.models.requests.questions.QuestionUpdateRequest;
import com.questionsandanswers.questionsandanswers.services.QuestionService;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * Controlador para la entidad Question (preguntas)
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/questions")
public class QuestionController extends MainClassController{

    @Autowired
    private QuestionService questionService;

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
    public ResponseEntity<QuestionRequest> create(@RequestBody @Validated QuestionRequest qr, BindingResult result){
        Validation.generalValidations(result);
        Question question = new Question(qr, getUserDetailsImp().getId());
        return ResponseEntity.ok().body(questionService.saveQuestion(question));
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<QuestionUpdateRequest> update(@Valid @RequestBody QuestionUpdateRequest qur, BindingResult result){
        Validation.generalValidations(result);
        check(qur.getId());
        Question question = new Question(qur);
        return ResponseEntity.ok().body(questionService.updateQuestion(question));
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
     * @param entityId
     */
    private void check(long entityId){
        Validation.validateAuthor(
                getUserDetailsImp(),
                questionService.getQuestion(entityId).getUser().getId()
        );
    }

}
