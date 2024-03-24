package com.exam.dto.response;

import com.exam.model.Quiz;
import com.exam.model.User;

import java.sql.Timestamp;

public class StatisticResponse {
    private User user;
    private int marks;
    private Timestamp startTime;
    private Timestamp submitTime;
    private String duration;
    private int rank;
    private int numberOfCorrect;
    private int numberOfIncorrect;
    private Quiz quiz;

}
