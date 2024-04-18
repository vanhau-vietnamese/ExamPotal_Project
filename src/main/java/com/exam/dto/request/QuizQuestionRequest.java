package com.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizQuestionRequest {
    private Long questionId;
    private Integer marksOfQuestion;
}
