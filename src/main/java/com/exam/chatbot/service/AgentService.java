package com.exam.chatbot.service;

import com.exam.chatbot.dto.ChatRequestDto;
import com.exam.chatbot.dto.ChatResponseDto;
import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.dto.request.QuestionRequest;
import org.springframework.ai.chat.messages.Message;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AgentService {
    List<VerifyQuestionResultDto> verifyQuestions(List<QuestionRequest> extractQuestions) throws IOException;

    String processAndStore(MultipartFile file) throws IOException;

    VerifyQuestionResultDto verifyQuestion(QuestionRequest questionRequest) throws IOException;

    String chatMessage(String message, List<MultipartFile> files);

    String imageToCode() throws IOException;

    String doChat(ChatRequestDto request);

    List<Message> getHistory();

    String clearHistory();
}
