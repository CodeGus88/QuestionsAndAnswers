package com.questionsandanswers.questionsandanswers.models.dto;

import com.questionsandanswers.questionsandanswers.models.Question;

import java.io.Serializable;

public class QuestionItemDto implements Serializable {

    private Long id;

    private String title;

    private String body;

    private String tags;

    private int punctuation;

    public QuestionItemDto() {
    }

    public QuestionItemDto(Long id, String title, String body, String tags, int punctuation) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.punctuation = punctuation;
    }

    public QuestionItemDto(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.body = question.getBody();
        this.tags = question.getTags();
        this.punctuation = question.getVoteList().size();
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

    public int getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(int punctuation) {
        this.punctuation = punctuation;
    }

    @Override
    public String toString() {
        return "QuestionItemDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", tags='" + tags + '\'' +
                ", punctuation=" + punctuation +
                '}';
    }
}
