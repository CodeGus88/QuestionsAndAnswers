package com.questionsandanswers.questionsandanswers.models.dto;

import com.questionsandanswers.questionsandanswers.models.AnswerVote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AnswerVoteDto implements Serializable {

    private int resulOfTheVotes;
    private long questionId;
    private List<VoteResume> voteResumeList;

    public AnswerVoteDto(List<AnswerVote> answerVoteList){
        voteResumeList = new ArrayList<>();
        if(answerVoteList != null){
            this.resulOfTheVotes = answerVoteList.size();
            questionId = answerVoteList.size()>0? answerVoteList.get(0).getAnswer().getId(): 0;
            for(AnswerVote answerVote : answerVoteList){
                voteResumeList.add(new VoteResume(answerVote.getId(), answerVote.getUser().getId()));
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

    @Override
    public String toString() {
        return "AnswerVoteDto{" +
                "resulOfTheVotes=" + resulOfTheVotes +
                ", questionId=" + questionId +
                ", voteResumeList=" + voteResumeList +
                '}';
    }
}
