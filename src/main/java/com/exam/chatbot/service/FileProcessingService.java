package com.exam.chatbot.service;

import com.exam.dto.request.QuestionRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.IOException;
import java.util.List;

public interface FileProcessingService {
    List<QuestionRequest> generateQuestionsFromFileId(String fileId, int countQuestion);

    String processAndStore(MultipartFile file) throws IOException;

    List<QuestionRequest> handlePdf(MultipartFile file, int countQuestion) throws IOException;
}
