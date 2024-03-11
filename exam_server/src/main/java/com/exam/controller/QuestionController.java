package com.exam.controller;

import com.exam.dto.request.QuestionRequest;
import com.exam.model.Question;
import com.exam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionRequest question){
        return questionService.addQuestion(question);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestion(@PathVariable("questionId") Long id){
        return questionService.getQuestion(id);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Question> editQuestion(@PathVariable("id") Long id, @RequestBody Question question){
        return questionService.editQuestion(id, question);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("id")Long id){
        return questionService.deleteQuestion(id);
    }
}
