package com.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionRequest {
    private String media;
    private String content;
    private String questionTypeId;
    private String status;
    private Long quizId;
}
