package com.exam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyStatisticsDTO {
    private int month;
    private int totalStudents;
    private int totalQuestions;
    private int totalQuizzes;
}
