package com.exam.chatbot.controller;

import com.exam.chatbot.dto.GenerateQuestionRequest;
import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.chatbot.service.QuestionExtractorService;
import com.exam.dto.request.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionExtractController {
    private final QuestionExtractorService questionExtractorService;

    @PostMapping("/extract")
    public ResponseEntity<List<QuestionRequest>> extractQuestions(@RequestParam("file") MultipartFile file) throws IOException {
        List<QuestionRequest> questions = questionExtractorService.extractQuestions(file);
        return ResponseEntity.ok(questions);
    }

    @PostMapping("/extract/verify")
    public ResponseEntity<List<VerifyQuestionResultDto>> verifyQuestions(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(questionExtractorService.verifyQuestions(file));
    }

    @PostMapping("/generate")
    public ResponseEntity<List<VerifyQuestionResultDto>> generateQuestions(
            @RequestParam("file") MultipartFile file,
            @RequestParam("message") String message,
            @RequestParam("number") Integer number) throws IOException {

        // Tạo request object
        GenerateQuestionRequest request = new GenerateQuestionRequest();
        request.setFile(file);
        request.setNumber(number);
        request.setMessage(message);
        return ResponseEntity.ok(questionExtractorService.generateQuestions(request));
    }

    @PostMapping(value = "/test")
    public ResponseEntity<String> generation(@RequestParam("message") String message, @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        return ResponseEntity.ok(questionExtractorService.chatMessage(message, files));
    }

}
