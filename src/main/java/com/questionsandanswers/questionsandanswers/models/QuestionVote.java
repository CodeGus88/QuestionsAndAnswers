package com.questionsandanswers.questionsandanswers.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Modelo o entidad Vote (Voto)
 */
@Entity
@Table(name = "question_votes")
public class QuestionVote implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Question question;

    public QuestionVote(){}

    public QuestionVote(long questionId, long userId){
        question = new Question();
        question.setId(questionId);
        user = new User();
        user.setId(userId);
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
