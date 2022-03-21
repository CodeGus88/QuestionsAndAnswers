package com.questionsandanswers.questionsandanswers.models.dto;

import com.questionsandanswers.questionsandanswers.models.Answer;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Objeto response, para respuesta
 */
public class AnswerDto implements Serializable {

    private long id;
    private String body;
    private UserDto user;
    private AnswerVoteDto vote;
    private ZonedDateTime createDate;

    public AnswerDto(Answer answer){
            id = answer.getId();
            body = answer.getBody();
            user = new UserDto(answer.getUser());
            vote = new AnswerVoteDto(answer.getAnswerVoteList());
            createDate = answer.getCreateDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public AnswerVoteDto getVote() {
        return vote;
    }

    public void setVote(AnswerVoteDto vote) {
        this.vote = vote;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", user=" + user +
                ", vote=" + vote +
                ", createDate=" + createDate +
                '}';
    }
}
