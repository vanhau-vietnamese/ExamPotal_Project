package com.exam.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AnswerRequest {
    private String media;
    private String content;
    private boolean isCorrect;

}
