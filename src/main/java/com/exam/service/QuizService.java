package com.exam.service;

import com.exam.dto.request.QuizRequest;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.Map;

public interface QuizService {
    ResponseEntity<?> getAllQuizzes();
    ResponseEntity<?> addQuiz(QuizRequest quiz);
    ResponseEntity<?> getQuiz(Long id);
    ResponseEntity<?> deleteQuiz(Long id);
    ResponseEntity<?> updateQuiz(Long id, QuizRequest quizRequest);
    ResponseEntity<?> getQuizzesOfCategory(Long categoryId);
    ResponseEntity<?> getQuizzesOfCreateAt(Map<String, Timestamp> request);

    ResponseEntity<?> searchQuizzes(Map<String, String> searchRequest);

    ResponseEntity<?> paginationQuestion(int page, int size);
}
