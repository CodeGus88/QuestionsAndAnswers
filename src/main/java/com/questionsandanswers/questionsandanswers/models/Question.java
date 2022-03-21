package com.questionsandanswers.questionsandanswers.models;

import com.questionsandanswers.questionsandanswers.models.requests.questions.QuestionRequest;
import com.questionsandanswers.questionsandanswers.models.requests.questions.QuestionUpdateRequest;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo o entidad Question (Pregunta)
 */
@Entity
@Table(name = "questions")
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "body")
    private String body;

    @Column(name = "tags")
    private String tags;

    @Column(name = "create_date", updatable = false)
    private ZonedDateTime createDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<QuestionVote> questionVoteList;

    public Question(){
        user = new User();
        questionVoteList = new ArrayList<>();
        answerList = new ArrayList<>();
    }

   public Question(QuestionRequest questionRequest, long userId){
        title = questionRequest.getTitle();
        body = questionRequest.getBody();
        tags = questionRequest.getTags();
        createDate = ZonedDateTime.now();
        user = new User();
        user.setId(userId);
    }

    public Question(QuestionUpdateRequest questionUpdateRequest){
        id = questionUpdateRequest.getId();
        title = questionUpdateRequest.getTitle();
        body = questionUpdateRequest.getBody();
        tags = questionUpdateRequest.getTags();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<QuestionVote> getVoteList() {
        return questionVoteList;
    }

    public void setVoteList(List<QuestionVote> questionVoteList) {
        this.questionVoteList = questionVoteList;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
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
                ", answerList=" + answerList +
                ", questionVoteList=" + questionVoteList +
                '}';
    }
}
