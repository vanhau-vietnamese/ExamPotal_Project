package com.exam.service;

import com.exam.dto.request.SubmitRequest;
import org.springframework.http.ResponseEntity;

public interface TakeQuizService {
    public ResponseEntity<?> submitQuiz(SubmitRequest submitRequest);
    public void startQuiz(Long id);
}
