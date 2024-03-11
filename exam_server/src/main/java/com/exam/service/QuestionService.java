package com.exam.service;

import com.exam.dto.request.QuestionRequest;
import com.exam.model.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface QuestionService {
    public ResponseEntity<?> addQuestion(QuestionRequest question);
    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestion(Long id);

    @GetMapping("/")
    public ResponseEntity<?> getAllQuestions();

    @PutMapping("/edit/{id}")
    public ResponseEntity<Question> editQuestion(Long id, Question question);

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(Long id);
}
