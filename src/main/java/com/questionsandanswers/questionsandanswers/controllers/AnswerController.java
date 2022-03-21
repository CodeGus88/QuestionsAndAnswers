package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.exceptions.Validation;
import com.questionsandanswers.questionsandanswers.models.Answer;
import com.questionsandanswers.questionsandanswers.models.requests.answers.AnswerRequest;
import com.questionsandanswers.questionsandanswers.models.requests.answers.AnswerUpdateRequest;
import com.questionsandanswers.questionsandanswers.services.AnswerService;
import com.questionsandanswers.questionsandanswers.models.dto.AnswerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/answers")
public class AnswerController extends MainClassController{

    @Autowired
    private AnswerService answerService;

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
    public ResponseEntity<AnswerRequest> create(@Valid @RequestBody AnswerRequest ar, BindingResult result){
       Validation.generalValidations(result);
        Answer answer = new Answer(ar, getUserDetailsImp().getId());
       return ResponseEntity.ok().body(answerService.saveAnswer(answer));
    }

    @PutMapping("update")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AnswerUpdateRequest> update(@Valid @RequestBody AnswerUpdateRequest aur, BindingResult result){
        Validation.generalValidations(result);
        check(aur.getId());
        Answer answer = new Answer(aur);
        return ResponseEntity.ok().body(answerService.updateAnswer(answer));
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
                getUserDetailsImp(),
                answerService.getAnswer(answerId).getUser().getId()
        );
    }

}
