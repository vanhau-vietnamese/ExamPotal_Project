package com.exam.chatbot.dto;

import com.exam.dto.request.QuestionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyQuestionResultDto {
    private boolean status;
    private String reason;
    private String suggestion;
    private QuestionRequest question;
}
