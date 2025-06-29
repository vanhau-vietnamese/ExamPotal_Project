package com.exam.chatbot.impl;

import com.exam.chatbot.dto.ChatRequestDto;
import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.chatbot.service.AgentService;
import com.exam.config.JwtAuthenticationFilter;
import com.exam.config.JwtUtils;
import com.exam.dto.request.QuestionRequest;
import com.exam.model.User;
import com.exam.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.content.Media;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class AgentServiceImpl implements AgentService {
    private final ChatClient chatClient;
    private final QdrantVectorStore vectorStore;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final ChatMemory chatMemory;

    @Value("classpath:/prompts/verify-question.st")
    private Resource ragPromptTemplate;

    public AgentServiceImpl(ChatClient.Builder builder, QdrantVectorStore vectorStore, JwtAuthenticationFilter jwtAuthenticationFilter, JwtUtils jwtUtils, UserRepository userRepository) {
        this.chatMemory = new InMemoryChatMemory();
        this.chatClient = builder
                .defaultSystem("### Vai trò: " +
                        "Bạn là 1 AI hoặc 1 chatbot hỗ trợ và trả lời các câu hỏi từ người dùng, trò chuyện như 1 người trợ giảng thông minh. Nếu câu hỏi của người dùng nằm ngoài vấn đề data đang có, thì bạn hãy trả lời như bình thường. " +
                        "- Nếu có thắc mắc gì liên quan đến tài khoản hay hệ thống web, bạn hãy nói người dùng liên hệ đến email admin này: 6251071029@st.utc2.edu.vn." +
//                        "### Trình bày câu trả lời theo bố cục sau: " +
//                        "- Phần 1: Tôm tắt nội dung sẽ trình bày " +
//                        "- Phần 2: Trình bày nội dung. " +
//                        "- Phần 3: Cung cấp thông tin nguồn là các đường dẫn trang web đến nội dung trong câu trả lời nếu có. " +
//                        " Nguồn tham khảo: " + "- [tiêu đề](đường dẫn)" +
                        "### Lưu ý: Nếu câu hỏi của người dùng chưa thể hiện cụ thể yêu cầu, bạn sẽ đặt câu hỏi yêu người dùng hỏi rõ ràng" +
                        "Trình bày câu trả lời của bạn 1 cách có cấu trúc dễ đọc, dễ hiểu, gọn gàng và đi vào trọng tâm của câu hỏi. ")
                .defaultAdvisors(
                        List.of(
                                new QuestionAnswerAdvisor(vectorStore),
                                new MessageChatMemoryAdvisor(this.chatMemory)
                        )
                )
                .build();
        this.vectorStore = vectorStore;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public String imageToCode() throws IOException {
        var imageData = new ClassPathResource("/images/java-open-ai.png");
        UserMessage userMessage = new UserMessage("The following is a screenshot of some code. Can you translate this from the image into text?",
                List.of(new Media(MimeTypeUtils.IMAGE_PNG,imageData)));
        Prompt prompt = new Prompt(userMessage);
        ChatResponse chatResponse = chatClient.prompt(prompt).call().chatResponse();
        return chatResponse.getResult().getOutput().getText();
    }

    @Override
    public String doChat(ChatRequestDto request) {
        String conversationId = getConversationId();

        var messageResponse =  chatClient.prompt()
                        .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId))
                        .user(request.getMessage())
                        .call()
                        .content();
        return messageResponse;
    }

    @Override
    public List<Message> getHistory() {
        return chatMemory.get(getConversationId(), 100);
    }

    @Override
    public String clearHistory() {
        chatMemory.clear(getConversationId());
        return "Deleted chat history Successfully";
    }

    @Override
