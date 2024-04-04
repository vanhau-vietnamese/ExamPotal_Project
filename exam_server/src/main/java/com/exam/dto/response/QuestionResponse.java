package com.exam.dto.response;

import com.exam.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionResponse {
    private Long id;
    private String media;
    private String content;
    private String status ;
    private QuestionTypeResponse questionType;
    private Timestamp createdAt;
    private Set<Answer> answers = new HashSet<>();
    private CategoryResponse category;
}
