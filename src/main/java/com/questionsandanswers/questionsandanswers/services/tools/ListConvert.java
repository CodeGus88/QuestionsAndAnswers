package com.questionsandanswers.questionsandanswers.services.tools;

import com.questionsandanswers.questionsandanswers.models.Answer;
import com.questionsandanswers.questionsandanswers.models.Question;
import com.questionsandanswers.questionsandanswers.models.User;
import com.questionsandanswers.questionsandanswers.models.dto.AnswerDto;
import com.questionsandanswers.questionsandanswers.models.dto.QuestionDto;
import com.questionsandanswers.questionsandanswers.models.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

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
