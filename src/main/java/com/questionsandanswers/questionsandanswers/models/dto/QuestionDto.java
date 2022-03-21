package com.questionsandanswers.questionsandanswers.models.dto;

import com.questionsandanswers.questionsandanswers.models.Answer;
import com.questionsandanswers.questionsandanswers.models.Question;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto response, para pregunta
 */
public class QuestionDto implements Serializable {

    private Long id;

    private String title;

    private String body;

    private String tags;

    private ZonedDateTime createDate;

    private UserDto user;

    private QuestionVoteDto vote;

    private List<AnswerDto> answerList;

    public QuestionDto(Question question){
        this.id = question.getId();
        this.title = question.getTitle();
        this.body = question.getBody();
        this.tags = question.getTags();
        this.createDate = question.getCreateDate();
        this.user = new UserDto(question.getUser());
        this.vote = new QuestionVoteDto(question.getVoteList());
        answerList = new ArrayList<>();
            for(Answer a : question.getAnswerList())
                answerList.add(new AnswerDto(a));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto userDto) {
        this.user = userDto;
    }

    public QuestionVoteDto getVote() {
        return vote;
    }

    public void setVote(QuestionVoteDto vote) {
        this.vote = vote;
    }

    public List<AnswerDto> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerDto> answerList) {
        this.answerList = answerList;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", tags='" + tags + '\'' +
                ", createDate=" + createDate +
                ", user=" + user +
                ", vote=" + vote +
                ", answerList=" + answerList +
                '}';
    }
}
