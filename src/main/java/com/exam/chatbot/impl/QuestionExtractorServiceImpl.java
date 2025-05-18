package com.exam.chatbot.impl;

import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.chatbot.service.AgentService;
import com.exam.chatbot.service.QuestionExtractorService;
import com.exam.dto.request.AnswerRequest;
import com.exam.dto.request.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class QuestionExtractorServiceImpl implements QuestionExtractorService {
    private final AgentService agentService;

    @Override
    public List<QuestionRequest> extractQuestions(MultipartFile file) throws IOException {
        String text = extractTextFromFile(file);
        String cleanedText = normalizeText(text);
        return parseQuestions(cleanedText);
    }

    @Override
    public List<VerifyQuestionResultDto>verifyQuestions(MultipartFile file) throws IOException {
        // lấy câu hỏi từ file
        List<QuestionRequest> extractQuestions = this.extractQuestions(file);
        List<VerifyQuestionResultDto> results = new ArrayList<>();
        for(QuestionRequest question : extractQuestions) {
            VerifyQuestionResultDto verifyQuestionResultDto =  agentService.verifyQuestion(question);
            results.add(verifyQuestionResultDto);
        }
        return results;
    }

    private String extractTextFromFile(MultipartFile file) throws IOException {
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

    private String extractFromPdf(MultipartFile file) throws IOException {
        try (RandomAccessReadBuffer rar = new RandomAccessReadBuffer(file.getInputStream());
             PDDocument document = Loader.loadPDF(rar)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String normalizeText(String text) {
        text = text.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\p{Punct}\\s]", "");  // remove special symbols

        String[] lines = text.split("\\r?\\n");
        StringBuilder normalized = new StringBuilder();

        boolean isQuestionStarted = false;
        StringBuilder currentQuestion = new StringBuilder();
        StringBuilder currentAnswers = new StringBuilder();

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.matches("^Câu\\s*\\d+[:\\.\\)]?.*")) {
                if (isQuestionStarted) {
                    normalized.append(currentQuestion.toString().trim()).append("\n");
                    normalized.append(currentAnswers.toString().trim()).append("\n");
                }
                currentQuestion.setLength(0);
                currentAnswers.setLength(0);
                isQuestionStarted = true;
                currentQuestion.append(line);
            } else if (line.matches("^[A-Z][\\.\\):]\\s+.*")) {
                currentAnswers.append(line).append("\n");
            } else if (line.toLowerCase().startsWith("đáp án")) {
                currentAnswers.append(line).append("\n");
            } else {
                currentQuestion.append(" ").append(line);
            }
        }

        if (isQuestionStarted) {
            normalized.append(currentQuestion.toString().trim()).append("\n");
            normalized.append(currentAnswers.toString().trim()).append("\n");
        }

        return normalized.toString();
    }

    private List<QuestionRequest> parseQuestions(String text) {
        List<QuestionRequest> questions = new ArrayList<>();

        Pattern questionBlockPattern = Pattern.compile(
                "(Câu\\s?\\d+[:\\.\\)]?)([\\s\\S]*?)(?=\\nCâu\\s?\\d+[:\\.\\)]|\\z)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
        );

        Matcher blockMatcher = questionBlockPattern.matcher(text);

        while (blockMatcher.find()) {
            String blockContent = blockMatcher.group(2).trim();
            String[] lines = blockContent.split("\\n");

            StringBuilder questionTextBuilder = new StringBuilder();
            Map<String, String> options = new LinkedHashMap<>();
            List<String> correctAnswers = new ArrayList<>();

            Pattern optionPattern = Pattern.compile("^([A-Z])[\\.\\):]\\s*(.+)");
            Pattern answerPattern = Pattern.compile("^Đáp án\\s*[:：]\\s*(.+)", Pattern.CASE_INSENSITIVE);

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;

                Matcher optionMatcher = optionPattern.matcher(line);
                Matcher answerMatcher = answerPattern.matcher(line);

                if (optionMatcher.find()) {
                    String label = optionMatcher.group(1);
                    String content = optionMatcher.group(2);
                    options.put(label, content);
                } else if (answerMatcher.find()) {
                    String[] correctParts = answerMatcher.group(1).split("[,;\\s]+");
                    for (String part : correctParts) {
                        part = part.trim().toUpperCase();
                        if (!part.isEmpty()) correctAnswers.add(part);
                    }
                } else {
                    questionTextBuilder.append(line).append(" ");
                }
            }

            String questionText = questionTextBuilder.toString().trim();
            if (options.size() >= 2) {
                QuestionRequest question = new QuestionRequest();
                question.setContent(questionText);
                question.setMedia(null);
                question.setQuestionTypeId("multiple_choice");
                question.setCategoryId(1L);

                List<AnswerRequest> answerRequests = new ArrayList<>();
                for (Map.Entry<String, String> entry : options.entrySet()) {
                    boolean isCorrect = correctAnswers.contains(entry.getKey());
                    answerRequests.add(new AnswerRequest("media" + entry.getKey(), entry.getValue(), isCorrect));
                }

                question.setAnswerRequestList(answerRequests);
                questions.add(question);
            }
        }

        return questions;
    }

    private String extractFromWord(MultipartFile file) throws IOException {
        return new XWPFWordExtractor(new XWPFDocument(file.getInputStream())).getText();
    }
}