//    public String processAndStore(MultipartFile file) throws IOException {
//        String fileId = UUID.randomUUID().toString();
//        List<Document> documents = extractDocumentsFromFile(file);
//
//        // Kiểm tra và phân tích dữ liệu trước khi thêm vào vector store
//        TextSplitter splitter = new TokenTextSplitter();
//        documents = splitter.apply(documents);
//
//        documents = documents.stream()
//                .map(doc -> new Document(doc.getText(), Map.of(
//                        "file_id", fileId,
//                        "file_name", file.getOriginalFilename()
//                )))
//                .toList();
//        if (documents.isEmpty()) {
//            throw new IllegalArgumentException("Không có đoạn văn nào hợp lệ để lưu vào Qdrant");
//        }
//
//        try {
//            // Kiểm tra thêm vào Qdrant
//            vectorStore.add(documents);
//        } catch (Exception e) {
//            System.out.println("Lỗi khi thêm dữ liệu vào Qdrant: " + e.getMessage());
//            throw new IOException("Không thể lưu dữ liệu vào Qdrant", e);
//        }
//
//        return fileId;
//    }

    public String processAndStore(MultipartFile file) throws IOException {
        String fileHash = calculateFileHash(file); // Tính hash file

        // Kiểm tra file đã tồn tại chưa
        if (isFileAlreadyStored(fileHash)) {
            // Nếu file đã tồn tại, trả về fileId cũ (hoặc có thể trả về fileHash luôn)
            return getExistingFileId(fileHash);
        }

        // Nếu file chưa tồn tại, tạo fileId mới
        String fileId = UUID.randomUUID().toString();
        List<Document> documents = extractDocumentsFromFile(file);

        // Tách nhỏ văn bản
        TextSplitter splitter = new TokenTextSplitter();
        documents = splitter.apply(documents);

        documents = documents.stream()
                .map(doc -> new Document(doc.getText(), Map.of(
                        "file_id", fileId,
                        "file_hash", fileHash, // Lưu hash để kiểm tra trùng
                        "file_name", file.getOriginalFilename()
                )))
                .toList();

        if (documents.isEmpty()) {
            throw new IllegalArgumentException("Không có đoạn văn nào hợp lệ để lưu vào Qdrant");
        }

        try {
            vectorStore.add(documents);
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm dữ liệu vào Qdrant: " + e.getMessage());
            throw new IOException("Không thể lưu dữ liệu vào Qdrant", e);
        }

        return fileId;
    }

    public String calculateFileHash(MultipartFile file) throws IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(file.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Không thể tính toán hash file", e);
        }
    }

    public boolean isFileAlreadyStored(String fileHash) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        SearchRequest searchRequest = SearchRequest.builder()
                .query(fileHash)
                .topK(1)
                .similarityThreshold(0.99) // gần như tuyệt đối
                .filterExpression(b.eq("file_hash", fileHash).build())
                .build();

        List<Document> existingDocs = vectorStore.similaritySearch(searchRequest);
        return !existingDocs.isEmpty();
    }

    public String getExistingFileId(String fileHash) {
        FilterExpressionBuilder b = new FilterExpressionBuilder();
        SearchRequest searchRequest = SearchRequest.builder()
                .query(fileHash)
                .topK(1)
                .similarityThreshold(0.99)
                .filterExpression(b.eq("file_hash", fileHash).build())
                .build();

        List<Document> existingDocs = vectorStore.similaritySearch(searchRequest);
        if (existingDocs.isEmpty()) {
            throw new IllegalStateException("Không tìm thấy file với hash đã lưu.");
        }
        return (String) existingDocs.get(0).getMetadata().get("file_id");
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

        var chatResposne = chatClient.prompt(prompt).call().chatResponse();
        var textOutput = chatResposne.getResult().getOutput().getText();

        return outputConverter.convert(textOutput);
    }

    @Override
    public String chatMessage(String message, List<MultipartFile> files) {
        String conversationId = getConversationId();

        // Nếu không có file (null hoặc empty list), xử lý như message text bình thường
        if (files == null || files.isEmpty()) {
            return chatClient.prompt()
                    .user(message)
                    .call()
                    .content();
        }

        List<Media> mediaList = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return "Tất cả file gửi lên phải là ảnh (jpg, png, webp, ...)";
                }

                byte[] bytes = file.getBytes();
                ByteArrayResource imageResource = new ByteArrayResource(bytes) {
                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename();
                    }
                };

                MimeType mimeType = MimeType.valueOf(contentType);
                mediaList.add(new Media(mimeType, imageResource));

            } catch (IOException e) {
                return "Lỗi khi đọc file ảnh: " + e.getMessage();
            }
        }

        // Nếu không có message thì dùng message mặc định
        if (message == null || message.isBlank()) {
            message = "Đây là các ảnh. Hãy mô tả hoặc trả lời dựa trên nội dung ảnh.";
        }

        // Tạo Prompt với media list
        try {
            UserMessage userMessage = new UserMessage(message, mediaList);
            Prompt prompt = new Prompt(userMessage);
            ChatResponse response = chatClient.prompt(prompt).call().chatResponse();

            return response.getResult().getOutput().getText();

        } catch (Exception e) {
            return "Đã xảy ra lỗi khi gọi mô hình AI: " + e.getMessage();
        }
    }

    private String getConversationId(){
        // get jwt from request
        String jwt = jwtAuthenticationFilter.getJwt();
        FirebaseToken decodedToken = jwtUtils.verifyToken(jwt);
        String email = decodedToken.getEmail();
        User user = userRepository.findByEmail(email);

        return user.getId();
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
