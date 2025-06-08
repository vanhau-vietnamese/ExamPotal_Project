package com.exam.chatbot.dto;

import com.exam.dto.request.QuestionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExtractQuestionRequest {
    private String title;
    private String description;
    private int maxMarks;
    private boolean status = false;
    private Long categoryId;
    private int durationMinutes;
    private List<QuestionRequest> questions;
}
