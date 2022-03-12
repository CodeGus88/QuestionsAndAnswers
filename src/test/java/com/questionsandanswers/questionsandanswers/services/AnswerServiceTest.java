package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.models.Answer;
import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.models.dto.AnswerDto;
import com.questionsandanswers.questionsandanswers.repository.AnswerRepository;
import com.questionsandanswers.questionsandanswers.services.tools.ListConvert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnswerServiceTest {

    @Mock
    AnswerRepository answerRepository;

    @InjectMocks
    AnswerService answerService;

    List<Answer> answerList;

    @BeforeEach
    void setUp() {
        answerList = new ArrayList<>();
        for(int i = 1; i<= 10; i++){
            Answer answer = new Answer();
            answer.setId(i);
            answer.setBody("This is the answer body " + i);
            answer.setCreateDate(ZonedDateTime.now());
            answer.setUser(new User());
            answer.setAnswerVoteList(new ArrayList<>());
            answer.setQuestion(new Question());
            answerList.add(answer);
        }
    }

    @Test
    void getAnswersWithQuestionId() {
        this.answerList.get(2).setId(2);
        this.answerList.get(5).setId(2);
        List<Answer> answerList = new ArrayList<>();
        answerList.add(this.answerList.get(2));
        answerList.add(this.answerList.get(5));
        when(answerRepository.findByQuestionId(2)).thenReturn(answerList);
        assertEquals(ListConvert.answerToAnswerDto(answerList).toString(),
                answerService.getAnswersWithQuestionId(2).toString());
    }

    @Test
    void getAnswer() {
        AnswerDto answerDto = new AnswerDto(answerList.get(0));
        when(answerRepository.findById(1L)).thenReturn(Optional.of(answerList.get(0)));
        assertEquals(answerDto.toString(), answerService.getAnswer(1L).toString());
    }

    @Test
    void saveAnswer() {
        Answer answer = answerList.get(5);
        AnswerDto answerDto = new AnswerDto(answer);
        when(answerRepository.save(answer)).thenReturn(answer);
        assertEquals(answerDto.toString(), answerService.saveAnswer(answer).toString());
    }

    @Test
    void updateAnswer() {
        Answer answer = answerList.get(5);
        AnswerDto answerDto = new AnswerDto(answer);
        when(answerRepository.save(answer)).thenReturn(answer);
        assertEquals(answerDto.toString(), answerService.updateAnswer(answer).toString());
    }

}