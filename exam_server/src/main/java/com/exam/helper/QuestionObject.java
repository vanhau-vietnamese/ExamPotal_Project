package com.exam.helper;

import com.exam.dto.response.AnswerResponse;
import com.exam.dto.response.CategoryResponse;
import com.exam.dto.response.QuestionTypeResponse;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
