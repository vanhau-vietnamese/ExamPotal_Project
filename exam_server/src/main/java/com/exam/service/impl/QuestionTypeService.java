package com.exam.service.impl;

import com.exam.model.QuestionType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface QuestionTypeService {
    public ResponseEntity<?> addQuestionType(QuestionType questionType);
    public ResponseEntity<?> getAllQuestionTypes();
}
