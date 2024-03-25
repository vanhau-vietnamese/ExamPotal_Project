package com.exam.dto.response;

import com.exam.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class QuestionResponse {
    private Long id;
    private String media;
    private String content;
    private String status ;
    private Integer marksOfQuestion ;
    private QuestionType questionType;
    private Timestamp createdAt;
    private Set<Answer> answers = new HashSet<>();
}
