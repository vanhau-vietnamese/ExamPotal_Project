package com.exam.controller;

import com.exam.model.QuestionType;
import com.exam.service.impl.QuestionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questionType")
public class QuestionTypeController {
    private final QuestionTypeService questionTypeService;
    @PostMapping("/add")
    public ResponseEntity<?> addQuestionType(@RequestBody QuestionType questionType){
        return questionTypeService.addQuestionType(questionType);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllQuestionTypes(){
        return questionTypeService.getAllQuestionTypes();
    }
}
