package com.exam.chatbot.service;

import com.exam.chatbot.dto.QuestionDto;
import com.exam.chatbot.dto.QuestionResultDto;
import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.dto.request.QuestionRequest;

import java.io.IOException;

public interface AgentVerifyQuestionService {
    VerifyQuestionResultDto verifyQuestion(QuestionRequest request) throws IOException;

    QuestionResultDto agentSuggestQuestion(QuestionDto request) throws IOException;
}
