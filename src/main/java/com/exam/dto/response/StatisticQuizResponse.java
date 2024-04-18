package com.exam.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticQuizResponse {
    private Long id;
    private String title;
    private String description;
    private int maxMarks;
    private int numberOfQuestions;
    private int durationMinutes;
}
