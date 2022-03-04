package com.questionsandanswers.questionsandanswers.services.dto;

import com.questionsandanswers.questionsandanswers.models.Vote;

import java.util.ArrayList;
import java.util.List;

public class VoteDto {
    private int resulOfTheVotes;
    private long questionId;
    private List<VoteResume> voteResumeList;

    public VoteDto(List<Vote> voteList){
        voteResumeList = new ArrayList<>();
        if(voteList != null){
            this.resulOfTheVotes = voteList.size();
            questionId = voteList.size()>0? voteList.get(0).getQuestion().getId(): 0;
            for(Vote vote : voteList){
                voteResumeList.add(new VoteResume(vote.getId(), vote.getUser().getId()));
            }
        }else{
            resulOfTheVotes = 0;
        }
    }

    public int getResulOfTheVotes() {
        return resulOfTheVotes;
    }

    public void setResulOfTheVotes(int resulOfTheVotes) {
        this.resulOfTheVotes = resulOfTheVotes;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public List<VoteResume> getVoteResumeList() {
        return voteResumeList;
    }

    public void setVoteResumeList(List<VoteResume> voteResumeList) {
        this.voteResumeList = voteResumeList;
    }

    class VoteResume{
        private long id;
        private long userId;

        public VoteResume(long id, long userId) {
            this.id = id;
            this.userId = userId;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }
    }
}
