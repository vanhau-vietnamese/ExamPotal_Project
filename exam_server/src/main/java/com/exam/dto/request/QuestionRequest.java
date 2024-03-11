package com.exam.dto.request;

import com.exam.model.QuestionType;
import com.exam.model.QuizQuestion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
public class QuestionRequest {
    private String media;
    private String content;
    private String questionTypeId;
    private String status;
    private Long quizId;
}
