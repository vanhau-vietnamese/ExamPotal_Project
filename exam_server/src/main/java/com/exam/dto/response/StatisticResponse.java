package com.exam.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StatisticResponse {
    private String fullName;
    private String email;
    private int marks;
    private Timestamp startTime;
    private Timestamp submitTime;
    private String duration;
    private int rank;
    private int numberOfCorrect;
    private int numberOfIncorrect;
    private QuizResponse quizResponse;

}
