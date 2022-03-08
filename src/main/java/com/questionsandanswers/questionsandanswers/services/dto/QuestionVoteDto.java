package com.questionsandanswers.questionsandanswers.services.dto;

import com.questionsandanswers.questionsandanswers.models.AnswerVote;
import com.questionsandanswers.questionsandanswers.models.QuestionVote;

import java.util.ArrayList;
import java.util.List;

public class VoteDto {
    private int resulOfTheVotes;
    private long questionId;
    private List<VoteResume> voteResumeList;

    public VoteDto(List<AnswerVote> questionVoteList){
        voteResumeList = new ArrayList<>();
        if(questionVoteList != null){
            this.resulOfTheVotes = questionVoteList.size();
            questionId = questionVoteList.size()>0? questionVoteList.get(0).getAnswer().getId(): 0;
            for(AnswerVote questionVote : questionVoteList){
                voteResumeList.add(new VoteResume(questionVote.getId(), questionVote.getUser().getId()));
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
