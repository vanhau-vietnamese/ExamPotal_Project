package com.exam.service;

import com.exam.dto.request.QuizRequest;
import com.exam.model.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Map;

public interface QuizService {
    public ResponseEntity<?> getAllQuizzes();
    public ResponseEntity<?> addQuiz(QuizRequest quiz);
    public ResponseEntity<?> getQuiz(Long id);
    public ResponseEntity<?> deleteQuiz(Long id);
    public ResponseEntity<?> updateQuiz(Long id, QuizRequest quizRequest);
    public ResponseEntity<?> getQuizzesOfCategory(Long categoryId);
    public ResponseEntity<?> getQuizzesOfCreateAt(Map<String, Timestamp> request);

    ResponseEntity<?> searchQuizzes(Map<String, String> searchRequest);
}
