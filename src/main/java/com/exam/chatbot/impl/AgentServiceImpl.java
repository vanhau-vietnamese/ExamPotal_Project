package com.exam.chatbot.impl;

import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.chatbot.service.AgentService;
import com.exam.dto.request.QuestionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class AgentServiceImpl implements AgentService {
    private final ChatClient chatClient;
    private final QdrantVectorStore vectorStore;

    @Value("classpath:/prompts/verify-question.st")
    private Resource ragPromptTemplate;

    public AgentServiceImpl(ChatClient.Builder builder, QdrantVectorStore vectorStore) {
        this.chatClient = builder.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore)).build();
        this.vectorStore = vectorStore;
    }


    @Override
    public String processAndStore(MultipartFile file) throws IOException {
        String fileId = UUID.randomUUID().toString();
        List<Document> documents = extractDocumentsFromFile(file);

        // Kiểm tra và phân tích dữ liệu trước khi thêm vào vector store
        TextSplitter splitter = new TokenTextSplitter();
        documents = splitter.apply(documents);

        documents = documents.stream()
                .map(doc -> new Document(doc.getText(), Map.of(
                        "file_id", fileId,
                        "file_name", file.getOriginalFilename()
                )))
                .toList();
        if (documents.isEmpty()) {
            throw new IllegalArgumentException("Không có đoạn văn nào hợp lệ để lưu vào Qdrant");
        }

        try {
            // Kiểm tra thêm vào Qdrant
            vectorStore.add(documents);
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm dữ liệu vào Qdrant: " + e.getMessage());
            throw new IOException("Không thể lưu dữ liệu vào Qdrant", e);
        }

        return fileId;
    }

    @Override
    public VerifyQuestionResultDto verifyQuestion(QuestionRequest questionRequest) throws IOException {
        var outputConverter = new BeanOutputConverter<>(VerifyQuestionResultDto.class);
        String format = outputConverter.getFormat();

        // Convert questionRequest to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String questionJson = objectMapper.writeValueAsString(questionRequest);

        // Gọi PromptTemplate với các tham số
        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate);
        Map<String, Object> promptParameters = new HashMap<>();
        promptParameters.put("question", questionJson); // <<--- JSON của câu hỏi
        promptParameters.put("format", format);
        Prompt prompt = promptTemplate.create(promptParameters);

        var chatResposne = chatClient.prompt(prompt).call().chatResponse();
        var textOutput = chatResposne.getResult().getOutput().getText();

        return outputConverter.convert(textOutput);
    }

    private List<Document> extractDocumentsFromFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename == null) throw new IllegalArgumentException("File must have a name");

        if (filename.endsWith(".pdf")) {
            return extractFromPdf(file);
        }
        else {
            throw new IllegalArgumentException("Unsupported file type: " + filename);
        }
    }

    private List<Document> extractFromPdf(MultipartFile file) throws IOException {
        Resource resource = new InputStreamResource(file.getInputStream());

        PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(
                        new ExtractedTextFormatter.Builder()
                                .withNumberOfBottomTextLinesToDelete(0)
                                .withNumberOfTopPagesToSkipBeforeDelete(0)
                                .build()
                )
                .withPagesPerDocument(1)
                .build();

        PagePdfDocumentReader reader = new PagePdfDocumentReader(resource, config);
        return reader.get();
    }

    @Override
    public List<VerifyQuestionResultDto> verifyQuestions(List<QuestionRequest> extractQuestions) throws IOException {
//        BeanOutputConverter<List<VerifyQuestionResultDto>> outputConverter = new BeanOutputConverter<>(
//                new ParameterizedTypeReference<List<VerifyQuestionResultDto>>() {}
//        );
//        String format = outputConverter.getFormat();
//
//        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate, Map.of(
//                "question", String.valueOf(countQuestion),
//                "documents", String.join("\n", contentList),
//                "format", format
//        ));
//        Prompt prompt = promptTemplate.create();
//
//        ChatResponse chatResponse = chatClient.prompt().call().chatResponse();
//
//        return outputConverter.convert(
//                Objects.requireNonNull(chatResponse)
//                        .getResult()
//                        .getOutput()
//                        .getText()
//        );
        return null;
    }

}
