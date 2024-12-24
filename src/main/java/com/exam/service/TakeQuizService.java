package com.exam.service;

import com.exam.dto.request.StartQuizRequest;
import com.exam.dto.request.SubmitRequest;
import org.springframework.http.ResponseEntity;

public interface TakeQuizService {
    ResponseEntity<?> submitQuiz(SubmitRequest submitRequest);
    ResponseEntity<?> startQuiz(StartQuizRequest startQuizRequest);
}
