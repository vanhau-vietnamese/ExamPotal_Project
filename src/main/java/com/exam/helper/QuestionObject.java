package com.exam.helper;

import com.exam.dto.response.CategoryResponse;
import com.exam.dto.response.QuestionTypeResponse;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class QuestionObject {
    private Long id;
    private String media;
    private String content;
    private QuestionTypeResponse questionType;
    private Timestamp createdAt;
    private CategoryResponse category;
}
