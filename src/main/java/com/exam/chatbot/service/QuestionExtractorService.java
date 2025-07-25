package com.exam.chatbot.service;

import com.exam.chatbot.dto.ChatRequestDto;
import com.exam.chatbot.dto.GenerateQuestionRequest;
import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.dto.request.QuestionRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface QuestionExtractorService {
    List<QuestionRequest> extractQuestions(MultipartFile file) throws IOException;

    List<VerifyQuestionResultDto> verifyQuestions(MultipartFile file) throws IOException;

    List<VerifyQuestionResultDto> verifyQuestionsV2(MultipartFile file) throws IOException;

    String chatMessage(String message, List<MultipartFile> files );

    List<VerifyQuestionResultDto> generateQuestions(GenerateQuestionRequest request) throws IOException;

//    List

}
