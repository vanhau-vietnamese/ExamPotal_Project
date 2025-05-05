package com.exam.service;

import com.exam.dto.request.FilterCreateAtRequest;
import com.exam.dto.request.QuestionRequest;
import com.exam.dto.request.QuestionTypeRequest;
import com.exam.model.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface QuestionService {
    ResponseEntity<?> addQuestion(QuestionRequest question);
    ResponseEntity<?> getQuestion(Long id);
    ResponseEntity<?> getAllQuestions();
    ResponseEntity<?> editQuestion(Long id, QuestionRequest questionRequest);
    ResponseEntity<String> deleteQuestion(Long id);
    ResponseEntity<?> getQuestionsOfQuiz(Long id);
    ResponseEntity<?> getQuestionsOfCategory(Long id);
    ResponseEntity<?> getQuestionsOfQuestionType(QuestionTypeRequest questionTypeRequest);
    ResponseEntity<?> getQuestionsOfCreateAt(FilterCreateAtRequest request);
    ResponseEntity<?> searchQuestions(Map<String, String> searchRequest);
    ResponseEntity<?> paginationQuestions(int page, int size);
}
