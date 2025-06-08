package com.exam.dto.response;

import com.exam.chatbot.dto.QuestionResultDto;
import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.convert.ExamObjectConverter;
import com.exam.enums.EStatus;
import com.exam.helper.ExamObject;
import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.model.UserQuestionResult;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserHistoryDetailResponse {
    private Long quizId;
    private String generalMessage;
    private Long id;
    private int marks;
    private Timestamp startTime;
    private Timestamp submitTime;
    private String durationTime;
    private Integer numberOfCorrect;
    private Integer numberOfIncorrect;
    private UserResponseDto user;
    private String userId;
    private List<QuestionResultDto> listQuestions;

    public UserHistoryDetailResponse(Long quizId, String userId, Long id, int marks, Timestamp startTime, Timestamp submitTime, String durationTime, Integer numberOfCorrect, Integer numberOfIncorrect) {
        this.quizId = quizId;
        this.userId = userId;
        this.id = id;
        this.marks = marks;
        this.startTime = startTime;
        this.submitTime = submitTime;
        this.durationTime = durationTime;
        this.numberOfCorrect = numberOfCorrect;
        this.numberOfIncorrect = numberOfIncorrect;
    }
}
