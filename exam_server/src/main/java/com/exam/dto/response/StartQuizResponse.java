package com.exam.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StartQuizResponse {
    private Long userQuizResultId;
    private Long quizId;
    private String title;
    private String description;
    private int maxMarks;
    private int numberOfQuestions;
    private int durationMinutes;
    private List<QuestionResponse> questionResponseList;
}
