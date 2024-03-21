package com.exam.controller;

import com.exam.dto.request.StartQuizRequest;
import com.exam.dto.request.SubmitRequest;
import com.exam.model.Quiz;
import com.exam.model.User;
import com.exam.model.UserQuizResult;
import com.exam.repository.QuizRepository;
import com.exam.repository.UserQuizResultRepository;
import com.exam.service.TakeQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequiredArgsConstructor
@RequestMapping("/take_exam")
public class TakeQuizController {
    private final TakeQuizService takeQuizService;

    @PostMapping("/start-quiz")
    public ResponseEntity<?> startQuiz(@RequestBody StartQuizRequest startQuizRequest){
        return takeQuizService.startQuiz(startQuizRequest);
    }
    @PostMapping("/submit")
    public ResponseEntity<?> submitQuiz(@RequestBody SubmitRequest submitRequest){
        return takeQuizService.submitQuiz(submitRequest);
    }
}
