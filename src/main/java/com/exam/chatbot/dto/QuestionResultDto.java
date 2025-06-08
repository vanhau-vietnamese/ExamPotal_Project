package com.exam.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResultDto {
    private boolean status; // trạng thái tru/ false
    private String reason; // lý do sai
    private String suggestion; // đề xuất ôn tập
    private String solution; // cách giải
    private String reference; // link đường dẫn ôn tập
    private QuestionDto question; // câu hỏi
    private boolean result = false;
}
