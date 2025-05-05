package com.exam.chatbot.impl;

import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.chatbot.service.AgentService;
import com.exam.dto.request.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AgentServiceImpl implements AgentService {
    private final ChatClient chatClient;

    @Value("classpath:/prompts/verify-question.st")
    private Resource ragPromptTemplate;
    public AgentServiceImpl(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public List<VerifyQuestionResultDto> verifyQuestions(List<QuestionRequest> extractQuestions) throws IOException {
        BeanOutputConverter<List<VerifyQuestionResultDto>> outputConverter = new BeanOutputConverter<>(
                new ParameterizedTypeReference<List<VerifyQuestionResultDto>>() {}
        );
        String format = outputConverter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate, Map.of(
                "question", String.valueOf(countQuestion),
                "documents", String.join("\n", contentList),
                "format", format
        ));
        Prompt prompt = promptTemplate.create();

        ChatResponse chatResponse = chatClient.prompt().call().chatResponse();

        return outputConverter.convert(
                Objects.requireNonNull(chatResponse)
                        .getResult()
                        .getOutput()
                        .getText()
        );
    }
}
