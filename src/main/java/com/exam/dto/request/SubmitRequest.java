package com.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SubmitRequest {
    private Long userQuizResultId;
    private Long quizId;
    private List<QuestionChoiceRequest> answers = new ArrayList<>();
}
