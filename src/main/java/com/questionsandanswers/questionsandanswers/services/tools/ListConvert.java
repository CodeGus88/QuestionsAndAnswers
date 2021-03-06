package com.questionsandanswers.questionsandanswers.services.tools;

import com.questionsandanswers.questionsandanswers.models.Answer;
import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.models.dto.AnswerDto;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionDto;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionItemDto;
import com.questionsandanswers.questionsandanswers.models.dto.UserDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListConvert {

    /**
     * Convierte la lista Question a QuestionDto
     * @param userList
     * @return  userDtoList
     */
    public static List<UserDto> userToUserDto(List<User> userList){
        List<UserDto> questionDtoList = new ArrayList<>();
        for(User u : userList){
            questionDtoList.add(new UserDto(u));
        }
        return questionDtoList;
    }

    /**
     * Convierte la lista Question a QuestionDto
     * @param questionList
     * @return  questionDtoList
     */
    public static List<QuestionDto> questionToQuestionDto(List<Question> questionList){
            List<QuestionDto> questionDtoList = new ArrayList<>();
            for(Question q : questionList){
                questionDtoList.add(new QuestionDto(q));
            }
            return questionDtoList;
    }

    /**
     * Convierte la lista Question a QuestionDto
     * @param questionList
     * @return  questionDtoList
     */
    public static List<QuestionItemDto> questionToQuestionItemDto(List<Question> questionList){
        List<QuestionItemDto> questionItemDtoList = new ArrayList<>();
        for(Question q : questionList){
            questionItemDtoList.add(new QuestionItemDto(q));
        }
        Collections.sort(questionItemDtoList, (o1, o2)->o1.getPunctuation() - o2.getPunctuation());
        Collections.reverse(questionItemDtoList);
        return questionItemDtoList;
    }

    /**
     * Convertir lista de Answer a AnswerDto
     * @param answerList
     * @return  AnswerDtoList
     */
    public static List<AnswerDto> answerToAnswerDto(List<Answer> answerList){
        List<AnswerDto> answerDtoList = new ArrayList<>();
        for(Answer a: answerList){
            answerDtoList.add(new AnswerDto(a));
        }
        return answerDtoList;
    }

}
