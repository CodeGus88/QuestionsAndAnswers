package com.questionsandanswers.questionsandanswers.models.requests.questions;

import com.questionsandanswers.questionsandanswers.models.Question;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class QuestionRequest implements Serializable {

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotBlank
    private String tags;

    public QuestionRequest(){}

    public QuestionRequest(Question question){
        title = question.getTitle();
        body = question.getBody();
        tags = question.getTags();
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

    @Override
    public String toString() {
        return "QuestionRequest{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", tags='" + tags + '\'' +
                '}';
    }
}
