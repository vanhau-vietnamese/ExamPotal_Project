package com.exam.chatbot.service;

import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.dto.request.QuestionRequest;

import java.io.IOException;
import java.util.List;

public interface AgentService {
    List<VerifyQuestionResultDto> verifyQuestions(List<QuestionRequest> extractQuestions) throws IOException;
}
