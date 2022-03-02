package com.questionsandanswers.questionsandanswers.controllers;

import com.questionsandanswers.questionsandanswers.models.Vote;
import com.questionsandanswers.questionsandanswers.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("create")
    public ResponseEntity<Boolean> create(@RequestBody Vote vote){
        return voteService.addVote(vote);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id){
        return voteService.removeVote(id);
    }

    @DeleteMapping("delete/{questionId}/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable(name = "questionId") long questionId, @PathVariable(name = "userId") long userId){
        return voteService.removeVoteWithQuestionAndUser(questionId, userId);
    }

}
