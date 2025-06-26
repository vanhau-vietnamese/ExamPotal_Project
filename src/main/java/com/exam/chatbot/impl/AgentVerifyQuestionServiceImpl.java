package com.exam.chatbot.impl;

import com.exam.chatbot.dto.GenerateQuestionRequest;
import com.exam.chatbot.dto.QuestionDto;
import com.exam.chatbot.dto.QuestionResultDto;
import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.chatbot.service.AgentVerifyQuestionService;
import com.exam.dto.request.QuestionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AgentVerifyQuestionServiceImpl implements AgentVerifyQuestionService {
    private final ChatClient chatClient;
    private final QdrantVectorStore vectorStore;
    private final ChatMemory chatMemory;

    @Value("classpath:/prompts/verify-question.st")
    private Resource ragPromptTemplate;

    @Value("classpath:/prompts/suggest-question.st")
    private Resource ragSuggestPromptTemplate;

    @Value("classpath:/prompts/generate-question.st")
    private Resource ragGeneratePromptTemplate;

    public AgentVerifyQuestionServiceImpl(ChatClient.Builder builder, QdrantVectorStore vectorStore) {
        this.chatMemory = new InMemoryChatMemory();
        this.chatClient = builder
//                .defaultAdvisors(
//                        List.of(
//                                new QuestionAnswerAdvisor(vectorStore),
//                                new MessageChatMemoryAdvisor(this.chatMemory)
//                        )
//                )
                .build();
        this.vectorStore = vectorStore;
    }

    @Transactional
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
    public Boolean verifyMessage(String message) throws IOException {

        return false;
    }

    @Transactional
//    @Override
//    public List<VerifyQuestionResultDto> generateQuestion(String fileId) {
//        int countQuestion = 6;
//        var topK = countQuestion * 3;
//
//        FilterExpressionBuilder b = new FilterExpressionBuilder();
//
//        SearchRequest searchRequest = SearchRequest.builder()
//                .query("")
//                .topK(topK) // Bạn có thể tùy chỉnh số lượng, nên lấy nhiều để AI có đủ context
//                .similarityThreshold(0.8) // Vì không dùng similarity, nên để ngưỡng 0.0
//                .filterExpression(b.eq("file_id", fileId).build()) // Lọc theo file_id
//                .build();
//
//        List<Document> documents = vectorStore.similaritySearch(searchRequest);
//
//        String context = documents.stream()
//                .map(Document::getText)
//                .collect(Collectors.joining(System.lineSeparator()));
//
//        var outputConverter = new BeanOutputConverter<>(
//                new ParameterizedTypeReference<List<VerifyQuestionResultDto>>() { });
//        String format = outputConverter.getFormat();
//
//        // Gọi PromptTemplate với các tham số
//        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
//        Map<String, Object> promptParameters = new HashMap<>();
//        promptParameters.put("format", format);
//        promptParameters.put("countQuestion", countQuestion);
//        promptParameters.put("context", context);
//        Prompt prompt = promptTemplate.create(promptParameters);
//
//        var chatResponse = chatClient.prompt(prompt).call().chatResponse();
//        var textOutput = chatResponse.getResult().getOutput().getText();
//
//        return outputConverter.convert(textOutput);
//    }
    @Override
    public List<VerifyQuestionResultDto> generateQuestions(GenerateQuestionRequest request) throws IOException {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        SearchRequest searchRequest = SearchRequest.builder()
                .query("generate questions from this file")
                .topK(400)
                .filterExpression(b.eq("file_id", request.getFileId()).build()) // Lọc theo file_id
                .build();

        List<Document> relatedDocuments = vectorStore.similaritySearch(searchRequest);

        if (relatedDocuments.isEmpty()) {
            throw new IllegalStateException("Không tìm thấy nội dung nào trong file.");
        }

        int totalQuestionsNeeded = request.getNumber() != null ? request.getNumber() : 10;
        List<VerifyQuestionResultDto> allQuestions = new ArrayList<>();
        int batchSize = 7000; // Mỗi batch 8000 ký tự (~ 2k token)

        int currentIndex = 0;

        while (allQuestions.size() < totalQuestionsNeeded && currentIndex < relatedDocuments.size()) {
            // Lấy context cho batch hiện tại
            StringBuilder contextBuilder = new StringBuilder();
            while (currentIndex < relatedDocuments.size() && contextBuilder.length() < batchSize) {
                contextBuilder.append(relatedDocuments.get(currentIndex).getText()).append("\n");
                currentIndex++;
            }
            String context = contextBuilder.toString();

            // Prompt tạo batch câu hỏi
            PromptTemplate promptTemplate = new PromptTemplate(ragGeneratePromptTemplate);
            Map<String, Object> promptParams = new HashMap<>();
            promptParams.put("context", context);
            promptParams.put("number", totalQuestionsNeeded - allQuestions.size());

            var outputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<VerifyQuestionResultDto>>() {});
            String format = outputConverter.getFormat();
            promptParams.put("format", format);

            Prompt prompt = promptTemplate.create(promptParams);

            var chatResponse = chatClient.prompt(prompt).call().chatResponse();
            String result = chatResponse.getResult().getOutput().getText();

            List<VerifyQuestionResultDto> batchQuestions = outputConverter.convert(result);

            if (batchQuestions == null || batchQuestions.isEmpty()) {
                break; // Không sinh thêm được nữa
            }

            allQuestions.addAll(batchQuestions);
        }

        // Nếu tổng số câu sinh được > số yêu cầu thì chỉ trả về đúng số yêu cầu
        if (allQuestions.size() > totalQuestionsNeeded) {
            allQuestions = allQuestions.subList(0, totalQuestionsNeeded);
        }

        return allQuestions;
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
