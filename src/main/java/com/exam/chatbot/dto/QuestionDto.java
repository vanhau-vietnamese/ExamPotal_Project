package com.exam.chatbot.dto;

import com.exam.dto.request.AnswerRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private String media;
    private String content;
    private String questionTypeId;
    private Long categoryId;
    private boolean result;
    private List<AnswerDto> answers = new ArrayList<>();
    private Integer marksOfQuestion;
}
