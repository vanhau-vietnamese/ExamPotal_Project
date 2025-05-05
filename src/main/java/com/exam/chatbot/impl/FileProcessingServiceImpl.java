package com.exam.chatbot.impl;

import com.exam.chatbot.service.FileProcessingService;
import com.exam.dto.request.QuestionRequest;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
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
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class FileProcessingServiceImpl implements FileProcessingService {

    private final ChatClient chatClient;
    private final QdrantVectorStore vectorStore;

    @Value("classpath:/prompts/file-upload.st")
    private Resource ragPromptTemplate;

    public FileProcessingServiceImpl(ChatClient.Builder builder, QdrantVectorStore vectorStore) {
        this.chatClient = builder.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore)).build();
        this.vectorStore = vectorStore;
    }

    @Override
    public String processAndStore(MultipartFile file) throws IOException {
        String fileId = UUID.randomUUID().toString();
        List<Document> documents = extractDocumentsFromFile(file);

        TextSplitter splitter = new TokenTextSplitter();
        documents = splitter.apply(documents);

        documents = documents.stream()
                .map(doc -> new Document(doc.getText(), Map.of(
                        "file_id", fileId,
                        "file_name", file.getOriginalFilename()
                )))
                .toList();

        vectorStore.add(documents);
        return fileId;
    }

    @Override
    @Transactional
    public List<QuestionRequest> handlePdf(MultipartFile file, int countQuestion) throws IOException {
        String fileId = processAndStore(file);

        SearchRequest searchRequest = SearchRequest.builder()
                .filterExpression("file_id == '" + fileId + "'")
                .topK(countQuestion)
                .build();

        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest(searchRequest)
                .build();

        List<Document> similarDocuments = vectorStore.similaritySearch(searchRequest);
        List<String> contentList = similarDocuments.stream().map(Document::getText).toList();

        BeanOutputConverter<List<QuestionRequest>> outputConverter = new BeanOutputConverter<>(
                new ParameterizedTypeReference<List<QuestionRequest>>() {}
        );

        String format = outputConverter.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(ragPromptTemplate, Map.of(
                "countQuestion", String.valueOf(countQuestion),
                "documents", String.join("\n", contentList),
                "format", format
        ));
        Prompt prompt = promptTemplate.create();

        ChatResponse chatResponse = chatClient.prompt(prompt)
                .advisors(qaAdvisor)
                .call()
                .chatResponse();

        return outputConverter.convert(
                Objects.requireNonNull(chatResponse)
                        .getResult()
                        .getOutput()
                        .getText()
        );
    }

    @Override
    public List<QuestionRequest> generateQuestionsFromFileId(String fileId, int countQuestion) {
        SearchRequest searchRequest = SearchRequest.builder()
                .filterExpression("file_id == '" + fileId + "'")
                .topK(countQuestion)
                .build();

        List<Document> similarDocuments = vectorStore.similaritySearch(searchRequest);
        similarDocuments.forEach(doc -> System.out.println("DOCUMENT: " + doc.getText()));

        // TODO: Có thể triển khai giống handlePdf nếu cần sinh câu hỏi tại đây
        return Collections.emptyList();
    }

    private List<Document> extractDocumentsFromFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename == null) throw new IllegalArgumentException("File must have a name");

        if (filename.endsWith(".pdf")) {
            return extractFromPdf(file);
        } else if (filename.endsWith(".docx") || filename.endsWith(".doc")) {
            return extractFromWord(file);
        } else {
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

    private List<Document> extractFromWord(MultipartFile file) throws IOException {
        try (XWPFDocument doc = new XWPFDocument(file.getInputStream());
             XWPFWordExtractor extractor = new XWPFWordExtractor(doc)) {
            return List.of(new Document(extractor.getText()));
        }
    }
}
