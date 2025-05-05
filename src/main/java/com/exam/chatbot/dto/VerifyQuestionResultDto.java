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
    private String status; // VALID / INVALID
    private String reason; // null nếu valid
    private String suggestion; // null nếu valid
    private QuestionRequest question;
}
