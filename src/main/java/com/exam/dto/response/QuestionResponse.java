package com.exam.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionResponse {
    private Long id;
    private String media;
    private String content;
    private QuestionTypeResponse questionType;
    private Timestamp createdAt;
    private Set<AnswerResponse> answers = new LinkedHashSet<>();
    private CategoryResponse category;
    private Map<String, Object> additionalFields = new HashMap<>();
}
