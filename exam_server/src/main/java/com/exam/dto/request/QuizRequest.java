package com.exam.dto.request;

import com.exam.model.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class QuizRequest {
    private String title;
    private String description;
    private int maxMarks;
    private int numberOfQuestions;
    private boolean status = false;
    private Long categoryId;
    private int durationMinutes;
}
