package com.exam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class QuantityStatisticsResponse {
    private int totalNumberOfQuestions;
    private int totalNumberOfQuizzes;
    private int totalNumberOfStudents;
}
