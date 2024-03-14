package com.exam.service.impl;

import com.exam.model.QuestionType;
import com.exam.repository.QuestionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionTypeServiceImpl implements QuestionTypeService{
    private final QuestionTypeRepository questionTypeRepository;
    @Override
    public ResponseEntity<?> addQuestionType(QuestionType questionType) {
        if(questionTypeRepository.existsByDisplayName(questionType.getDisplayName())){
            return ResponseEntity.badRequest().body("QuestionType already exists");
        }
        return ResponseEntity.ok(questionTypeRepository.save(questionType));
    }

    @Override
    public ResponseEntity<?> getAllQuestionTypes() {
        return ResponseEntity.ok(questionTypeRepository.findAll());
    }
}
