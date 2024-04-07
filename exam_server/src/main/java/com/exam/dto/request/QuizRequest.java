package com.exam.dto.request;

import com.exam.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class QuizRequest {
    private String title;
    private String description;
    private int maxMarks;
    private boolean status = false;
    private Long categoryId;
    private int durationMinutes;
    private List<QuizQuestionRequest> listQuestion;
}
