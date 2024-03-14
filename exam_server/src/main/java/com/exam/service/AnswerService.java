package com.exam.service;

import com.exam.dto.request.AnswerRequest;
import org.springframework.http.ResponseEntity;

public interface AnswerService {
    public ResponseEntity<?> addAnswer(AnswerRequest answerRequest);
}
