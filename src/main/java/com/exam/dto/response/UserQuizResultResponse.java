package com.exam.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserQuizResultResponse {
    private Long id;
    private int marks;
    private Timestamp startTime;
    private Timestamp submitTime;
    private String durationTime;
    private Integer numberOfCorrect;
    private Integer numberOfIncorrect;
    private Map<String, Object> quiz = new HashMap<>();
}
