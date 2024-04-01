package com.exam.service;

import com.exam.dto.request.QuestionRequest;
import com.exam.model.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface QuestionService {
    public ResponseEntity<?> addQuestion(QuestionRequest question);
    public ResponseEntity<?> getQuestion(Long id);
    public ResponseEntity<?> getAllQuestions();
    public ResponseEntity<?> editQuestion(Long id, QuestionRequest questionRequest);
    public ResponseEntity<String> deleteQuestion(Long id);
    public ResponseEntity<?> getQuestionsOfQuiz(Long id);
    public ResponseEntity<?> getQuestionsOfCategory(Long id);
}
