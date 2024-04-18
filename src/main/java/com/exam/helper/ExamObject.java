package com.exam.helper;

import com.exam.dto.response.QuestionResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExamObject {
    private Long quizId;
    private String title;
    private String description;
    private int maxMarks;
    private int numberOfQuestions;
    private int durationMinutes;
    private List<QuestionResponse> questionResponseList;
}
