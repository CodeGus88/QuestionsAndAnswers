package com.questionsandanswers.questionsandanswers.models.requests.answers;

import com.questionsandanswers.questionsandanswers.models.Answer;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Formulario para editar una respuesta
 */
public class AnswerUpdateRequest {

    @NotNull
    @Min(value = 1)
    private long id;

    @NotBlank
    private String body;

    public AnswerUpdateRequest(){}

    public AnswerUpdateRequest(Answer answer){
        id = answer.getId();
        body = answer.getBody();
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

    @Override
    public String toString() {
        return "AnswerUpdateRequest{" +
                "id=" + id +
                ", body='" + body + '\'' +
                '}';
    }
}
