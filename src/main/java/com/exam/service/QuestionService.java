package com.exam.service;

import com.exam.dto.request.FilterCreateAtRequest;
import com.exam.dto.request.QuestionRequest;
import com.exam.dto.request.QuestionTypeRequest;
import com.exam.model.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface QuestionService {
    public ResponseEntity<?> addQuestion(QuestionRequest question);
    public ResponseEntity<?> getQuestion(Long id);
    public ResponseEntity<?> getAllQuestions();
    public ResponseEntity<?> editQuestion(Long id, QuestionRequest questionRequest);
    public ResponseEntity<String> deleteQuestion(Long id);
    public ResponseEntity<?> getQuestionsOfQuiz(Long id);
    public ResponseEntity<?> getQuestionsOfCategory(Long id);
    public ResponseEntity<?> getQuestionsOfQuestionType(QuestionTypeRequest questionTypeRequest);
    public ResponseEntity<?> getQuestionsOfCreateAt(FilterCreateAtRequest request);
    public ResponseEntity<?> searchQuestions(Map<String, String> searchRequest);
}
