package com.exam.chatbot.impl;

import com.exam.QuestionTypeConstant;
import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.chatbot.service.AgentService;
import com.exam.chatbot.service.AgentVerifyQuestionService;
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

    private final AgentVerifyQuestionService agentVerifyQuestionService;

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
            VerifyQuestionResultDto verifyQuestionResultDto = agentVerifyQuestionService.verifyQuestion(question);
            verifyQuestionResultDto.setQuestion(question);
            results.add(verifyQuestionResultDto);
        }
        return results;
    }

    @Override
    public String chatMessage(String message, List<MultipartFile> files) {
        return agentService.chatMessage(message, files);
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

//    private String normalizeText(String text) {
//        text = text.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\p{Punct}\\s]", "");  // remove special symbols
//
//        String[] lines = text.split("\\r?\\n");
//        StringBuilder normalized = new StringBuilder();
//
//        boolean isQuestionStarted = false;
//        StringBuilder currentQuestion = new StringBuilder();
//        StringBuilder currentAnswers = new StringBuilder();
//
//        for (String line : lines) {
//            line = line.trim();
//            if (line.isEmpty()) continue;
//
//            if (line.matches("^Câu\\s*\\d+[:\\.\\)]?.*")) {
//                if (isQuestionStarted) {
//                    normalized.append(currentQuestion.toString().trim()).append("\n");
//                    normalized.append(currentAnswers.toString().trim()).append("\n");
//                }
//                currentQuestion.setLength(0);
//                currentAnswers.setLength(0);
//                isQuestionStarted = true;
//                currentQuestion.append(line);
//            } else if (line.matches("^[A-Z][\\.\\):]\\s+.*")) {
//                currentAnswers.append(line).append("\n");
//            } else if (line.toLowerCase().startsWith("đáp án")) {
//                currentAnswers.append(line).append("\n");
//            } else {
//                currentQuestion.append(" ").append(line);
//            }
//        }
//
//        if (isQuestionStarted) {
//            normalized.append(currentQuestion.toString().trim()).append("\n");
//            normalized.append(currentAnswers.toString().trim()).append("\n");
//        }
//
//        return normalized.toString();
//    }

//    private String normalizeText(String text) {
//        text = text.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\p{Punct}\\s]", "");  // Xóa ký tự đặc biệt bất thường
//
//        String[] lines = text.split("\\r?\\n");
//        StringBuilder normalized = new StringBuilder();
//
//        boolean isInsideQuestion = false;
//        StringBuilder currentQuestion = new StringBuilder();
//        StringBuilder currentAnswers = new StringBuilder();
//
//        Pattern questionStartPattern = Pattern.compile("^Câu\\s*\\d+[:\\.\\)]?.*", Pattern.CASE_INSENSITIVE);
//        Pattern optionPattern = Pattern.compile("^([A-H])\\s*[\\.\\):]\\s+.*");
//        Pattern answerLinePattern = Pattern.compile("^Đáp án\\s*[:：].*", Pattern.CASE_INSENSITIVE);
//
//        for (String line : lines) {
//            line = line.trim();
//            if (line.isEmpty()) continue;
//
//            Matcher questionMatcher = questionStartPattern.matcher(line);
//            Matcher optionMatcher = optionPattern.matcher(line);
//            Matcher answerMatcher = answerLinePattern.matcher(line);
//
//            if (questionMatcher.find()) {
//                if (isInsideQuestion) {
//                    // kết thúc câu cũ
//                    normalized.append(currentQuestion.toString().trim()).append("\n");
//                    normalized.append(currentAnswers.toString().trim()).append("\n");
//                }
//                currentQuestion.setLength(0);
//                currentAnswers.setLength(0);
//                currentQuestion.append(line);
//                isInsideQuestion = true;
//
//            } else if (optionMatcher.find()) {
//                currentAnswers.append(line).append("\n");
//            } else if (answerMatcher.find()) {
//                currentAnswers.append(line).append("\n");
//            } else {
//                if (isInsideQuestion && currentAnswers.length() == 0) {
//                    // vẫn đang ở phần câu hỏi
//                    currentQuestion.append(" ").append(line);
//                } else if (isInsideQuestion) {
//                    // continuation line của phương án gần nhất
//                    currentAnswers.append(line).append("\n");
//                }
//            }
//        }
//
//        // Thêm câu cuối cùng nếu còn sót lại
//        if (isInsideQuestion) {
//            normalized.append(currentQuestion.toString().trim()).append("\n");
//            normalized.append(currentAnswers.toString().trim()).append("\n");
//        }
//
//        return normalized.toString();
//    }

    private String normalizeText(String text) {
        text = text.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\p{Punct}\\s]", "");  // Xóa ký tự đặc biệt bất thường

        String[] lines = text.split("\\r?\\n");
        StringBuilder normalized = new StringBuilder();

        boolean isInsideQuestion = false;
        StringBuilder currentQuestion = new StringBuilder();
        StringBuilder currentAnswers = new StringBuilder();

        Pattern questionStartPattern = Pattern.compile("^Câu\\s*\\d+[:\\.\\)]?.*", Pattern.CASE_INSENSITIVE);
        Pattern optionLinePattern = Pattern.compile("([A-H])\\s*[\\.\\):]\\s+[^A-H]+");
        Pattern answerLinePattern = Pattern.compile("^Đáp án\\s*[:：].*", Pattern.CASE_INSENSITIVE);

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            Matcher questionMatcher = questionStartPattern.matcher(line);
            Matcher answerMatcher = answerLinePattern.matcher(line);

            if (questionMatcher.find()) {
                if (isInsideQuestion) {
                    normalized.append(currentQuestion.toString().trim()).append("\n");
                    normalized.append(currentAnswers.toString().trim()).append("\n");
                }
                currentQuestion.setLength(0);
                currentAnswers.setLength(0);
                currentQuestion.append(line);
                isInsideQuestion = true;

            } else if (answerMatcher.find()) {
                currentAnswers.append(line).append("\n");

            } else if (line.matches("^([A-H])\\s*[\\.\\):]\\s+.*")) {
                // Dòng chỉ có một đáp án, ví dụ: A. something
                currentAnswers.append(line).append("\n");

            } else if (optionLinePattern.matcher(line).find()) {
                // Trường hợp dòng có nhiều phương án gộp lại: A. ... B. ... C. ...
                // Sử dụng regex để tách thành nhiều dòng
                Matcher matcher = Pattern.compile("([A-H])\\s*[\\.\\):]\\s+[^A-H]*").matcher(line);
                while (matcher.find()) {
                    String matched = matcher.group();
                    currentAnswers.append(matched.trim()).append("\n");
                }

            } else {
                if (isInsideQuestion && currentAnswers.length() == 0) {
                    currentQuestion.append(" ").append(line);
                } else if (isInsideQuestion) {
                    currentAnswers.append(line).append("\n");
                }
            }
        }

        if (isInsideQuestion) {
            normalized.append(currentQuestion.toString().trim()).append("\n");
            normalized.append(currentAnswers.toString().trim()).append("\n");
        }

        return normalized.toString();
    }

    private List<QuestionRequest> parseQuestions(String text) {
        List<QuestionRequest> questions = new ArrayList<>();

        // Regex mạnh hơn: bắt đầu từ "Câu x", kết thúc khi gặp "Câu x+1" hoặc kết thúc chuỗi
        Pattern questionBlockPattern = Pattern.compile(
                "(?i)(?m)(Câu\\s*\\d+[:\\.\\)]?[\\s\\S]*?)(?=(\\n\\s*Câu\\s*\\d+[:\\.\\)]|\\z))"
        );

        Matcher blockMatcher = questionBlockPattern.matcher(text);

        while (blockMatcher.find()) {
            String blockContent = blockMatcher.group(1).trim();  // dùng group(1) thay vì group(2)

            // Chia block thành các dòng
            String[] lines = blockContent.split("\\n");

            StringBuilder questionTextBuilder = new StringBuilder();
            Map<String, String> options = new LinkedHashMap<>();
            List<String> correctAnswers = new ArrayList<>();

            Pattern optionPattern = Pattern.compile("^([A-H])\\s*[\\.\\):]\\s*(.+)");
            Pattern answerPattern = Pattern.compile("^Đáp án\\s*[:：]\\s*(.+)", Pattern.CASE_INSENSITIVE);

            String lastOptionKey = null;

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;

                Matcher optionMatcher = optionPattern.matcher(line);
                Matcher answerMatcher = answerPattern.matcher(line);

                if (answerMatcher.find()) {
                    // Trích xuất đáp án đúng (có thể nhiều hơn 1)
                    String[] parts = answerMatcher.group(1).split("[,;\\s]+");
                    for (String part : parts) {
                        String key = part.trim().toUpperCase();
                        if (!key.isEmpty()) correctAnswers.add(key);
                    }
                } else if (optionMatcher.find()) {
                    String key = optionMatcher.group(1).trim();
                    String value = optionMatcher.group(2).trim();
                    options.put(key, value);
                    lastOptionKey = key;
                } else {
                    // Nếu là continuation line của phương án gần nhất
                    if (lastOptionKey != null && options.containsKey(lastOptionKey)) {
                        String prev = options.get(lastOptionKey);
                        options.put(lastOptionKey, prev + " " + line);
                    } else {
                        // Nếu là phần nội dung câu hỏi
                        questionTextBuilder.append(line).append(" ");
                    }
                }
            }

            String questionText = questionTextBuilder.toString().trim();

            if (!questionText.isEmpty() && options.size() >= 2) {
                QuestionRequest question = new QuestionRequest();
                question.setContent(questionText);
                question.setMedia(null); // nếu không có media
                question.setCategoryId(1L); // có thể chỉnh nếu cần

                List<AnswerRequest> answerRequests = new ArrayList<>();
                var correctCount = 0;
                for (Map.Entry<String, String> entry : options.entrySet()) {
                    boolean isCorrect = correctAnswers.contains(entry.getKey());
                    answerRequests.add(new AnswerRequest(null, entry.getValue(), isCorrect));
                    if(isCorrect) correctCount++;
                }

                question.setQuestionTypeId(correctCount > 1
                        ? QuestionTypeConstant.MULTIPLE_CHOICE
                        : QuestionTypeConstant.SINGLE_CHOICE);

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
