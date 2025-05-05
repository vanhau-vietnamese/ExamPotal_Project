package com.exam.chatbot.controller;

import com.exam.chatbot.service.FileProcessingService;
import com.exam.dto.request.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/upload")
public class FileUploadController {
    private final FileProcessingService fileProcessingService;

    @PostMapping("/file")

    // return list question
    public ResponseEntity<List<QuestionRequest>> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(name = "countQuestion", defaultValue = "10") int countQuestion) {
        try {
//            // Bước 1: Lưu vào Vector Store và nhận về fileId
//            String fileId = fileProcessingService.processAndStore(file);

//            // Bước 2: Sinh câu hỏi từ nội dung trong Vector Store của file đó
//            List<QuestionRequest> questions = fileProcessingService.generateQuestionsFromFileId(fileId, countQuestion);

            List<QuestionRequest> questions = fileProcessingService.handlePdf(file, countQuestion);
            return ResponseEntity.ok(questions);
//            return ResponseEntity.ok(null);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

//    public ResponseEntity<VerifyResult> agentVerifyQuestionFromFile(){
//
//    }
}
