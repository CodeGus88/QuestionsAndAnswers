package com.questionsandanswers.questionsandanswers.models.requests.questions;

import com.questionsandanswers.questionsandanswers.models.Question;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Formulario para actualizar una pregunta
 */
public class QuestionUpdateRequest extends QuestionRequest implements Serializable {

    @NotNull(message = "Question id is required")
    private long id;

    public QuestionUpdateRequest(){

    }

    public QuestionUpdateRequest(Question question) {
        super(question);
        this.id = question.getId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "QuestionUpdateRequest{" +
                "id=" + id +
                '}';
    }
}
