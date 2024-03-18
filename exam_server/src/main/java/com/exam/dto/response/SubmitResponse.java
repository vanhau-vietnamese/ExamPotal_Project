package com.exam.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitResponse {
    private String userId;
    private Long quizId;
    private int marks;
    private String capture;
    private int numberOfCorrect;
    private int numberOfIncorrect;
}
