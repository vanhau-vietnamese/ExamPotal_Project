package com.exam.chatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateQuestionRequest {
    private MultipartFile file;
    private String message;
    private Integer number;
    private String fileId;
}
