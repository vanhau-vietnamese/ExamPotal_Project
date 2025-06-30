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

    @Value("classpath:/prompts/verify-question-v2.st")
    private Resource ragVerifyPromptTemplate;

    public AgentVerifyQuestionServiceImpl(ChatClient.Builder builder, QdrantVectorStore vectorStore) {
        this.chatMemory = new InMemoryChatMemory();
        this.chatClient = builder
                .build();
        this.vectorStore = vectorStore;
    }

    @Transactional
    @Override

//    public VerifyQuestionResultDto verifyQuestion(QuestionRequest questionRequest) throws IOException {
//        // Tạo format cho JSON kết quả
//        var outputConverter = new BeanOutputConverter<>(VerifyQuestionResultDto.class);
//        String format = outputConverter.getFormat();
//
//        // Convert questionRequest sang JSON string
//        ObjectMapper objectMapper = new ObjectMapper();
//        String questionJson = objectMapper.writeValueAsString(questionRequest);
//
//        System.out.println("question JSON: " + questionJson);
//
//        // Tìm context liên quan theo category_id
//        FilterExpressionBuilder b = new FilterExpressionBuilder();
//        SearchRequest searchRequest = SearchRequest.builder()
//                .query(questionRequest.getContent())
//                .topK(20) // Tìm 5 context gần nhất, đủ để hỗ trợ kiểm định
//                .similarityThreshold(0.7)
//                .build();
//
//        List<Document> relatedDocuments = vectorStore.similaritySearch(searchRequest);
//
//        // Gộp context lại thành một đoạn text
//        StringBuilder contextBuilder = new StringBuilder();
//        for (Document doc : relatedDocuments) {
//            contextBuilder.append(doc.getText()).append("\n");
//        }
//        String context = contextBuilder.toString();
//
//        // Tạo PromptTemplate với các tham số
//        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
//        Map<String, Object> promptParameters = new HashMap<>();
//        promptParameters.put("input", questionJson); // JSON câu hỏi cần kiểm định
//        promptParameters.put("format", format);
//        promptParameters.put("context", context); // Thêm context vào prompt
//
//        Prompt prompt = promptTemplate.create(promptParameters);
//
//        var chatResponse = chatClient.prompt(prompt).call().chatResponse();
//        var textOutput = chatResponse.getResult().getOutput().getText();
//
//        return outputConverter.convert(textOutput);
//    }

    public VerifyQuestionResultDto verifyQuestion(QuestionRequest questionRequest, String fileId) throws IOException {
        // Tạo format cho JSON kết quả
        var outputConverter = new BeanOutputConverter<>(VerifyQuestionResultDto.class);
        String format = outputConverter.getFormat();

        // Convert questionRequest sang JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String questionJson = objectMapper.writeValueAsString(questionRequest);

        System.out.println("question JSON: " + questionJson);

        // Tìm context liên quan theo category_id
//        FilterExpressionBuilder b = new FilterExpressionBuilder();
//        SearchRequest searchRequest = SearchRequest.builder()
//                .query(questionRequest.getContent())
//                .topK(20)
//                .similarityThreshold(0.7)
//                .build();

        FilterExpressionBuilder b = new FilterExpressionBuilder();
        SearchRequest searchRequest = SearchRequest.builder()
                .query(questionRequest.getContent())
                .topK(5)
                .filterExpression(b.eq("file_id", fileId).build())
                .build();

        List<Document> relatedDocuments = vectorStore.similaritySearch(searchRequest);

        String context;

        // Nếu không tìm thấy context phù hợp → để AI trả lời theo kiến thức chung
        if (relatedDocuments.isEmpty()) {
            context = "Không có tài liệu tham khảo. Vui lòng sử dụng kiến thức của bạn để kiểm định câu hỏi.";
        } else {
            // Gộp context lại thành một đoạn text
            StringBuilder contextBuilder = new StringBuilder();
            for (Document doc : relatedDocuments) {
                contextBuilder.append(doc.getText()).append("\n");
            }
            context = contextBuilder.toString();
        }

        // Tạo PromptTemplate với các tham số
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("input", questionJson);
        promptParameters.put("format", format);
        promptParameters.put("context", context);

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
    @Override
    public List<VerifyQuestionResultDto> generateQuestions(GenerateQuestionRequest request) throws IOException {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        SearchRequest searchRequest = SearchRequest.builder()
                .query("generate questions from this file")
                .topK(1000)
                .filterExpression(b.eq("file_id", request.getFileId()).build()) // Lọc theo file_id
                .build();

        List<Document> relatedDocuments = vectorStore.similaritySearch(searchRequest);

        if (relatedDocuments.isEmpty()) {
            throw new IllegalStateException("Không tìm thấy nội dung nào trong file.");
        }

        int totalQuestionsNeeded = request.getNumber() != null ? request.getNumber() : 10;
        List<VerifyQuestionResultDto> allQuestions = new ArrayList<>();
        int batchSize = 7000; // Mỗi batch ~ 2k token (~ 7000 ký tự)

        int currentIndex = 0;

        while (allQuestions.size() < totalQuestionsNeeded && currentIndex < relatedDocuments.size()) {
            // Lấy context cho batch hiện tại
            StringBuilder contextBuilder = new StringBuilder();
            while (currentIndex < relatedDocuments.size() && contextBuilder.length() < batchSize) {
                contextBuilder.append(relatedDocuments.get(currentIndex).getText()).append("\n");
                currentIndex++;
            }
            String context = contextBuilder.toString();

            // Tạo prompt sinh câu hỏi
            PromptTemplate promptTemplate = new PromptTemplate(ragGeneratePromptTemplate);
            Map<String, Object> promptParams = new HashMap<>();
            promptParams.put("context", context);
            promptParams.put("number", totalQuestionsNeeded - allQuestions.size());
            promptParams.put("message", request.getMessage());

            var outputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<VerifyQuestionResultDto>>() {});
            String format = outputConverter.getFormat();
            promptParams.put("format", format);

            Prompt prompt = promptTemplate.create(promptParams);

            var chatResponse = chatClient.prompt(prompt).call().chatResponse();
            String result = chatResponse.getResult().getOutput().getText();

            // Xử lý lọc bỏ phần JSON Schema (nếu có)
            int jsonArrayStart = result.indexOf("[");
            if (jsonArrayStart == -1) {
                throw new IllegalStateException("Không tìm thấy danh sách JSON hợp lệ trong kết quả.");
            }
            String validJson = result.substring(jsonArrayStart).trim();

            // Parse kết quả JSON thực
            List<VerifyQuestionResultDto> batchQuestions = outputConverter.convert(validJson);

            if (batchQuestions == null || batchQuestions.isEmpty()) {
                break; // Nếu không sinh thêm được nữa, dừng luôn
            }

            allQuestions.addAll(batchQuestions);
        }

        // Nếu sinh nhiều hơn yêu cầu, cắt lại đúng số lượng
        if (allQuestions.size() > totalQuestionsNeeded) {
            allQuestions = allQuestions.subList(0, totalQuestionsNeeded);
        }

        return allQuestions;
    }

    @Override
    public List<VerifyQuestionResultDto> verifyQuestionsV2(String fileId) throws IOException {
        // Bước 1: Lấy dữ liệu file đã upload
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        SearchRequest searchRequest1 = SearchRequest.builder()
                .query("extract questions from this file")
                .topK(1000)
                .filterExpression(b.eq("file_id", fileId).build())
                .build();

        List<Document> relatedDocuments1 = vectorStore.similaritySearch(searchRequest1);

        if (relatedDocuments1.isEmpty()) {
            throw new IllegalStateException("Không tìm thấy nội dung nào trong file.");
        }

        StringBuilder contextBuilder = new StringBuilder();
        for (Document doc : relatedDocuments1) {
            contextBuilder.append(doc.getText()).append("\n");
        }
        String context1 = contextBuilder.toString();

        System.out.println("CONTEXT: " + context1);

//        // Bước 1: Lấy dữ liệu file đã upload
//        SearchRequest searchRequest2 = SearchRequest.builder()
//                .query("extract questions from this file")
//                .topK(1000)
//                .filterExpression(b.eq("file_id", fileId).build())
//                .build();
//
//        List<Document> relatedDocuments2 = vectorStore.similaritySearch(searchRequest2);
//
//        if (relatedDocuments2.isEmpty()) {
//            throw new IllegalStateException("Không tìm thấy nội dung nào trong file.");
//        }
//
//        StringBuilder contextBuilder2 = new StringBuilder();
//        for (Document doc : relatedDocuments2) {
//            contextBuilder.append(doc.getText()).append("\n");
//        }
//        String context2 = contextBuilder2.toString();

        // Tạo prompt trích xuất câu hỏi
        PromptTemplate promptTemplate = new PromptTemplate(ragVerifyPromptTemplate);
        Map<String, Object> promptParams = new HashMap<>();
        promptParams.put("context1", context1);

        var outputConverter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<VerifyQuestionResultDto>>() {});
        String format = outputConverter.getFormat();
        promptParams.put("format", format);

        Prompt prompt = promptTemplate.create(promptParams);

        var chatResponse = chatClient.prompt(prompt).call().chatResponse();
        String result = chatResponse.getResult().getOutput().getText();

        int jsonArrayStart = result.indexOf("[");
        if (jsonArrayStart == -1) {
            throw new IllegalStateException("Không tìm thấy danh sách JSON hợp lệ trong kết quả.");
        }
        String validJson = result.substring(jsonArrayStart).trim();

        // Parse JSON kết quả
        List<VerifyQuestionResultDto> extractedQuestions = outputConverter.convert(validJson);

        if (extractedQuestions == null || extractedQuestions.isEmpty()) {
            throw new IllegalStateException("Không trích xuất được câu hỏi nào từ file.");
        }

        return extractedQuestions;
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
