package com.questionsandanswers.questionsandanswers.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "answers")
public class Answer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Lob
    @Column(name = "body")
    private String body;

    @Column(name = "create_date", updatable = false)
    private ZonedDateTime createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Question question;

    @OneToMany(mappedBy = "answer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<AnswerVote> answerVoteList;

    public Answer(){
        answerVoteList = new ArrayList<>();
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

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
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

    public List<AnswerVote> getAnswerVoteList() {
        return answerVoteList;
    }

    public void setAnswerVoteList(List<AnswerVote> answerVoteList) {
        this.answerVoteList = answerVoteList;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", createDate=" + createDate +
                ", user=" + user +
                ", question=" + question +
                ", answerVoteList=" + answerVoteList +
                '}';
    }
}
