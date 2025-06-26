package com.exam.chatbot.service;

import com.exam.chatbot.dto.GenerateQuestionRequest;
import com.exam.chatbot.dto.QuestionDto;
import com.exam.chatbot.dto.QuestionResultDto;
import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.dto.request.QuestionRequest;

import java.io.IOException;
import java.util.List;

public interface AgentVerifyQuestionService {
    VerifyQuestionResultDto verifyQuestion(QuestionRequest request) throws IOException;

    QuestionResultDto agentSuggestQuestion(QuestionDto request) throws IOException;

    Boolean verifyMessage(String message) throws IOException;

    List<VerifyQuestionResultDto> generateQuestions(GenerateQuestionRequest request) throws IOException;
}
