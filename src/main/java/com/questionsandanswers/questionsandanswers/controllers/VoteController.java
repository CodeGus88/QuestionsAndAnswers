package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.exceptions.AdviceController;
import com.questionsandanswers.questionsandanswers.models.Vote;
import com.questionsandanswers.questionsandanswers.services.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para la entidad Vote (votos)
 */
@RestController
@RequestMapping("api/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    private Logger logger = LoggerFactory.getLogger(AdviceController.class);

    @PostMapping("create")
    public ResponseEntity<Boolean> create(@RequestBody Vote vote){
        try{
            return voteService.addVote(vote);
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        try{
            return voteService.removeVote(id);
        }catch (Exception e){
            return null;
        }
    }

    @DeleteMapping("delete/{questionId}/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "questionId") long questionId, @PathVariable(name = "userId") long userId){
        try {
            return voteService.removeVoteWithQuestionAndUser(questionId, userId);
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

}
