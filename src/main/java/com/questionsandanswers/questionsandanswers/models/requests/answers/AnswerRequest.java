package com.questionsandanswers.questionsandanswers.models.requests.answers;

import com.questionsandanswers.questionsandanswers.models.Answer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AnswerRequest {

    @NotBlank
    private String body;

    @NotNull
    @Min(value = 1)
    private long questionId;

    public AnswerRequest(Answer answer){
        body = answer.getBody();
        questionId = answer.getQuestion().getId();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "AnswerRequest{" +
                "body='" + body + '\'' +
                ", questionId=" + questionId +
                '}';
    }
}
