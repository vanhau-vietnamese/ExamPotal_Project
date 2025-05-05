package com.exam.chatbot.controller;

import com.exam.chatbot.dto.VerifyQuestionResultDto;
import com.exam.chatbot.service.QuestionExtractorService;
import com.exam.dto.request.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
