package com.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionRequest {
    private Long id;
    private String media;
    private String content;
    private String questionTypeId;
    private Long categoryId;
    private List<AnswerRequest> answerRequestList = new ArrayList<>();
    private Integer marksOfQuestion;
}
