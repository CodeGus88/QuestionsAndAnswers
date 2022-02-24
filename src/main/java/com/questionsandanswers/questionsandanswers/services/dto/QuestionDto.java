package com.questionsandanswers.questionsandanswers.services.dto;

import com.questionsandanswers.questionsandanswers.models.Question;

import java.util.Date;

public class QuestionDto {

    private Long id;

    private String title;

    private String body;

    private String tags;

    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        this.date = question.getDate();
        UserDto userDto =  new UserDto();
        userDto.writeFromModel(question.getUser());
        this.user.setId(userDto.getId());
        this.user.setFullName(userDto.getFullName());
        this.user.setEmail(userDto.getEmail());
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", tags='" + tags + '\'' +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}
