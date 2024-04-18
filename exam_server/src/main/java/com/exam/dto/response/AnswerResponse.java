package com.exam.dto.response;

import com.exam.model.Question;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AnswerResponse {
    private Long id;
    private String media;
    private String content;
    private Timestamp createdAt;
    private boolean isCorrect;
}
