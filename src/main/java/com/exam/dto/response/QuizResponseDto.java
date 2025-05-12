package com.exam.dto.response;


import com.exam.enums.EStatus;
import com.exam.model.Category;
import com.exam.model.Quiz;
import com.exam.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class QuizResponseDto {
    private Long id;
    private String title;
    private String description;
    private int maxMarks;
    private int numberOfQuestions;
    private int durationMinutes;
    private EStatus status;
    private UserResponse createBy;
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    private CategoryResponse category;

    public QuizResponseDto(Quiz quiz) {
        this.id = quiz.getId();
        this.title = quiz.getTitle();
        this.description = quiz.getDescription();
        this.maxMarks = quiz.getMaxMarks();
        this.numberOfQuestions = quiz.getNumberOfQuestions();
        this.durationMinutes = quiz.getDurationMinutes();
        this.status = quiz.getStatus();
        this.createBy = new UserResponse(quiz.getCreateBy());
        this.createdAt = quiz.getCreatedAt();
        this.category = new CategoryResponse(quiz.getCategory());
    }
}
