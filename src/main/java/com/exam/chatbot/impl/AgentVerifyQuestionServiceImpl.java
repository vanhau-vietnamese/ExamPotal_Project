package com.exam.chatbot.impl;

import com.exam.chatbot.dto.QuestionDto;
import com.exam.chatbot.dto.QuestionResultDto;
import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.chatbot.service.AgentVerifyQuestionService;
import com.exam.dto.request.QuestionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AgentVerifyQuestionServiceImpl implements AgentVerifyQuestionService {
    private final ChatClient chatClient;

    @Value("classpath:/prompts/verify-question.st")
    private Resource ragPromptTemplate;

    @Value("classpath:/prompts/suggest-question.st")
    private Resource ragSuggestPromptTemplate;

    public AgentVerifyQuestionServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public VerifyQuestionResultDto verifyQuestion(QuestionRequest questionRequest) throws IOException {
        var outputConverter = new BeanOutputConverter<>(VerifyQuestionResultDto.class);
        String format = outputConverter.getFormat();

        // Convert questionRequest to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String questionJson = objectMapper.writeValueAsString(questionRequest);

        System.out.println("question JSON: " + questionJson);

        // Gọi PromptTemplate với các tham số
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("input", questionJson); // <<--- JSON của câu hỏi
        promptParameters.put("format", format);
        Prompt prompt = promptTemplate.create(promptParameters);

        var chatResponse = chatClient.prompt(prompt).call().chatResponse();
        var textOutput = chatResponse.getResult().getOutput().getText();

        return outputConverter.convert(textOutput);
    }

    @Override
    public QuestionResultDto agentSuggestQuestion(QuestionDto request) throws IOException {
        var outputConverter = new BeanOutputConverter<>(QuestionResultDto.class);
        String format = outputConverter.getFormat();

        // Convert questionRequest to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String questionJson = objectMapper.writeValueAsString(request);

        System.out.println("question JSON: " + questionJson);

        // Gọi PromptTemplate với các tham số
        PromptTemplate promptTemplate = new PromptTemplate(ragSuggestPromptTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("input", questionJson); // <<--- JSON của câu hỏi
        promptParameters.put("format", format);
        Prompt prompt = promptTemplate.create(promptParameters);

        var chatResponse = chatClient.prompt(prompt).call().chatResponse();
        var textOutput = chatResponse.getResult().getOutput().getText();

        return outputConverter.convert(textOutput);
    }
}
