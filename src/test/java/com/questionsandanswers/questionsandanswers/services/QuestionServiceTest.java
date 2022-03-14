package com.questionsandanswers.questionsandanswers.services;

import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionDto;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionItemDto;
import com.questionsandanswers.questionsandanswers.models.enums.TimeMeasurementsEnum;
import com.questionsandanswers.questionsandanswers.repository.QuestionRepository;
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
class QuestionServiceTest {

    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    QuestionService questionService;

    List<Question> questionList;

    @BeforeEach
    void setUp() {
        questionList =  new ArrayList<>();
        for(int i = 1; i <= 10; i++) {
            long id = ((long) i) + 1;
            Question question = new Question();
            question.setId(id);
            question.setTitle("Título " + id);
            question.setBody("Body " + id);
            question.setCreateDate(ZonedDateTime.now());
            question.setTags("tag 1, tag 2, tag 3");
            question.setUser(new User());
            question.setAnswerList(new ArrayList<>());
            question.setVoteList(new ArrayList<>());
            questionList.add(question);
        }
        System.out.println(questionList);
    }

    @Test
    void getQuestionList() {
        List<QuestionItemDto> expectedList = ListConvert.questionToQuestionItemDto(
                questionList
        );
        when(questionRepository.findAll()).thenReturn(questionList);
        assertEquals(expectedList.toString(), questionService.getQuestionList().toString());
    }

    @Test
    void getQuestion() {
        QuestionDto expectedQuestonDto = new QuestionDto(questionList.get(0));
        when(questionRepository.findById(1L)).thenReturn(Optional.of(questionList.get(0)));
        assertEquals(expectedQuestonDto.toString(), questionService.getQuestion(1L).toString());
    }

    @Test
    void saveQuestion() {
        long generatedId = 8;
        QuestionDto expectedQuestonDto = new QuestionDto(questionList.get(5));
        expectedQuestonDto.setId(generatedId);

        Question questionGenerated = questionList.get(5);
        questionGenerated.setId(generatedId);
        when(questionRepository.save(questionList.get(5))).thenReturn(questionGenerated);

        assertEquals(expectedQuestonDto.toString(), questionService.saveQuestion(questionList.get(5)));
    }

    @Test
    void updateQuestion() {
        Question question = questionList.get(9);
        QuestionDto expectedQuestonDto = new QuestionDto(question);

        when(questionRepository.existsById(question.getId()) ).thenReturn(true);
        when(questionRepository.save(question)).thenReturn(question);

        assertEquals(expectedQuestonDto.toString(), questionService.updateQuestion(question));
    }

    @Test
    void deleteQuestion() {
        assertEquals(false, when(questionRepository.existsById(5)).thenReturn(false));
    }

    @Test
    void userQuestionList() {
        List<Question> questionList = new ArrayList<>();
        User user1 = new User();
        user1.setId(7);
        Question question1 = this.questionList.get(1);
        Question question2 = this.questionList.get(7);

        question1.setUser(user1);
        question2.setUser(user1);

        questionList.add(question1);
        questionList.add(question2);

        List<QuestionItemDto> questionItemDtoList =  ListConvert.questionToQuestionItemDto(questionList);

        when(questionRepository.findByUserId(7) ).thenReturn(questionList);

        assertEquals(questionItemDtoList.toString(), questionService.userQuestionList(7).toString());

    }

    @Test
    void getQuestionListInDays() {
        int days = 7;
        when( questionRepository.findByCreateDateBetween(ZonedDateTime.now().minusDays(days),
                ZonedDateTime.now())).thenReturn(questionList);
        List<QuestionItemDto> questionItemDtoList = ListConvert.questionToQuestionItemDto(questionList);
        assertEquals(questionItemDtoList.toString(), questionService.getQuestionListInDays(days).toString().toString());
    }

    @Test
    void testGetQuestionListInDays() {
        when( questionRepository.findByCreateDateBetween(ZonedDateTime.now().minusDays(30),
                ZonedDateTime.now())).thenReturn(questionList);
        List<QuestionItemDto> questionItemDtoList = ListConvert.questionToQuestionItemDto(questionList);
        assertEquals(questionItemDtoList.toString(), questionService.getQuestionListInDays(TimeMeasurementsEnum.MONTH.toString()).toString().toString());
    }

    @Test
    void getQuestionListSearchMatches() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(this.questionList.get(1));
        List<QuestionItemDto> espectedList = ListConvert.questionToQuestionItemDto(questionList);
        when(questionRepository.searchMatches("Body 1") ).thenReturn(questionList);
        assertEquals(espectedList.toString(), questionService.getQuestionListSearchMatches("Body 1").toString());
    }

}