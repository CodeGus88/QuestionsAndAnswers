package com.questionsandanswers.questionsandanswers.services.dto;

import com.questionsandanswers.questionsandanswers.models.Question;

import java.time.ZonedDateTime;

public class QuestionDto {

    private Long id;

    private String title;

    private String body;

    private String tags;

    private ZonedDateTime createDate;

    private UserDto user;

    public QuestionDto(){
        user = new UserDto();
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

    public void writeFromModel(Question question){
        this.id = question.getId();
        this.title = question.getTitle();
        this.body = question.getBody();
        this.tags = question.getTags();
        this.createDate = question.getCreateDate();
        UserDto userDto =  new UserDto();
        userDto.writeFromModel(question.getUser());
        this.user = userDto;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", tags='" + tags + '\'' +
                ", createDate=" + createDate +
                ", user=" + user +
                '}';
    }
}
