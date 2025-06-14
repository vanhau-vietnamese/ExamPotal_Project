package com.exam.dto.request;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizRequest {
    private String title;
    private String description;
    private int maxMarks;
    private boolean status = false;
    private Long categoryId;
    private int durationMinutes;
    private List<QuizQuestionRequest> listQuestion;
    private List<QuestionRequest> questions;
}
