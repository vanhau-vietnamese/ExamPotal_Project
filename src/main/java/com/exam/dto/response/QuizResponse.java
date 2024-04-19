package com.exam.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class QuizResponse {
    private Long userQuizResultId;
    private Long quizId;
    private String title;
    private String description;
    private int maxMarks;
    private int numberOfQuestions;
    private int durationMinutes;
    private Timestamp createAt;
    private List<QuestionResponse> questionResponseList;
}
